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

class Class332 {
    private final Char2ObjectArrayMap field2482 = new Char2ObjectArrayMap();
    int field2483;
    final char field2484;
    final Identifier field2485;
    final Font field2486;
    boolean field2487 = false;
    final int field2488;
    final char field2489;
    int field2490;

    public void method2072() {
        if (!this.field2487) {
            int var1 = this.field2489 - this.field2484 - 1;
            int var2 = (int)(Math.ceil(Math.sqrt((double)var1)) * 1.5);
            this.field2482.clear();
            int var3 = 0;
            int var4 = 0;
            int var5 = 0;
            int var6 = 0;
            int var7 = 0;
            int var8 = 0;
            int var9 = 0;
            List<Class2> var10 = new ArrayList();
            AffineTransform var11 = new AffineTransform();

            for (FontRenderContext var12 = new FontRenderContext(var11, true, false); var3 <= var1; var4++) {
                char var13 = (char)(this.field2484 + var3);
                Font var14 = this.method2076(var13);
                Rectangle2D var15 = var14.getStringBounds(String.valueOf(var13), var12);
                int var16 = (int)Math.ceil(var15.getWidth());
                int var17 = (int)Math.ceil(var15.getHeight());
                var3++;
                var5 = Math.max(var5, var7 + var16);
                var6 = Math.max(var6, var8 + var17);
                if (var4 >= var2) {
                    var7 = 0;
                    var8 += var9 + this.field2488;
                    var4 = 0;
                    var9 = 0;
                }

                var9 = Math.max(var9, var17);
                var10.add(new Class2(var7, var8, var16, var17, var13, this));
                var7 += var16 + this.field2488;
            }

            BufferedImage var18 = new BufferedImage(Math.max(var5 + this.field2488, 1), Math.max(var6 + this.field2488, 1), 2);
            this.field2483 = var18.getWidth();
            this.field2490 = var18.getHeight();
            Graphics2D var19 = var18.createGraphics();
            var19.setColor(new Color(255, 255, 255, 0));
            var19.fillRect(0, 0, this.field2483, this.field2490);
            var19.setColor(Color.WHITE);
            var19.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
            var19.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            var19.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            for (Class2 var21 : var10) {
                var19.setFont(this.method2076(var21.method12()));
                FontMetrics var22 = var19.getFontMetrics();
                var19.drawString(String.valueOf(var21.method12()), var21.method9(), var21.method7() + var22.getAscent());
                this.field2482.put(var21.method12(), var21);
            }

            method2073(this.field2485, var18);
            this.field2487 = true;
        }
    }

    public static void method2073(Identifier i, BufferedImage bi) {
        try {
            int var2 = bi.getWidth();
            int var3 = bi.getHeight();
            NativeImage var4 = new NativeImage(Format.RGBA, var2, var3, false);
            long var5 = var4.pointer;
            IntBuffer var7 = MemoryUtil.memIntBuffer(var5, var4.getWidth() * var4.getHeight());
            WritableRaster var9 = bi.getRaster();
            ColorModel var10 = bi.getColorModel();
            int var11 = var9.getNumBands();
            int var12 = var9.getDataBuffer().getDataType();

            Object var13 = switch (var12) {
                case 0 -> new byte[var11];
                case 1 -> new short[var11];
                default -> throw new IllegalArgumentException("Unknown data buffer type: " + var12);
                case 3 -> new int[var11];
                case 4 -> new float[var11];
                case 5 -> new double[var11];
            };

            for (int var14 = 0; var14 < var3; var14++) {
                for (int var15 = 0; var15 < var2; var15++) {
                    var9.getDataElements(var15, var14, var13);
                    int var16 = var10.getAlpha(var13);
                    int var17 = var10.getRed(var13);
                    int var18 = var10.getGreen(var13);
                    int var19 = var10.getBlue(var13);
                    int var20 = var16 << 24 | var19 << 16 | var18 << 8 | var17;
                    var7.put(var20);
                }
            }

            NativeImageBackedTexture var22 = new NativeImageBackedTexture(var4);
            var22.upload();
            if (RenderSystem.isOnRenderThread()) {
                MinecraftClient.getInstance().getTextureManager().registerTexture(i, var22);
            } else {
                RenderSystem.recordRenderCall(() -> MinecraftClient.getInstance().getTextureManager().registerTexture(i, var22));
            }
        } catch (Throwable var21) {
            var21.printStackTrace();
        }
    }

    public Class332(char from, char to, Font font, Identifier identifier, int padding) {
        this.field2484 = from;
        this.field2489 = to;
        this.field2486 = font;
        this.field2485 = identifier;
        this.field2488 = padding;
    }

    public boolean method2074(char c) {
        return c >= this.field2484 && c < this.field2489;
    }

    public void method2075() {
        MinecraftClient.getInstance().getTextureManager().destroyTexture(this.field2485);
        this.field2482.clear();
        this.field2483 = -1;
        this.field2490 = -1;
        this.field2487 = false;
    }

    private Font method2076(char c) {
        return this.field2486.canDisplay(c) ? this.field2486 : this.field2486;
    }

    public Class2 method2077(char c) {
        if (!this.field2487) {
            this.method2072();
        }

        return (Class2)this.field2482.get(c);
    }
}
