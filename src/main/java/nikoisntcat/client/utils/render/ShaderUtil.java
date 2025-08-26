package nikoisntcat.client.utils.render;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.impl.misc.ClientSettingsModule;
import nikoisntcat.client.utils.math.Shader;
import org.joml.Matrix4f;

public class ShaderUtil {
    private static final List<SimpleFramebuffer> field2276;
    private static final List<SimpleFramebuffer> field2277;
    private static SimpleFramebuffer fb;
    private static int field2280;
    private static int field2281;
    private static Framebuffer field2282;
    private static int field2285;

    static {
        field2277 = new ArrayList<>();
        field2276 = new ArrayList<>();
        field2285 = -1;
        field2281 = -1;
        field2280 = -1;
    }

    public static void method1869(MatrixStack matrices, Consumer<MatrixStack> renderAction, float intensity, float kawaseOffset) {
        ShaderUtil.method1873(1);
        ShaderUtil.method1870(matrices);
        renderAction.accept(matrices);
        ShaderUtil.method1874(matrices, intensity, 1, 0, kawaseOffset);
    }

    public static void method1870(MatrixStack matrices) {
        if (ClientSettingsModule.noShader.getValue()) {
            return;
        }
        if (!RenderSystem.isOnRenderThread()) {
            throw new IllegalStateException("BloomRenderer must be called on the render thread!");
        }
        matrices.push();
        fb.beginWrite(false);
        RenderSystem.clear(16640);
        RenderSystem.viewport(0, 0, ShaderUtil.fb.textureWidth, ShaderUtil.fb.textureHeight);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
    }

