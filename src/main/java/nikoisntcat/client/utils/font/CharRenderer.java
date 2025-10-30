package nikoisntcat.client.utils.font;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.NativeImage.Format;
import net.minecraft.util.Identifier;
import org.lwjgl.system.MemoryUtil;

class CharRenderer {
    private final Char2ObjectArrayMap<CharToBeRendered> charMap = new Char2ObjectArrayMap<>();
    int width;
    final char from;
    final Identifier identifier;
    final Font font;
    boolean calculatedStuff = false;
    final int padding;
    final char to;
    int height;

    public void calc() {
        if (!this.calculatedStuff) {
            // e.g. 'A' - '0' - 1 -> 16
            int charsInRange = this.to - this.from - 1;
            // e.g. charsInRange = 'A' - '0' - 1, then it's 6.
            int var2 = (int)(Math.ceil(Math.sqrt(charsInRange)) * 1.5);
            this.charMap.clear();
            int i = 0;
            int var4 = 0;
            int idkW = 0;
            int idkH = 0;
            int x = 0;
            int y = 0;
            int bHCapped = 0;
            List<CharToBeRendered> chars = new ArrayList<>();
            AffineTransform tx = new AffineTransform();

            for (FontRenderContext ctx = new FontRenderContext(tx, true, false); i <= charsInRange; var4++) {
                char c = (char)(this.from + i);
                Font var14 = this.getFont(c);
                Rectangle2D bounds = var14.getStringBounds(String.valueOf(c), ctx);
                int bW = (int)Math.ceil(bounds.getWidth());
                int bH = (int)Math.ceil(bounds.getHeight());
                i++;
                idkW = Math.max(idkW, x + bW);
                idkH = Math.max(idkH, y + bH);
                if (var4 >= var2) {
                    x = 0;
                    y += bHCapped + this.padding;
                    var4 = 0;
                    bHCapped = 0;
                }

                bHCapped = Math.max(bHCapped, bH);
                chars.add(new CharToBeRendered(x, y, bW, bH, c, this));
                x += bW + this.padding;
            }

            BufferedImage bi = new BufferedImage(Math.max(idkW + this.padding, 1), Math.max(idkH + this.padding, 1), 2);
            this.width = bi.getWidth();
            this.height = bi.getHeight();
            Graphics2D graphics = bi.createGraphics();
            graphics.setColor(new Color(255, 255, 255, 0));
            graphics.fillRect(0, 0, this.width, this.height);
            graphics.setColor(Color.WHITE);
            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            for (CharToBeRendered chr : chars) {
                graphics.setFont(this.getFont(chr.value()));
                FontMetrics fm = graphics.getFontMetrics();
                graphics.drawString(String.valueOf(chr.value()), chr.u(), chr.v() + fm.getAscent());
                this.charMap.put(chr.value(), chr);
            }

            registerTextureFromBufferedImage(this.identifier, bi);
            this.calculatedStuff = true;
        }
    }

    public static void registerTextureFromBufferedImage(Identifier i, BufferedImage bi) {
        try {
            int w = bi.getWidth();
            int h = bi.getHeight();
            NativeImage ni/*gge real*/ = new NativeImage(Format.RGBA, w, h, false);
            long ptr = ni.pointer;
            // those who know :trolls:
            IntBuffer intBufferClient = MemoryUtil.memIntBuffer(ptr, ni.getWidth() * ni.getHeight());
            WritableRaster raster = bi.getRaster();
            ColorModel cm = bi.getColorModel();
            // show a ban...d
            int bands = raster.getNumBands();
            int dbType = raster.getDataBuffer().getDataType();

            Object arrType = switch (dbType) {
                case 0 -> new byte[bands];
                case 1 -> new short[bands];
                case 3 -> new int[bands];
                case 4 -> new float[bands];
                case 5 -> new double[bands];
                default -> throw new IllegalArgumentException("Unknown data buffer type: " + dbType);
            };

            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    raster.getDataElements(x, y, arrType);
                    int var16 = cm.getAlpha(arrType);
                    int var17 = cm.getRed(arrType);
                    int var18 = cm.getGreen(arrType);
                    int var19 = cm.getBlue(arrType);
                    int var20 = var16 << 24 | var19 << 16 | var18 << 8 | var17;
                    intBufferClient.put(var20);
                }
            }

            NativeImageBackedTexture txt = new NativeImageBackedTexture(ni);
            txt.upload();
            if (RenderSystem.isOnRenderThread()) {
                MinecraftClient.getInstance().getTextureManager().registerTexture(i, txt);
            } else {
                RenderSystem.recordRenderCall(() -> MinecraftClient.getInstance().getTextureManager().registerTexture(i, txt));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public CharRenderer(char from, char to, Font font, Identifier identifier, int padding) {
        this.from = from;
        this.to = to;
        this.font = font;
        this.identifier = identifier;
        this.padding = padding;
    }

    public boolean inRange(char c) {
        return c >= this.from && c < this.to;
    }

    public void method2075() {
        MinecraftClient.getInstance().getTextureManager().destroyTexture(this.identifier);
        this.charMap.clear();
        this.width = -1;
        this.height = -1;
        this.calculatedStuff = false;
    }

    private Font getFont(char c) {
        // pov "obfuscation":
        return this.font.canDisplay(c) ? this.font : this.font;
    }

    public CharToBeRendered get(char c) {
        if (!this.calculatedStuff) {
            this.calc();
        }

        return this.charMap.get(c);
    }
}
