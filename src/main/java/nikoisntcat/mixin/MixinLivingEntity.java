package nikoisntcat.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.JumpEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={LivingEntity.class})
public class MixinLivingEntity {
    @Unique
    private float preYaw = 0.0f;

    @Unique
    private JumpEvent jumpEvent;

    @Inject(method={"jump"}, at={@At(value="HEAD")}, cancellable=true)
    public void jumpPre(CallbackInfo callbackInfo) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof ClientPlayerEntity playerEntity) {
            this.jumpEvent = new JumpEvent(playerEntity.getYaw());
            AegisClient.eventManager.onJump(this.jumpEvent);
            if (this.jumpEvent.isCancelled()) {
                callbackInfo.cancel();
                return;
            }
            this.preYaw = playerEntity.getYaw();
            playerEntity.setYaw(this.jumpEvent.field1978);
        }
    }

    @Inject(method={"jump"}, at={@At(value="TAIL")})
    public void jumpPost(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity instanceof ClientPlayerEntity playerEntity) {
            if (this.jumpEvent != null) {
                this.jumpEvent = null;
                playerEntity.setYaw(this.preYaw);
            }
        }
    }
}
