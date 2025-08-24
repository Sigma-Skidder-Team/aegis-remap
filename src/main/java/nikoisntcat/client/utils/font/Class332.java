package nikoisntcat.client.utils.font;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
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
        Object object;
        if (this.field2487) {
            return;
        }
        int n = this.field2489 - this.field2484 - 1;
        int n2 = (int)(Math.ceil(Math.sqrt(n)) * 1.5);
        this.field2482.clear();
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        ArrayList<Class2> arrayList = new ArrayList<Class2>();
        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext fontRenderContext = new FontRenderContext(affineTransform, true, false);
        while (n3 <= n) {
            char c = (char)(this.field2484 + n3);
            object = this.method2076(c);
            Rectangle2D rectangle2D = ((Font)object).getStringBounds(String.valueOf(c), fontRenderContext);
            int n10 = (int)Math.ceil(rectangle2D.getWidth());
            int n11 = (int)Math.ceil(rectangle2D.getHeight());
            ++n3;
            n5 = Math.max(n5, n7 + n10);
            n6 = Math.max(n6, n8 + n11);
            if (n4 >= n2) {
                n7 = 0;
                n8 += n9 + this.field2488;
                n4 = 0;
                n9 = 0;
            }
            n9 = Math.max(n9, n11);
            arrayList.add(new Class2(n7, n8, n10, n11, c, this));
            n7 += n10 + this.field2488;
            ++n4;
        }
        BufferedImage bufferedImage = new BufferedImage(Math.max(n5 + this.field2488, 1), Math.max(n6 + this.field2488, 1), 2);
        this.field2483 = bufferedImage.getWidth();
        this.field2490 = bufferedImage.getHeight();
        object = bufferedImage.createGraphics();
        ((Graphics)object).setColor(new Color(255, 255, 255, 0));
        ((Graphics)object).fillRect(0, 0, this.field2483, this.field2490);
        ((Graphics)object).setColor(Color.WHITE);
        ((Graphics2D)object).setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        ((Graphics2D)object).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        ((Graphics2D)object).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        for (Class2 class2 : arrayList) {
            ((Graphics)object).setFont(this.method2076(class2.method12()));
            FontMetrics fontMetrics = ((Graphics)object).getFontMetrics();
            ((Graphics2D)object).drawString(String.valueOf(class2.method12()), class2.method9(), class2.method7() + fontMetrics.getAscent());
            this.field2482.put(class2.method12(), (Object)class2);
        }
        Class332.method2073(this.field2485, bufferedImage);
        this.field2487 = true;
    }

    public static void method2073(Identifier i, BufferedImage bi) {
        try {
            int n = bi.getWidth();
            int n2 = bi.getHeight();
            NativeImage nativeImage = new NativeImage(NativeImage.Format.RGBA, n, n2, false);
            long l = nativeImage.pointer;
            IntBuffer intBuffer = MemoryUtil.memIntBuffer((long)l, (int)(nativeImage.getWidth() * nativeImage.getHeight()));
            WritableRaster writableRaster = bi.getRaster();
            ColorModel colorModel = bi.getColorModel();
            int n3 = writableRaster.getNumBands();
            int n4 = writableRaster.getDataBuffer().getDataType();
            /*
            Object[] objectArray = switch (n4) {
                case 0 -> new byte[n3];
                case 1 -> (Object[])new short[n3];
                case 3 -> (Object[])new int[n3];
                case 4 -> (Object[])new float[n3];
                case 5 -> (Object[])new double[n3];
                default -> throw new IllegalArgumentException("Unknown data buffer type: " + n4);
            };


            for (int j = 0; j < n2; ++j) {
                for (int k = 0; k < n; ++k) {
                    writableRaster.getDataElements(k, j, objectArray);
                    int n5 = colorModel.getAlpha(objectArray);
                    int n6 = colorModel.getRed(objectArray);
                    int n7 = colorModel.getGreen(objectArray);
                    int n8 = colorModel.getBlue(objectArray);
                    int n9 = n5 << 24 | n8 << 16 | n7 << 8 | n6;
                    intBuffer.put(n9);
                }
            }
             */
            NativeImageBackedTexture nativeImageBackedTexture = new NativeImageBackedTexture(nativeImage);
            nativeImageBackedTexture.upload();
            if (RenderSystem.isOnRenderThread()) {
                MinecraftClient.getInstance().getTextureManager().registerTexture(i, (AbstractTexture)nativeImageBackedTexture);
            } else {
                RenderSystem.recordRenderCall(() -> MinecraftClient.getInstance().getTextureManager().registerTexture(i, (AbstractTexture)nativeImageBackedTexture));
            }
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
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
        if (this.field2486.canDisplay(c)) {
            return this.field2486;
        }
        return this.field2486;
    }

    public Class2 method2077(char c) {
        if (!this.field2487) {
            this.method2072();
        }
        return (Class2)this.field2482.get(c);
    }
}
