package nikoisntcat.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.Render3DEvent;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={GameRenderer.class})
public abstract class MixinGameRenderer {

    @Inject(method={"renderHand"}, at={@At(value="HEAD")})
    private void renderHand(Camera camera, float tickDelta, Matrix4f matrix4f, CallbackInfo callbackInfo) {
        MatrixStack matrixStack1 = new MatrixStack();
        RenderSystem.getModelViewStack().pushMatrix().mul(matrixStack1.peek().getPositionMatrix());
        matrixStack1.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrixStack1.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));
        Render3DEvent render3DEvent = new Render3DEvent(camera, matrixStack1, tickDelta);
        AegisClient.eventManager.onRender3D(render3DEvent);
        render3DEvent.method1422();
        RenderSystem.getModelViewStack().popMatrix();
    }
}
