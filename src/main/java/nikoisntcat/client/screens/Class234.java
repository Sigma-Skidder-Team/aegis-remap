package nikoisntcat.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;

import java.awt.Color;
import java.util.function.Function;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhase;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.TriState;
import net.minecraft.util.Util;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.impl.KillAuraModule;
import nikoisntcat.client.utils.MinecraftUtil;
import nikoisntcat.client.utils.font.Class160;
import nikoisntcat.client.utils.font.Class318;
import nikoisntcat.client.utils.math.Class281;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Class234 extends MinecraftUtil {
    private static final MultiPhase field2050 = RenderLayer.of(
            "debug_triangle_strip",
            VertexFormats.POSITION_COLOR,
            DrawMode.TRIANGLE_STRIP,
            1536,
            false,
            true,
            RenderLayer.MultiPhaseParameters.builder()
                    .program(RenderPhase.POSITION_COLOR_PROGRAM)
                    .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                    .cull(RenderPhase.DISABLE_CULLING)
                    .build(false)
    );
    ;
    private static final Function field2051 = Util.memoize(
            texture -> RenderLayer.of(
                    "gui_textured",
                    VertexFormats.POSITION_TEXTURE_COLOR,
                    DrawMode.TRIANGLE_FAN,
                    786432,
                    RenderLayer.MultiPhaseParameters.builder()
                            .texture(new RenderPhase.Texture((Identifier) texture, TriState.FALSE, false))
                            .program(RenderPhase.POSITION_TEXTURE_COLOR_PROGRAM)
                            .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                            .cull(RenderPhase.DISABLE_CULLING)
                            .depthTest(RenderPhase.LEQUAL_DEPTH_TEST)
                            .build(false)
            )
    );

    private static void method1561(DrawContext context, float x, float y, float width, float height, float radius, int color, float thickness) {
        MatrixStack var8 = context.getMatrices();
        float var9 = (float) (color >> 24 & 0xFF) / 255.0F;
        float var10 = (float) (color >> 16 & 0xFF) / 255.0F;
        float var11 = (float) (color >> 8 & 0xFF) / 255.0F;
        float var12 = (float) (color & 0xFF) / 255.0F;
        VertexConsumer var13 = context.vertexConsumers.getBuffer(field2050);
        float var14 = radius;
        float var15 = Math.max(0.0F, radius - thickness);
        float var16 = x;
        float var17 = x + width;
        float var18 = y;
        float var19 = y + height;
        float var20 = x + thickness;
        float var21 = x + width - thickness;
        float var22 = y + thickness;
        float var23 = y + height - thickness;

        for (int var25 = 0; var25 <= 10; var25++) {
            float var26 = (float) ((double) var25 * Math.PI / 2.0 / 10.0);
            float var27 = MathHelper.sin(var26);
            float var28 = MathHelper.cos(var26);
            var13.vertex(var8.peek().getPositionMatrix(), var17 - var14 + var27 * var14, var18 + var14 - var28 * var14, 0.0F)
                    .color(var10, var11, var12, var9);
            var13.vertex(var8.peek().getPositionMatrix(), var21 - var15 + var27 * var15, var22 + var15 - var28 * var15, 0.0F)
                    .color(var10, var11, var12, var9);
        }

        for (int var29 = 0; var29 <= 10; var29++) {
            float var32 = (float) ((double) var29 * Math.PI / 2.0 / 10.0);
            float var35 = MathHelper.sin(var32);
            float var38 = MathHelper.cos(var32);
            var13.vertex(var8.peek().getPositionMatrix(), var17 - var14 + var38 * var14, var19 - var14 + var35 * var14, 0.0F)
                    .color(var10, var11, var12, var9);
            var13.vertex(var8.peek().getPositionMatrix(), var21 - var15 + var38 * var15, var23 - var15 + var35 * var15, 0.0F)
                    .color(var10, var11, var12, var9);
        }

        for (int var30 = 0; var30 <= 10; var30++) {
            float var33 = (float) ((double) var30 * Math.PI / 2.0 / 10.0);
            float var36 = MathHelper.sin(var33);
            float var39 = MathHelper.cos(var33);
            var13.vertex(var8.peek().getPositionMatrix(), var16 + var14 - var36 * var14, var19 - var14 + var39 * var14, 0.0F)
                    .color(var10, var11, var12, var9);
            var13.vertex(var8.peek().getPositionMatrix(), var20 + var15 - var36 * var15, var23 - var15 + var39 * var15, 0.0F)
                    .color(var10, var11, var12, var9);
        }

        for (int var31 = 0; var31 <= 10; var31++) {
            float var34 = (float) ((double) var31 * Math.PI / 2.0 / 10.0);
            float var37 = MathHelper.sin(var34);
            float var40 = MathHelper.cos(var34);
            var13.vertex(var8.peek().getPositionMatrix(), var16 + var14 - var40 * var14, var18 + var14 - var37 * var14, 0.0F)
                    .color(var10, var11, var12, var9);
            var13.vertex(var8.peek().getPositionMatrix(), var20 + var15 - var40 * var15, var22 + var15 - var37 * var15, 0.0F)
                    .color(var10, var11, var12, var9);
        }

        var13.vertex(var8.peek().getPositionMatrix(), var17 - var14, var18, 0.0F).color(var10, var11, var12, var9);
        var13.vertex(var8.peek().getPositionMatrix(), var21 - var15, var22, 0.0F).color(var10, var11, var12, var9);
    }

    public static void method1562(
            MatrixStack matrixStack, double entityX, double entityY, double entityZ, LivingEntity entity, float scale, String customName, float output
    ) {
        Class160 var11 = Class318.field2406;
        String var12 = customName == null ? entity.getName().getString().replaceAll("§.", "") : customName;
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        if (!var12.isEmpty()) {
            float var13 = (float) (entityX - mc.gameRenderer.getCamera().getPos().getX());
            float var14 = (float) (entityY - mc.gameRenderer.getCamera().getPos().getY());
            float var15 = (float) (entityZ - mc.gameRenderer.getCamera().getPos().getZ());
            boolean var16 = entity == KillAuraModule.field1611;
            Color var17 = var16 ? Color.getHSBColor((float) (System.currentTimeMillis() % 2000L) / 2000.0F, 0.4F, 1.0F) : new Color(-65794);
            String var18 = String.format("%.1f", entity.getHealth());
            float var19 = Math.min(entity.getHealth() / entity.getMaxHealth(), 1.0F);
            matrixStack.push();
            matrixStack.translate(var13, var14 + 0.6F - 0.33333334F * (1.0F - scale), var15);
            method1580(matrixStack, (double) mc.gameRenderer.getCamera().getYaw(), 0.0F, -1.0F, 0.0F);
            method1580(matrixStack, (double) mc.gameRenderer.getCamera().getPitch(), 1.0F, 0.0F, 0.0F);
            matrixStack.scale(-0.009F * scale, -0.009F * scale, -0.009F * scale);
            int var20 = (int) (var11.method1186(var12) / 2.0F);
            float var21 = 0.0F;
            if (entity instanceof AbstractClientPlayerEntity && output > 0.0F) {
                var21 = 30.0F * output;
            }

            float var22 = (float) (-var20 - 10) - var21;
            matrixStack.push();
            method1587(matrixStack.peek().getPositionMatrix(), var22, -25.0F, (float) (var20 + 10), var11.method1188(var12) + 2.0F, -870178270);
            float var23 = (float) (var20 + 10) - ((float) (-var20 - 10) - var21);
            method1587(
                    matrixStack.peek().getPositionMatrix(),
                    var22,
                    var11.method1188(var12) - 1.0F - (float) entity.hurtTime / 3.0F,
                    var22 + var23 * var19,
                    var11.method1188(var12) + 2.0F,
                    -13378253
            );
            matrixStack.pop();
            if (entity instanceof AbstractClientPlayerEntity var24 && output > 0.0F) {
                method1582(matrixStack, var24, var22 + 5.0F, -20.0F, 30.0F * output, 30.0F, 0.0F, 0, 0.0F);
            }

            matrixStack.push();
            matrixStack.translate((double) (-var11.method1186(var12) / 2.0F), 0.0, 0.0);
            if (var16) {
                int var28 = Color.BLACK.getRGB();

                for (int var26 = -1; var26 <= 1; var26++) {
                    for (int var27 = -1; var27 <= 1; var27++) {
                        if (var26 != 0 || var27 != 0) {
                            var11.method1192(matrixStack, var12, (double) ((float) var26 * 1.4F), (double) (-20.0F + (float) var27 * 1.4F), var28);
                        }
                    }
                }
            }

            var11.method1192(matrixStack, var12, 0.0, -20.0, var17.getRGB());
            int var29 = (int) Class318.field2410.method1186("Health: 20.0");
            String var25 = "Health: ";
            if (var29 > var20) {
                var25 = "H: ";
            }

            Class318.field2410.method1192(matrixStack, var25 + var18, 0.0, 2.0, -3355444);
            matrixStack.pop();
            matrixStack.pop();
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
            RenderSystem.depthMask(true);
        }
    }

    public static boolean method1563(Framebuffer framebuffer) {
        return framebuffer == null || framebuffer.viewportWidth != mc.getWindow().getFramebufferWidth() || framebuffer.viewportHeight != mc.getWindow().getFramebufferHeight();
    }

    public static double method1564(DrawContext drawContext, int x, int y, String text, String title, String iconCode, float percent) {
        Class160 var7 = Class318.field2425;
        Class160 var8 = Class318.field2426;
        int var9 = (int) var7.method1191(iconCode) + 5;
        int var10 = (int) var7.method1186(iconCode);
        int var12 = (int) var8.method1186(title) + 8;
        int var13 = (int) var8.method1186(text) + 8;
        int var14 = Math.max(var12, var13);
        int var15 = var10 + var14 + 20;
        Color var16 = new Color(48, 253, 0, 255);
        Color var17 = new Color(0, 150, 0, 255);
        Color var18 = new Color(0, 191, 0, 255);
        Color var19 = new Color(148, 255, 167, 255);
        Color var20 = new Color(150, 150, 150, 255);
        switch (iconCode) {
            case "\uE900":
                var16 = new Color(255, 0, 0);
                var17 = new Color(150, 0, 0);
                var18 = new Color(191, 0, 0);
                var19 = new Color(255, 0, 0);
                break;
            case "\uE902":
                var16 = new Color(255, 255, 0);
                var17 = new Color(150, 150, 0);
                var18 = new Color(191, 191, 0);
                var19 = new Color(250, 255, 0);
                break;
            case "\uE903":
                var16 = new Color(0, 150, 255);
                var17 = new Color(0, 50, 205);
                var18 = var16;
                var19 = var16;
                break;
            default:
                break;
        }

        drawContext.fill(x + 2, y + var9, x + var15, y + var9 + 1, var17.getRGB());
        drawContext.fill((int) ((float) (x + var15) - (float) var15 * percent), y + var9, x + var15, y + var9 + 1, var16.getRGB());
        drawContext.fill(x + 2, y, x + var15, y + var9, new Color(15, 15, 15, 200).getRGB());
        drawContext.fill(x, y, x + 2, y + var9 + 1, var16.getRGB());
        var8.method1175(drawContext, title, (double) (x + var10 + 8), (double) (y + 3), var18.getRGB());
        var8.method1175(drawContext, text, (double) (x + var10 + 8), (double) ((float) y + 3.0F + var8.method1191(title)), var20.getRGB());
        var7.method1175(drawContext, iconCode, (double) (x + 2 + 1), (double) (y + 4), var19.getRGB());
        return (double) var15;
    }

    public static void method1565(MatrixStack matrixStack, float x, float y, float width, float height, float radius, int color, float borderWidth) {
        matrixStack.push();
        float var10000 = (float) (color >> 24 & 0xFF) / 255.0F;
        var10000 = (float) (color >> 16 & 0xFF) / 255.0F;
        var10000 = (float) (color >> 8 & 0xFF) / 255.0F;
        var10000 = (float) (color & 0xFF) / 255.0F;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        method1571(matrixStack, x, y, width, height, radius, color, borderWidth);
        RenderSystem.disableBlend();
        matrixStack.pop();
    }

    public static Framebuffer method1567(Framebuffer framebuffer) {
        return method1584(framebuffer, false);
    }

    public static void method1568(DrawContext context, float x, float y, float width, float height, int color) {
        method1574(context, x, y, x + width, y + height, 0.0F, color);
    }

    public static Vec3d method1569(Vec3d preVec3d, Vec3d targetVec3d, double renderPartialTicks) {
        return new Vec3d(
                preVec3d.x + (targetVec3d.x - preVec3d.x) * renderPartialTicks,
                preVec3d.y + (targetVec3d.y - preVec3d.y) * renderPartialTicks,
                preVec3d.z + (targetVec3d.z - preVec3d.z) * renderPartialTicks
        );
    }

    public static Vec3d method1570(Entity entity, double renderPartialTicks) {
        return new Vec3d(
                entity.prevX + (entity.getX() - entity.prevX) * renderPartialTicks,
                entity.prevY + (entity.getY() - entity.prevY) * renderPartialTicks,
                entity.prevZ + (entity.getZ() - entity.prevZ) * renderPartialTicks
        );
    }

    private static void method1571(MatrixStack matrixStack, float x, float y, float width, float height, float radius, int color, float thickness) {
        float var8 = (float) (color >> 24 & 0xFF) / 255.0F;
        float var9 = (float) (color >> 16 & 0xFF) / 255.0F;
        float var10 = (float) (color >> 8 & 0xFF) / 255.0F;
        float var11 = (float) (color & 0xFF) / 255.0F;
        BufferBuilder var13 = Tessellator.getInstance().begin(DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        float var14 = radius;
        float var15 = Math.max(0.0F, radius - thickness);
        float var16 = x;
        float var17 = x + width;
        float var18 = y;
        float var19 = y + height;
        float var20 = x + thickness;
        float var21 = x + width - thickness;
        float var22 = y + thickness;
        float var23 = y + height - thickness;

        for (int var25 = 0; var25 <= 10; var25++) {
            float var26 = (float) ((double) var25 * Math.PI / 2.0 / 10.0);
            float var27 = MathHelper.sin(var26);
            float var28 = MathHelper.cos(var26);
            var13.vertex(matrixStack.peek().getPositionMatrix(), var17 - var14 + var27 * var14, var18 + var14 - var28 * var14, 0.0F)
                    .color(var9, var10, var11, var8);
            var13.vertex(matrixStack.peek().getPositionMatrix(), var21 - var15 + var27 * var15, var22 + var15 - var28 * var15, 0.0F)
                    .color(var9, var10, var11, var8);
        }

        for (int var29 = 0; var29 <= 10; var29++) {
            float var33 = (float) ((double) var29 * Math.PI / 2.0 / 10.0);
            float var36 = MathHelper.sin(var33);
            float var39 = MathHelper.cos(var33);
            var13.vertex(matrixStack.peek().getPositionMatrix(), var17 - var14 + var39 * var14, var19 - var14 + var36 * var14, 0.0F)
                    .color(var9, var10, var11, var8);
            var13.vertex(matrixStack.peek().getPositionMatrix(), var21 - var15 + var39 * var15, var23 - var15 + var36 * var15, 0.0F)
                    .color(var9, var10, var11, var8);
        }

        for (int var30 = 0; var30 <= 10; var30++) {
            float var34 = (float) ((double) var30 * Math.PI / 2.0 / 10.0);
            float var37 = MathHelper.sin(var34);
            float var40 = MathHelper.cos(var34);
            var13.vertex(matrixStack.peek().getPositionMatrix(), var16 + var14 - var37 * var14, var19 - var14 + var40 * var14, 0.0F)
                    .color(var9, var10, var11, var8);
            var13.vertex(matrixStack.peek().getPositionMatrix(), var20 + var15 - var37 * var15, var23 - var15 + var40 * var15, 0.0F)
                    .color(var9, var10, var11, var8);
        }

        for (int var31 = 0; var31 <= 10; var31++) {
            float var35 = (float) ((double) var31 * Math.PI / 2.0 / 10.0);
            float var38 = MathHelper.sin(var35);
            float var41 = MathHelper.cos(var35);
            var13.vertex(matrixStack.peek().getPositionMatrix(), var16 + var14 - var41 * var14, var18 + var14 - var38 * var14, 0.0F)
                    .color(var9, var10, var11, var8);
            var13.vertex(matrixStack.peek().getPositionMatrix(), var20 + var15 - var41 * var15, var22 + var15 - var38 * var15, 0.0F)
                    .color(var9, var10, var11, var8);
        }

        var13.vertex(matrixStack.peek().getPositionMatrix(), var17 - var14, var18, 0.0F).color(var9, var10, var11, var8);
        var13.vertex(matrixStack.peek().getPositionMatrix(), var21 - var15, var22, 0.0F).color(var9, var10, var11, var8);
        BuiltBuffer var32 = var13.endNullable();
        if (var32 != null) {
            BufferRenderer.drawWithGlobalProgram(var32);
        }
    }

    public static void method1572(DrawContext context, AbstractClientPlayerEntity player, int x, int y, TextRenderer textRenderer) {
        context.getMatrices().push();
        method1585(context, (float) x, (float) y, 200.0F, 60.0F, 10.0F, -869651926);
        int var9 = x + 5;
        int var10 = y + 5;
        method1585(context, (float) var9, (float) var10, 50.0F, 50.0F, 8.0F, -12829636);
        method1586(context, player, (float) (var9 + 2), (float) (var10 + 2), 46.0F, 46.0F, 6.0F, 0, 0.0F);
        int var12 = var9 + 50 + 15;
        int var13 = var10 + 8;
        String var14 = player.getName().getString();
        context.drawText(textRenderer, var14, var12, var13, -1, false);
        int var15 = (int) player.getHealth();
        int var16 = (int) player.getMaxHealth();
        String var17 = "HP: " + var15 + "/" + var16;
        context.drawText(textRenderer, var17, var12, var13 + 16, -10040218, false);
        String var18 = "Level: " + player.experienceLevel;
        context.drawText(textRenderer, var18, var12, var13 + 28, -10040065, false);
        int var20 = y + 60 - 3;
        method1568(context, (float) x, (float) var20, 200.0F, 3.0F, -10590465);
        context.getMatrices().pop();
    }

    public static void method1573(
            DrawContext context,
            Identifier texture,
            float x,
            float y,
            float width,
            float height,
            float radius,
            float u,
            float v,
            float textureWidth,
            float textureHeight,
            float imageWidth,
            float imageHeight
    ) {
        MatrixStack var13 = context.getMatrices();
        var13.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        VertexConsumer var14 = context.vertexConsumers.getBuffer((RenderLayer) field2051.apply(texture));

        float var20 = x + radius;
        float var21 = x + width - radius;
        float var22 = y + radius;
        float var23 = y + height - radius;
        float var24 = u / imageWidth;
        float var25 = v / imageHeight;
        float var26 = (u + textureWidth) / imageWidth;
        float var27 = (v + textureHeight) / imageHeight;

        for (int var28 = 0; var28 <= 10; var28++) {
            float var29 = (float) ((double) var28 * Math.PI / 2.0 / 10.0);
            float var30 = MathHelper.sin(var29);
            float var31 = MathHelper.cos(var29);
            float var32 = var21 + var30 * radius;
            float var33 = var22 - var31 * radius;
            float var34 = (var32 - x) / width;
            float var35 = (var33 - y) / height;
            float var36 = var24 + var34 * (var26 - var24);
            float var37 = var25 + var35 * (var27 - var25);
            var14.vertex(var13.peek().getPositionMatrix(), var32, var33, 0.0F).texture(var36, var37).color(255, 255, 255, 255);
        }

        for (int var38 = 0; var38 <= 10; var38++) {
            float var41 = (float) ((double) var38 * Math.PI / 2.0 / 10.0);
            float var45 = MathHelper.sin(var41);
            float var49 = MathHelper.cos(var41);
            float var53 = var21 + var49 * radius;
            float var57 = var23 + var45 * radius;
            float var61 = (var53 - x) / width;
            float var64 = (var57 - y) / height;
            float var67 = var24 + var61 * (var26 - var24);
            float var70 = var25 + var64 * (var27 - var25);
            var14.vertex(var13.peek().getPositionMatrix(), var53, var57, 0.0F).texture(var67, var70).color(255, 255, 255, 255);
        }

        for (int var39 = 0; var39 <= 10; var39++) {
            float var42 = (float) ((double) var39 * Math.PI / 2.0 / 10.0);
            float var46 = MathHelper.sin(var42);
            float var50 = MathHelper.cos(var42);
            float var54 = var20 - var46 * radius;
            float var58 = var23 + var50 * radius;
            float var62 = (var54 - x) / width;
            float var65 = (var58 - y) / height;
            float var68 = var24 + var62 * (var26 - var24);
            float var71 = var25 + var65 * (var27 - var25);
            var14.vertex(var13.peek().getPositionMatrix(), var54, var58, 0.0F).texture(var68, var71).color(255, 255, 255, 255);
        }

        for (int var40 = 0; var40 <= 10; var40++) {
            float var43 = (float) ((double) var40 * Math.PI / 2.0 / 10.0);
            float var47 = MathHelper.sin(var43);
            float var51 = MathHelper.cos(var43);
            float var55 = var20 - var51 * radius;
            float var59 = var22 - var47 * radius;
            float var63 = (var55 - x) / width;
            float var66 = (var59 - y) / height;
            float var69 = var24 + var63 * (var26 - var24);
            float var72 = var25 + var66 * (var27 - var25);
            var14.vertex(var13.peek().getPositionMatrix(), var55, var59, 0.0F).texture(var69, var72).color(255, 255, 255, 255);
        }

        float var44 = var22 - radius;
        float var48 = (var21 - x) / width;
        float var52 = (var44 - y) / height;
        float var56 = var24 + var48 * (var26 - var24);
        float var60 = var25 + var52 * (var27 - var25);
        var14.vertex(var13.peek().getPositionMatrix(), var21, var44, 0.0F).texture(var56, var60).color(255, 255, 255, 255);
        RenderSystem.disableBlend();
        var13.pop();
    }

    public static void method1574(DrawContext context, float x1, float y1, float x2, float y2, float z, int color) {
        RenderLayer var7 = RenderLayer.getGui();
        Matrix4f var8 = context.getMatrices().peek().getPositionMatrix();
        if (x1 < x2) {
            float var9 = x1;
            x1 = x2;
            x2 = var9;
        }

        if (y1 < y2) {
            float var10 = y1;
            y1 = y2;
            y2 = var10;
        }

        VertexConsumer var11 = context.vertexConsumers.getBuffer(var7);
        var11.vertex(var8, x1, y1, z).color(color);
        var11.vertex(var8, x1, y2, z).color(color);
        var11.vertex(var8, x2, y2, z).color(color);
        var11.vertex(var8, x2, y1, z).color(color);
    }

    public static void method1575(
            MatrixStack matrixStack, double entityX, double entityY, double entityZ, LivingEntity entity, float scale, String customName, float output
    ) {
        Class160 var11 = Class318.field2419;
        String var12 = customName == null ? entity.getName().getString().replaceAll("§.", "") : customName;
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        if (!var12.isEmpty()) {
            float var13 = (float) (entityX - mc.gameRenderer.getCamera().getPos().getX());
            float var14 = (float) (entityY - mc.gameRenderer.getCamera().getPos().getY());
            float var15 = (float) (entityZ - mc.gameRenderer.getCamera().getPos().getZ());
            RenderSystem.disableCull();
            String var16 = (float) Math.round(entity.getHealth() * 10.0F) / 10.0F + "";
            float var17 = Math.min(entity.getHealth() / entity.getMaxHealth(), 1.0F);
            matrixStack.push();
            matrixStack.translate(var13, var14 + 0.6F - 0.33333334F * (1.0F - scale), var15);
            method1580(matrixStack, (double) mc.gameRenderer.getCamera().getYaw(), 0.0F, -1.0F, 0.0F);
            method1580(matrixStack, (double) mc.gameRenderer.getCamera().getPitch(), 1.0F, 0.0F, 0.0F);
            matrixStack.scale(-0.009F * scale, -0.009F * scale, -0.009F * scale);
            int var20 = (int) (var11.method1186(var12) / 2.0F);
            float var21 = 0.0F;
            if (entity instanceof AbstractClientPlayerEntity && output > 0.0F) {
                var21 = 30.0F * output;
            }

            float var22 = (float) (-var20 - 10) - var21;
            matrixStack.push();
            method1587(matrixStack.peek().getPositionMatrix(), var22, -25.0F, (float) (var20 + 10), var11.method1188(var12) + 2.0F, 2132811808);
            float var23 = (float) (var20 + 10) - ((float) (-var20 - 10) - var21);
            method1587(
                    matrixStack.peek().getPositionMatrix(),
                    var22,
                    var11.method1188(var12) - 1.0F - (float) entity.hurtTime / 3.0F,
                    var22 + var23 * var17,
                    var11.method1188(var12) + 2.0F,
                    Integer.MAX_VALUE
            );
            matrixStack.pop();
            if (entity instanceof AbstractClientPlayerEntity var24 && output > 0.0F) {
                method1582(matrixStack, var24, var22 + 5.0F, -20.0F, 30.0F * output, 30.0F, 0.0F, 0, 0.0F);
            }

            matrixStack.push();
            matrixStack.translate((double) (-var11.method1186(var12) / 2.0F), 0.0, 0.0);
            int var26 = (int) Class318.field2410.method1186("Health: 20.0");
            String var25 = "Health: ";
            if (var26 > var20) {
                var25 = "H: ";
            }

            Class318.field2410.method1192(matrixStack, var25 + var16, 0.0, 2.0, -65794);
            var11.method1192(matrixStack, var12, 0.0, -20.0, -65794);
            matrixStack.pop();
            matrixStack.pop();
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.enableCull();
            RenderSystem.depthMask(true);
        }
    }

    public static void method1576(Matrix4f matrix4f, Vec3d vec3d, Color color, double r, double light) {
        BufferBuilder var8 = Tessellator.getInstance().begin(DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        RenderSystem.depthMask(false);
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        Color var9 = color;
        double var10 = vec3d.x - mc.getEntityRenderDispatcher().camera.getPos().getX();
        double var12 = vec3d.y - mc.getEntityRenderDispatcher().camera.getPos().getY();
        double var14 = vec3d.z - mc.getEntityRenderDispatcher().camera.getPos().getZ();

        for (float var17 = 0.0F; (double) var17 <= 6.479534853492872; var17 += (float) (Math.PI / 16)) {
            double var18 = var10 + r * Math.cos((double) var17);
            double var20 = var14 + r * Math.sin((double) var17);
            var8.vertex(matrix4f, (float) var18, (float) var12, (float) var20)
                    .color(new Color(var9.getRed(), var9.getGreen(), var9.getBlue(), (int) ((float) var9.getAlpha() * 0.25F)).getRGB());
        }

        for (float var22 = 0.0F; (double) var22 <= 6.479534853492872; var22 += (float) (Math.PI / 16)) {
            double var24 = var10 + r * Math.cos((double) var22);
            double var25 = var14 + r * Math.sin((double) var22);
            var8.vertex(matrix4f, (float) var24, (float) var12, (float) var25).color(var9.getRGB());
            var8.vertex(matrix4f, (float) var24, (float) (var12 - light), (float) var25)
                    .color(new Color(var9.getRed(), var9.getGreen(), var9.getBlue(), 0).getRGB());
        }

        BuiltBuffer var23 = var8.endNullable();
        if (var23 != null) {
            BufferRenderer.drawWithGlobalProgram(var23);
        }

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
    }

    public static void method1577(
            MatrixStack matrixStack,
            Identifier texture,
            float x,
            float y,
            float width,
            float height,
            float radius,
            float u,
            float v,
            float textureWidth,
            float textureHeight,
            float imageWidth,
            float imageHeight
    ) {
        matrixStack.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, texture);
        BufferBuilder var14 = Tessellator.getInstance().begin(DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_TEXTURE_COLOR);
        float var15 = x + width / 2.0F;
        float var16 = y + height / 2.0F;
        float var17 = (u + textureWidth / 2.0F) / imageWidth;
        float var18 = (v + textureHeight / 2.0F) / imageHeight;
        var14.vertex(matrixStack.peek().getPositionMatrix(), var15, var16, 0.0F).texture(var17, var18).color(255, 255, 255, 255);
        float var20 = x + radius;
        float var21 = x + width - radius;
        float var22 = y + radius;
        float var23 = y + height - radius;
        float var24 = u / imageWidth;
        float var25 = v / imageHeight;
        float var26 = (u + textureWidth) / imageWidth;
        float var27 = (v + textureHeight) / imageHeight;

        for (int var28 = 0; var28 <= 10; var28++) {
            float var29 = (float) ((double) var28 * Math.PI / 2.0 / 10.0);
            float var30 = MathHelper.sin(var29);
            float var31 = MathHelper.cos(var29);
            float var32 = var21 + var30 * radius;
            float var33 = var22 - var31 * radius;
            float var34 = (var32 - x) / width;
            float var35 = (var33 - y) / height;
            float var36 = var24 + var34 * (var26 - var24);
            float var37 = var25 + var35 * (var27 - var25);
            var14.vertex(matrixStack.peek().getPositionMatrix(), var32, var33, 0.0F).texture(var36, var37).color(255, 255, 255, 255);
        }

        for (int var38 = 0; var38 <= 10; var38++) {
            float var41 = (float) ((double) var38 * Math.PI / 2.0 / 10.0);
            float var45 = MathHelper.sin(var41);
            float var49 = MathHelper.cos(var41);
            float var53 = var21 + var49 * radius;
            float var57 = var23 + var45 * radius;
            float var61 = (var53 - x) / width;
            float var65 = (var57 - y) / height;
            float var68 = var24 + var61 * (var26 - var24);
            float var71 = var25 + var65 * (var27 - var25);
            var14.vertex(matrixStack.peek().getPositionMatrix(), var53, var57, 0.0F).texture(var68, var71).color(255, 255, 255, 255);
        }

        for (int var39 = 0; var39 <= 10; var39++) {
            float var42 = (float) ((double) var39 * Math.PI / 2.0 / 10.0);
            float var46 = MathHelper.sin(var42);
            float var50 = MathHelper.cos(var42);
            float var54 = var20 - var46 * radius;
            float var58 = var23 + var50 * radius;
            float var62 = (var54 - x) / width;
            float var66 = (var58 - y) / height;
            float var69 = var24 + var62 * (var26 - var24);
            float var72 = var25 + var66 * (var27 - var25);
            var14.vertex(matrixStack.peek().getPositionMatrix(), var54, var58, 0.0F).texture(var69, var72).color(255, 255, 255, 255);
        }

        for (int var40 = 0; var40 <= 10; var40++) {
            float var43 = (float) ((double) var40 * Math.PI / 2.0 / 10.0);
            float var47 = MathHelper.sin(var43);
            float var51 = MathHelper.cos(var43);
            float var55 = var20 - var51 * radius;
            float var59 = var22 - var47 * radius;
            float var63 = (var55 - x) / width;
            float var67 = (var59 - y) / height;
            float var70 = var24 + var63 * (var26 - var24);
            float var73 = var25 + var67 * (var27 - var25);
            var14.vertex(matrixStack.peek().getPositionMatrix(), var55, var59, 0.0F).texture(var70, var73).color(255, 255, 255, 255);
        }

        float var44 = var22 - radius;
        float var48 = (var21 - x) / width;
        float var52 = (var44 - y) / height;
        float var56 = var24 + var48 * (var26 - var24);
        float var60 = var25 + var52 * (var27 - var25);
        var14.vertex(matrixStack.peek().getPositionMatrix(), var21, var44, 0.0F).texture(var56, var60).color(255, 255, 255, 255);
        BuiltBuffer var64 = var14.endNullable();
        if (var64 != null) {
            BufferRenderer.drawWithGlobalProgram(var64);
        }

        RenderSystem.disableBlend();
        matrixStack.pop();
    }

    public static void method1578(
            MatrixStack matrixStack, float x, float y, float width, float height, float radius, int color, float borderWidth, int borderColor
    ) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float var9 = (float) (color >> 16 & 0xFF) / 255.0F;
        float var10 = (float) (color >> 8 & 0xFF) / 255.0F;
        float var11 = (float) (color & 0xFF) / 255.0F;
        float var12 = (float) (color >> 24 & 0xFF) / 255.0F;
        float var13 = (float) (borderColor >> 16 & 0xFF) / 255.0F;
        float var14 = (float) (borderColor >> 8 & 0xFF) / 255.0F;
        float var15 = (float) (borderColor & 0xFF) / 255.0F;
        float var16 = (float) (borderColor >> 24 & 0xFF) / 255.0F;

        Class281 var17 = AegisClient.field2313;
        var17.method1796("rounded");
        var17.method1800("Size", width, height);
        var17.method1800("Radius", radius);
        var17.method1800("ColorOverride", var9, var10, var11, var12);
        var17.method1800("BorderWidth", borderWidth);
        var17.method1800("BorderColor", var13, var14, var15, var16);
        var17.method1786("ModelViewMat", RenderSystem.getModelViewMatrix());
        var17.method1786("ProjMat", RenderSystem.getProjectionMatrix());

        Matrix4f var18 = matrixStack.peek().getPositionMatrix();
        BufferBuilder var19 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        var19.vertex(var18, x, y + height, 0.0F).texture(0.0F, 1.0F).color(var9, var10, var11, var12);
        var19.vertex(var18, x + width, y + height, 0.0F).texture(1.0F, 1.0F).color(var9, var10, var11, var12);
        var19.vertex(var18, x + width, y, 0.0F).texture(1.0F, 0.0F).color(var9, var10, var11, var12);
        var19.vertex(var18, x, y, 0.0F).texture(0.0F, 0.0F).color(var9, var10, var11, var12);
        BufferRenderer.drawWithGlobalProgram(var19.endNullable());
        RenderSystem.disableBlend();
    }

    public static void method1579(DrawContext context, float x, float y, float x2, float y2, int color) {
        method1574(context, x, y, x2, y2, 0.0F, color);
    }

    public static void method1580(MatrixStack matrices, double angle, float x, float y, float z) {
        matrices.multiply(RotationAxis.of(new Vector3f(x, y, z)).rotationDegrees((float) angle));
    }

    public static void method1581(DrawContext context, float x, float y, float width, float height, float radius, int color, float borderWidth) {
        MatrixStack var8 = context.getMatrices();
        var8.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        method1561(context, x, y, width, height, radius, color, borderWidth);
        RenderSystem.disableBlend();
        var8.pop();
    }

    public static void method1582(
            MatrixStack matrixStack, AbstractClientPlayerEntity player, float x, float y, float width, float height, float radius, int borderColor, float borderWidth
    ) {
        Identifier var9 = player.getSkinTextures().texture();
        method1577(matrixStack, var9, x, y, width, height, radius, 8.0F, 8.0F, 8.0F, 8.0F, 64.0F, 64.0F);
        if (borderColor != 0 && borderWidth > 0.0F) {
            method1565(matrixStack, x, y, width, height, radius, borderColor, borderWidth);
        }
    }

    public static void method1583(BufferBuilder bufferBuilder, Box box, Matrix4f m, Color c, boolean fix) {
        float var5 = (float) (box.minX - mc.getEntityRenderDispatcher().camera.getPos().getX());
        float var6 = (float) (box.minY - mc.getEntityRenderDispatcher().camera.getPos().getY());
        float var7 = (float) (box.minZ - mc.getEntityRenderDispatcher().camera.getPos().getZ());
        float var8 = (float) (box.maxX - mc.getEntityRenderDispatcher().camera.getPos().getX());
        float var9 = (float) (box.maxY - mc.getEntityRenderDispatcher().camera.getPos().getY());
        float var10 = (float) (box.maxZ - mc.getEntityRenderDispatcher().camera.getPos().getZ());
        if (!fix) {
            var5 = (float) box.minX;
            var6 = (float) box.minY;
            var7 = (float) box.minZ;
            var8 = (float) box.maxX;
            var9 = (float) box.maxY;
            var10 = (float) box.maxZ;
        }

        bufferBuilder.vertex(m, var5, var6, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var6, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var6, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var6, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var6, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var9, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var9, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var6, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var6, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var9, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var9, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var6, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var6, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var6, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var9, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var9, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var6, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var6, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var9, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var9, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var9, var7).color(c.getRGB());
        bufferBuilder.vertex(m, var5, var9, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var9, var10).color(c.getRGB());
        bufferBuilder.vertex(m, var8, var9, var7).color(c.getRGB());
    }

    public static Framebuffer method1584(Framebuffer framebuffer, boolean depth) {
        if (method1563(framebuffer)) {
            if (framebuffer != null) {
                framebuffer.delete();
            }

            return new SimpleFramebuffer(mc.getWindow().getFramebufferWidth(), mc.getWindow().getFramebufferHeight(), depth);
        } else {
            return framebuffer;
        }
    }

    public static void method1585(DrawContext context, float x, float y, float width, float height, float radius, int color) {
        float var7 = Math.max(10.0F, radius);
        float var8 = (float) (color >> 24 & 0xFF) / 255.0F;
        float var9 = (float) (color >> 16 & 0xFF) / 255.0F;
        float var10 = (float) (color >> 8 & 0xFF) / 255.0F;
        float var11 = (float) (color & 0xFF) / 255.0F;
        MatrixStack var12 = context.getMatrices();
        var12.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        VertexConsumer var13 = context.vertexConsumers.getBuffer(RenderLayer.getDebugTriangleFan());

        for (int var18 = 0; (float) var18 <= var7 * 4.0F; var18++) {
            float var19 = (float) ((double) var18 * Math.PI * 2.0 / (double) (var7 * 4.0F));
            float var20 = MathHelper.sin(var19) * radius;
            float var21 = MathHelper.cos(var19) * radius;
            float var22;
            float var23;
            if ((float) var18 <= var7) {
                var22 = x + width - radius;
                var23 = y + radius;
            } else if ((float) var18 <= var7 * 2.0F) {
                var22 = x + width - radius;
                var23 = y + height - radius;
            } else if ((float) var18 <= var7 * 3.0F) {
                var22 = x + radius;
                var23 = y + height - radius;
            } else {
                var22 = x + radius;
                var23 = y + radius;
            }

            var13.vertex(var12.peek().getPositionMatrix(), var22 + var20, var23 - var21, 0.0F).color(var9, var10, var11, var8);
        }

        RenderSystem.disableBlend();
        var12.pop();
    }

    public static void method1586(
            DrawContext context, AbstractClientPlayerEntity player, float x, float y, float width, float height, float radius, int borderColor, float borderWidth
    ) {
        Identifier var9 = player.getSkinTextures().texture();
        method1573(context, var9, x, y, width, height, radius, 8.0F, 8.0F, 8.0F, 8.0F, 64.0F, 64.0F);
        if (borderColor != 0 && borderWidth > 0.0F) {
            method1581(context, x, y, width, height, radius, borderColor, borderWidth);
        }
    }

    public static void method1587(Matrix4f matrix4f, float x1, float y1, float x2, float y2, int rgb) {
        if (x1 < x2) {
            float var6 = x1;
            x1 = x2;
            x2 = var6;
        }

        if (y1 < y2) {
            float var13 = y1;
            y1 = y2;
            y2 = var13;
        }

        float var14 = (float) (rgb >> 24 & 0xFF) / 255.0F;
        float var7 = (float) (rgb >> 16 & 0xFF) / 255.0F;
        float var8 = (float) (rgb >> 8 & 0xFF) / 255.0F;
        float var9 = (float) (rgb & 0xFF) / 255.0F;
        BufferBuilder var11 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        var11.vertex(matrix4f, x1, y2, 0.0F).color(var7, var8, var9, var14);
        var11.vertex(matrix4f, x2, y2, 0.0F).color(var7, var8, var9, var14);
        var11.vertex(matrix4f, x2, y1, 0.0F).color(var7, var8, var9, var14);
        var11.vertex(matrix4f, x1, y1, 0.0F).color(var7, var8, var9, var14);
        BuiltBuffer var12 = var11.endNullable();
        if (var12 != null) {
            BufferRenderer.drawWithGlobalProgram(var12);
        }
    }
}
