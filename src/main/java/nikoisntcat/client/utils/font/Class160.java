package nikoisntcat.client.utils.font;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.chars.Char2IntArrayMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.awt.Color;
import java.awt.Font;
import java.io.Closeable;
import java.security.MessageDigest;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import nikoisntcat.client.utils.MinecraftUtil;
import nikoisntcat.client.utils.font.Class1;
import nikoisntcat.client.utils.font.Class2;
import nikoisntcat.client.utils.font.Class332;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Class160 extends MinecraftUtil implements Closeable {
    private final int field1574;
    private final Char2ObjectArrayMap field1575;
    private Font field1576;
    private final int field1577;
    private final String field1578;
    private final float field1579;
    private Future field1580;
    private final ObjectList field1581;
    private static final ExecutorService field1582;
    private final Object2ObjectMap field1583 = new Object2ObjectOpenHashMap();
    private boolean field1584;
    private static final Char2IntArrayMap field1585;
    private int field1586;
    private int field1587;
    static Object field1588;

    public void method1163(DrawContext context, String s, float x, float y, float r, float g, float b, float a, boolean gradient, int offset) {
        if (this.field1580 != null && !this.field1580.isDone()) {
            try {
                this.field1580.get();
            } catch (ExecutionException | InterruptedException var42) {
            }
        }

        MatrixStack var11 = context.getMatrices();
        this.method1165();
        float var12 = r;
        float var13 = g;
        float var14 = b;
        var11.push();
        y -= 3.0F;
        var11.translate(method1177((double)x, 1), method1177((double)y, 1), 0.0);
        var11.scale(1.0F / (float)this.field1586, 1.0F / (float)this.field1586, 1.0F);
        Matrix4f var15 = var11.peek().getPositionMatrix();
        char[] var16 = s.toCharArray();
        float var17 = 0.0F;
        float var18 = 0.0F;
        boolean var19 = false;
        int var20 = 0;
        synchronized (this.field1583) {
            for (int var22 = 0; var22 < var16.length; var22++) {
                char var23 = var16[var22];
                if (var19) {
                    var19 = false;
                    char var24 = Character.toUpperCase(var23);
                    if (field1585.containsKey(var24)) {
                        int[] var26 = method1173(field1585.get(var24));
                        var12 = (float)var26[0] / 255.0F;
                        var13 = (float)var26[1] / 255.0F;
                        var14 = (float)var26[2] / 255.0F;
                    } else if (var24 == 'R') {
                        var12 = r;
                        var13 = g;
                        var14 = b;
                    }
                } else {
                    if (gradient) {
                        Color var47 = Color.BLACK;
                        var12 = (float)var47.getRed() / 255.0F;
                        var13 = (float)var47.getGreen() / 255.0F;
                        var14 = (float)var47.getBlue() / 255.0F;
                        a = (float)var47.getAlpha() / 255.0F;
                    }

                    if (var23 == 167) {
                        var19 = true;
                    } else if (var23 == '\n') {
                        var18 += this.method1191(s.substring(var20, var22)) * (float)this.field1586;
                        var17 = 0.0F;
                        var20 = var22 + 1;
                    } else {
                        Class2 var48 = this.method1179(var23);
                        if (var48 != null) {
                            if (var48.method12() != ' ') {
                                Identifier var25 = var48.method11().field2485;
                                Class1 var51 = new Class1(var17, var18, var12, var13, var14, var48);
                                ((ObjectList)this.field1583.computeIfAbsent(var25, integer -> new ObjectArrayList())).add(var51);
                            }

                            var17 += (float)var48.method10();
                        }
                    }
                }
            }

            ObjectIterator var45 = this.field1583.keySet().iterator();

            while (var45.hasNext()) {
                Identifier var46 = (Identifier)var45.next();
                RenderSystem.setShaderTexture(0, var46);
                List<Class1> var49 = (List)this.field1583.get(var46);
                VertexConsumer var50 = context.vertexConsumers.getBuffer(RenderLayer.getGuiTextured(var46));

                for (Class1 var27 : var49) {
                    float var28 = var27.field4;
                    float var29 = var27.field6;
                    float var30 = var27.field1;
                    float var31 = var27.field2;
                    float var32 = var27.field3;
                    Class2 var33 = var27.field5;
                    Class332 var34 = var33.method11();
                    float var35 = (float)var33.method10();
                    float var36 = (float)var33.method8();
                    float var37 = (float)var33.method9() / (float)var34.field2483;
                    float var38 = (float)var33.method7() / (float)var34.field2490;
                    float var39 = (float)(var33.method9() + var33.method10()) / (float)var34.field2483;
                    float var40 = (float)(var33.method7() + var33.method8()) / (float)var34.field2490;
                    var50.vertex(var15, var28 + 0.0F, var29 + var36, 0.0F).texture(var37, var40).color(var30, var31, var32, a);
                    var50.vertex(var15, var28 + var35, var29 + var36, 0.0F).texture(var39, var40).color(var30, var31, var32, a);
                    var50.vertex(var15, var28 + var35, var29 + 0.0F, 0.0F).texture(var39, var38).color(var30, var31, var32, a);
                    var50.vertex(var15, var28 + 0.0F, var29 + 0.0F, 0.0F).texture(var37, var38).color(var30, var31, var32, a);
                }
            }

            this.field1583.clear();
        }

        var11.pop();
    }

    private void method1165() {
        if ((int)mc.getWindow().getScaleFactor() != this.field1587) {
            this.close();
            this.method1170(this.field1576, this.field1579);
        }
    }

    @Contract(
            value = "-> new",
            pure = true
    )
    @NotNull
    public static Identifier method1166() {
        return Identifier.of("client", "temp/" + method1194());
    }

    public void method1167(MatrixStack stack, String s, float x, float y, float r, float g, float b, float a, boolean gradient, int offset) {
        if (this.field1580 != null && !this.field1580.isDone()) {
            try {
                this.field1580.get();
            } catch (ExecutionException | InterruptedException var45) {
            }
        }

        int[] var11 = new int[1];
        int[] var12 = new int[1];
        int[] var13 = new int[1];
        int[] var14 = new int[1];
        GL11.glGetIntegerv(34016, var11);
        GL11.glGetIntegerv(32873, var12);
        GL11.glGetTexParameteriv(3553, 10241, var13);
        GL11.glGetTexParameteriv(3553, 10240, var14);
        this.method1165();
        float var15 = r;
        float var16 = g;
        float var17 = b;
        stack.push();
        y -= 3.0F;
        stack.translate(method1177((double)x, 1), method1177((double)y, 1), 0.0);
        stack.scale(1.0F / (float)this.field1586, 1.0F / (float)this.field1586, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.texParameter(3553, 10241, 9729);
        RenderSystem.texParameter(3553, 10240, 9729);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        Matrix4f var18 = stack.peek().getPositionMatrix();
        char[] var19 = s.toCharArray();
        float var20 = 0.0F;
        float var21 = 0.0F;
        boolean var22 = false;
        int var23 = 0;
        synchronized (this.field1583) {
            for (int var25 = 0; var25 < var19.length; var25++) {
                char var26 = var19[var25];
                if (var22) {
                    var22 = false;
                    char var27 = Character.toUpperCase(var26);
                    if (field1585.containsKey(var27)) {
                        int[] var29 = method1173(field1585.get(var27));
                        var15 = (float)var29[0] / 255.0F;
                        var16 = (float)var29[1] / 255.0F;
                        var17 = (float)var29[2] / 255.0F;
                    } else if (var27 == 'R') {
                        var15 = r;
                        var16 = g;
                        var17 = b;
                    }
                } else {
                    if (gradient) {
                        Color var50 = Color.BLACK;
                        var15 = (float)var50.getRed() / 255.0F;
                        var16 = (float)var50.getGreen() / 255.0F;
                        var17 = (float)var50.getBlue() / 255.0F;
                        a = (float)var50.getAlpha() / 255.0F;
                    }

                    if (var26 == 167) {
                        var22 = true;
                    } else if (var26 == '\n') {
                        var21 += this.method1191(s.substring(var23, var25)) * (float)this.field1586;
                        var20 = 0.0F;
                        var23 = var25 + 1;
                    } else {
                        Class2 var51 = this.method1179(var26);
                        if (var51 != null) {
                            if (var51.method12() != ' ') {
                                Identifier var28 = var51.method11().field2485;
                                Class1 var54 = new Class1(var20, var21, var15, var16, var17, var51);
                                ((ObjectList)this.field1583.computeIfAbsent(var28, integer -> new ObjectArrayList())).add(var54);
                            }

                            var20 += (float)var51.method10();
                        }
                    }
                }
            }

            ObjectIterator var48 = this.field1583.keySet().iterator();

            while (var48.hasNext()) {
                Identifier var49 = (Identifier)var48.next();
                RenderSystem.setShaderTexture(0, var49);
                List<Class1> var52 = (List)this.field1583.get(var49);
                BufferBuilder var30 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

                for (Class1 var55 : var52) {
                    float var31 = var55.field4;
                    float var32 = var55.field6;
                    float var33 = var55.field1;
                    float var34 = var55.field2;
                    float var35 = var55.field3;
                    Class2 var36 = var55.field5;
                    Class332 var37 = var36.method11();
                    float var38 = (float)var36.method10();
                    float var39 = (float)var36.method8();
                    float var40 = (float)var36.method9() / (float)var37.field2483;
                    float var41 = (float)var36.method7() / (float)var37.field2490;
                    float var42 = (float)(var36.method9() + var36.method10()) / (float)var37.field2483;
                    float var43 = (float)(var36.method7() + var36.method8()) / (float)var37.field2490;
                    var30.vertex(var18, var31 + 0.0F, var32 + var39, 0.0F).texture(var40, var43).color(var33, var34, var35, a);
                    var30.vertex(var18, var31 + var38, var32 + var39, 0.0F).texture(var42, var43).color(var33, var34, var35, a);
                    var30.vertex(var18, var31 + var38, var32 + 0.0F, 0.0F).texture(var42, var41).color(var33, var34, var35, a);
                    var30.vertex(var18, var31 + 0.0F, var32 + 0.0F, 0.0F).texture(var40, var41).color(var33, var34, var35, a);
                }

                BufferRenderer.drawWithGlobalProgram(var30.end());
            }

            this.field1583.clear();
        }

        stack.pop();
        GL13.glActiveTexture(var11[0]);
        GL11.glBindTexture(3553, var12[0]);
        GL11.glTexParameteri(3553, 10241, var13[0]);
        GL11.glTexParameteri(3553, 10240, var14[0]);
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
    }

    public void method1168(MatrixStack stack, String s, float x, float y, float r, float g, float b, float a) {
        this.method1190(stack, s, x - this.method1186(s) / 2.0F, y, r, g, b, a);
    }

    public void method1169(MatrixStack stack, String s, double x, double y, Color color) {
        this.method1190(
                stack,
                s,
                (float)(x - (double)(this.method1186(s) / 2.0F)),
                (float)y,
                (float)color.getRed() / 255.0F,
                (float)color.getGreen() / 255.0F,
                (float)color.getBlue() / 255.0F,
                (float)color.getAlpha() / 255.0F
        );
    }

    public Class160(Font font, float sizePx, int charactersPerPage, int paddingBetweenCharacters, @Nullable String prebakeCharacters) {
        this.field1581 = new ObjectArrayList();
        this.field1575 = new Char2ObjectArrayMap();
        this.field1586 = 0;
        this.field1587 = -1;
        this.field1579 = sizePx;
        this.field1577 = charactersPerPage;
        this.field1574 = paddingBetweenCharacters;
        this.field1578 = prebakeCharacters;
        this.method1170(font, sizePx);
    }

    private void method1170(Font font, float sizePx) {
        if (this.field1584) {
            throw new IllegalStateException("Double call to init()");
        } else {
            this.field1584 = true;
            this.field1587 = (int)mc.getWindow().getScaleFactor();
            this.field1586 = this.field1587;
            this.field1576 = font.deriveFont(sizePx * (float)this.field1586);
            if (this.field1578 != null && !this.field1578.isEmpty()) {
                this.field1580 = this.method1176();
            }
        }
    }

    public void method1171(DrawContext context, String s, float x, float y, float r, float g, float b, float a) {
        this.method1163(context, s, x, y, r, g, b, a, false, 0);
    }

    @Contract(
            value = "_ -> new",
            pure = true
    )
    @NotNull
    public static int[] method1173(int in) {
        int var1 = in >> 16 & 0xFF;
        int var2 = in >> 8 & 0xFF;
        int var3 = in & 0xFF;
        return new int[]{var1, var2, var3};
    }

    public void method1174(MatrixStack stack, String s, double x, double y, int color) {
        float var8 = (float)(color >> 16 & 0xFF) / 255.0F;
        float var9 = (float)(color >> 8 & 0xFF) / 255.0F;
        float var10 = (float)(color & 0xFF) / 255.0F;
        float var11 = (float)(color >> 24 & 0xFF) / 255.0F;
        this.method1190(stack, s, (float)(x - (double)(this.method1186(s) / 2.0F)), (float)y, var8, var9, var10, var11);
    }

    public void method1175(DrawContext context, String s, double x, double y, int color) {
        float var8 = (float)(color >> 16 & 0xFF) / 255.0F;
        float var9 = (float)(color >> 8 & 0xFF) / 255.0F;
        float var10 = (float)(color & 0xFF) / 255.0F;
        float var11 = (float)(color >> 24 & 0xFF) / 255.0F;
        this.method1171(context, s, (float)x, (float)y, var8, var9, var10, var11);
    }

    private Future method1176() {
        return field1582.submit(() -> {
            for (char var4 : this.field1578.toCharArray()) {
                if (Thread.interrupted()) {
                    break;
                }

                this.method1179(var4);
            }

            return null;
        });
    }

    static {
        field1585 = new Class312();
        field1582 = Executors.newCachedThreadPool();
    }

    public static double method1177(double n, int point) {
        if (point == 0) {
            return Math.floor(n);
        } else {
            double var3 = Math.pow(10.0, (double)point);
            return (double)Math.round(n * var3) / var3;
        }
    }

    private Class332 method1178(char from, char to) {
        Class332 var3 = new Class332(from, to, this.field1576, method1166(), this.field1574);
        this.field1581.add(var3);
        return var3;
    }

    public void close() {
        try {
            if (this.field1580 != null && !this.field1580.isDone() && !this.field1580.isCancelled()) {
                this.field1580.cancel(true);
                this.field1580.get();
                this.field1580 = null;
            }

            ObjectListIterator var1 = this.field1581.iterator();

            while (var1.hasNext()) {
                ((Class332)var1.next()).method2075();
            }

            this.field1581.clear();
            this.field1575.clear();
            this.field1584 = false;
        } catch (Exception var2) {
        }
    }

    public Class160(Font font, float sizePx) {
        this(font, sizePx, 256, 5, null);
    }

    @Nullable
    private Class2 method1179(char glyph) {
        return (Class2)this.field1575.computeIfAbsent(glyph, this::method1187);
    }

    public void method1180(DrawContext context, String s, double x, double y, Color color) {
        this.method1171(
                context,
                s,
                (float)x,
                (float)y,
                (float)color.getRed() / 255.0F,
                (float)color.getGreen() / 255.0F,
                (float)color.getBlue() / 255.0F,
                (float)color.getAlpha() / 255.0F
        );
    }

    public void method1181(MatrixStack stack, String s, double x, double y, Color color) {
        this.method1190(
                stack,
                s,
                (float)x,
                (float)y,
                (float)color.getRed() / 255.0F,
                (float)color.getGreen() / 255.0F,
                (float)color.getBlue() / 255.0F,
                (float)color.getAlpha() / 255.0F
        );
    }

    public void method1182(MatrixStack matrices, String s, float x, float y, int i) {
        this.method1189(matrices, s, x - this.method1186(s) / 2.0F, y, i);
    }

    public static String method1183(String text) {
        char[] var1 = text.toCharArray();
        StringBuilder var2 = new StringBuilder();

        for (int var3 = 0; var3 < var1.length; var3++) {
            char var4 = var1[var3];
            if (var4 == 167) {
                var3++;
            } else {
                var2.append(var4);
            }
        }

        return var2.toString();
    }

    private static int method1185(int x, int n) {
        return n * (int)Math.floor((double)x / (double)n);
    }

    public float method1186(String text) {
        char[] var2 = method1183(text).toCharArray();
        float var3 = 0.0F;
        float var4 = 0.0F;

        for (char var8 : var2) {
            if (var8 == '\n') {
                var4 = Math.max(var3, var4);
                var3 = 0.0F;
            } else {
                Class2 var9 = this.method1179(var8);
                var3 += var9 == null ? 0.0F : (float)var9.method10() / (float)this.field1586;
            }
        }

        return Math.max(var3, var4);
    }

    private Class2 method1187(char glyph) {
        ObjectListIterator var2 = this.field1581.iterator();

        while (var2.hasNext()) {
            Class332 var3 = (Class332)var2.next();
            if (var3.method2074(glyph)) {
                return var3.method2077(glyph);
            }
        }

        int var4 = method1185(glyph, this.field1577);
        return this.method1178((char)var4, (char)(var4 + this.field1577)).method2077(glyph);
    }

    public float method1188(String str) {
        return this.method1191(str);
    }

    public void method1189(MatrixStack stack, String s, float x, float y, int offset) {
        this.method1167(stack, s, x, y, 255.0F, 255.0F, 255.0F, 255.0F, true, offset);
    }

    public void method1190(MatrixStack stack, String s, float x, float y, float r, float g, float b, float a) {
        this.method1167(stack, s, x, y, r, g, b, a, false, 0);
    }

    public float method1191(String text) {
        char[] var2 = method1183(text).toCharArray();
        if (var2.length == 0) {
            var2 = new char[]{' '};
        }

        float var3 = 0.0F;
        float var4 = 0.0F;

        for (char var8 : var2) {
            if (var8 == '\n') {
                if (var3 == 0.0F) {
                    var3 = this.method1179(' ') == null ? 0.0F : (float)((Class2)Objects.requireNonNull(this.method1179(' '))).method8() / (float)this.field1586;
                }

                var4 += var3;
                var3 = 0.0F;
            } else {
                Class2 var9 = this.method1179(var8);
                var3 = Math.max(var9 == null ? 0.0F : (float)var9.method8() / (float)this.field1586, var3);
            }
        }

        return var3 + var4 - 3.0F;
    }

    public void method1192(MatrixStack stack, String s, double x, double y, int color) {
        float var8 = (float)(color >> 16 & 0xFF) / 255.0F;
        float var9 = (float)(color >> 8 & 0xFF) / 255.0F;
        float var10 = (float)(color & 0xFF) / 255.0F;
        float var11 = (float)(color >> 24 & 0xFF) / 255.0F;
        this.method1190(stack, s, (float)x, (float)y, var8, var9, var10, var11);
    }

    private static String method1194() {
        return (String)IntStream.range(0, 32).mapToObj(operand -> String.valueOf((char)new Random().nextInt(97, 123))).collect(Collectors.joining());
    }
}
