package nikoisntcat.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.impl.render.RotationsModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {LivingEntityRenderer.class})
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> implements FeatureRendererContext<S, M> {
    @Unique
    private float prePitch = 0.0f;

    public MixinLivingEntityRenderer(EntityRendererFactory.Context ctx, M model, float shadowRadius) {
        super(ctx);
    }

    @Inject(method = {"render"}, at = {@At(value = "HEAD")})
    public void renderHead(S livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo callbackInfo) {
        if (livingEntityRenderState instanceof PlayerEntityRenderState livingEntity) {
            if (livingEntity.id == MinecraftClient.getInstance().player.getId()) {
                this.prePitch = MinecraftClient.getInstance().player.getPitch();
                RotationsModule rotations = (RotationsModule) AegisClient.moduleManager.moduleMap.get(RotationsModule.class);
                if (rotations.isEnabled()) {
                    rotations.method1225(livingEntity);
                }
            }
        }
    }

    @Inject(method = {"render"}, at = {@At(value = "TAIL")})
    public void renderTail(S livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo callbackInfo) {
        if (livingEntityRenderState instanceof PlayerEntityRenderState) {
        }
    }
}