    private static void draw(Matrix4f matrix4f, boolean bind, float width, float height) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix4f, 0.0f, 0.0f, 0.0f).texture(0.0f, 1.0f);
        bufferBuilder.vertex(matrix4f, 0.0f, height, 0.0f).texture(0.0f, 0.0f);
        bufferBuilder.vertex(matrix4f, width, height, 0.0f).texture(1.0f, 0.0f);
        bufferBuilder.vertex(matrix4f, width, 0.0f, 0.0f).texture(1.0f, 1.0f);
        if (bind) {
            BufferRenderer.draw(Objects.requireNonNull(bufferBuilder.endNullable()));
        } else {
            BufferRenderer.drawWithGlobalProgram(Objects.requireNonNull(bufferBuilder.endNullable()));
        }
    }

    public static void blur(MatrixStack matrices, Consumer<MatrixStack> renderAction, float intensity, int bloomColor, float kawaseOffset) {
        ShaderUtil.method1873(1);
        ShaderUtil.method1870(matrices);
        renderAction.accept(matrices);
        ShaderUtil.method1874(matrices, intensity, 1, bloomColor, kawaseOffset);
    }

    private static void method1873(int iterations) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        int n = minecraftClient.getWindow().getFramebufferWidth();
        int n2 = minecraftClient.getWindow().getFramebufferHeight() + 1;
        if (n <= 0 || n2 <= 0) {
            throw new IllegalStateException("[BloomRenderer] Invalid framebuffer size: " + n + "x" + n2);
        }
        field2282 = minecraftClient.getFramebuffer();
        if (fb != null && (ShaderUtil.fb.textureWidth != n || ShaderUtil.fb.textureHeight != n2)) {
            fb.delete();
            fb = null;
        }
        if (fb == null) {
            fb = new SimpleFramebuffer(n, n2, true);
            if (ShaderUtil.fb.fbo == 0 || fb.getColorAttachment() == 0) {
                throw new IllegalStateException("SimpleFramebuffer creation failed! FBO: " + ShaderUtil.fb.fbo + " Tex: " + fb.getColorAttachment());
            }
        }
        if (field2285 != n || field2281 != n2 || field2280 != iterations) {
            Iterator<SimpleFramebuffer> iterator = field2277.iterator();
            while (iterator.hasNext()) {
                iterator.next().delete();
            }
            iterator = field2276.iterator();
            while (iterator.hasNext()) {
                iterator.next().delete();
            }
            field2277.clear();
            field2276.clear();
            field2277.add(fb);
            field2277.add(new SimpleFramebuffer(n, n2, true));
            field2276.add(new SimpleFramebuffer(n, n2, true));
            field2285 = n;
            field2281 = n2;
            field2280 = iterations;
        }
    }

    public static void method1874(MatrixStack matrices, float intensity, int iterations, int bloomColor, float kawaseOffset) {
        SimpleFramebuffer simpleFramebuffer;
        SimpleFramebuffer simpleFramebuffer2;
        fb.endWrite();
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        float f = (float)(bloomColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(bloomColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(bloomColor & 0xFF) / 255.0f;
        float f4 = (float)(bloomColor >> 24 & 0xFF) / 255.0f;
        Shader shader = AegisClient.shaders;
        for (int i = 1; i <= iterations; ++i) {
            simpleFramebuffer2 = field2277.get(i - 1);
            simpleFramebuffer = field2277.get(i);
            simpleFramebuffer.beginWrite(true);
            shader.set("blur_kawase_down");
            shader.setUniformsInts("TextureA", 0);
            shader.setUniformFloats("srcSize", simpleFramebuffer2.textureWidth, simpleFramebuffer2.textureHeight);
            shader.setUniformFloats("dstSize", simpleFramebuffer2.textureWidth, simpleFramebuffer2.textureHeight);
            shader.setUniformFloats("halfpixel", 1.0f / (float)simpleFramebuffer.textureWidth, 1.0f / (float)simpleFramebuffer.textureHeight);
            shader.setUniformFloats("offset", kawaseOffset);
            shader.method1801();
            shader.bind();
            RenderSystem.activeTexture(33984);
            RenderSystem.bindTexture(simpleFramebuffer2.getColorAttachment());
            ShaderUtil.draw(matrices.peek().getPositionMatrix(), true, (float)((double)simpleFramebuffer.textureWidth / minecraftClient.getWindow().getScaleFactor()), (float)((double)simpleFramebuffer.textureHeight / minecraftClient.getWindow().getScaleFactor()));
            shader.unbind();
            simpleFramebuffer.endWrite();
        }
        simpleFramebuffer2 = field2277.get(iterations);
        simpleFramebuffer = field2276.getFirst();
        simpleFramebuffer.beginWrite(true);
        shader.set("blur_kawase_up");
        shader.setUniformsInts("TextureA", 0);
        shader.setUniformFloats("srcSize", simpleFramebuffer2.textureWidth, simpleFramebuffer2.textureHeight);
        shader.setUniformFloats("dstSize", simpleFramebuffer2.textureWidth, simpleFramebuffer2.textureHeight);
        shader.setUniformFloats("halfpixel", 1.0f / (float)simpleFramebuffer.textureWidth, 1.0f / (float)simpleFramebuffer.textureHeight);
        shader.setUniformFloats("offset", kawaseOffset);
        shader.method1801();
        shader.bind();
        RenderSystem.activeTexture(33984);
        RenderSystem.bindTexture(simpleFramebuffer2.getColorAttachment());
        ShaderUtil.draw(matrices.peek().getPositionMatrix(), true, (float)((double)simpleFramebuffer.textureWidth / minecraftClient.getWindow().getScaleFactor()), (float)((double)simpleFramebuffer.textureHeight / minecraftClient.getWindow().getScaleFactor()));
        simpleFramebuffer.endWrite();
        field2282.beginWrite(true);
        shader.set("composite");
        shader.setUniformsInts("BaseTexture", 0);
        shader.setUniformsInts("BloomTexture", 1);
        shader.setUniformFloats("Intensity", intensity);
        shader.setUniformFloats("BloomColor", f, f2, f3, f4);
        if (bloomColor == 0) {
            shader.setUniformsInts("HasColor", 0);
        } else {
            shader.setUniformsInts("HasColor", 1);
        }
        shader.method1801();
        shader.bind();
        RenderSystem.activeTexture(33984);
        RenderSystem.bindTexture(field2282.getColorAttachment());
        RenderSystem.activeTexture(33985);
        RenderSystem.bindTexture(field2276.getFirst().getColorAttachment());
        ShaderUtil.draw(matrices.peek().getPositionMatrix(), true, minecraftClient.getWindow().getScaledWidth(), minecraftClient.getWindow().getScaledHeight());
        shader.unbind();
        RenderSystem.disableBlend();
        matrices.pop();
    }
}