package nikoisntcat.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.ElytraSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.util.Hand;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.SlowdownEvent;
import nikoisntcat.client.modules.impl.DisablerModule;
import nikoisntcat.client.utils.Class224;
import nikoisntcat.client.utils.Class226;
import nikoisntcat.client.utils.interfaces.IClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {ClientPlayerEntity.class})
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity implements IClientPlayerEntity {
    @Unique
    private MotionEvent motionEvent;
    @Unique
    private SlowdownEvent slowDownEvent;

    @Shadow
    public abstract boolean isUsingItem();

    @Shadow
    public abstract boolean shouldFilterText();

    @Shadow
    protected abstract void sendMovementPackets();

    @Shadow
    public boolean usingItem;

    @Shadow
    private boolean falling;

    @Shadow
    @Final
    protected MinecraftClient client;

    @Override
    @Unique
    public float getJumpMovementFactor() {
        return super.getOffGroundSpeed();
    }

    protected float getOffGroundSpeed() {
        return super.getOffGroundSpeed();
    }

    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = {"sendMovementPackets"}, at = {@At(value = "HEAD")})
    private void sendMovementPacketsPre(CallbackInfo callbackInfo) {
        if (AegisClient.motionManager.field2020) {
            return;
        }
        ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) (Object) this;
        this.motionEvent = new MotionEvent(clientPlayerEntity.getX(), clientPlayerEntity.getY(), clientPlayerEntity.getZ(), clientPlayerEntity.isOnGround(), clientPlayerEntity.getYaw(), clientPlayerEntity.getPitch(), MotionEvent.Class123.PRE);
        AegisClient.field2316.method1519(this.motionEvent);
        AegisClient.eventManager.onMotion(this.motionEvent);
        this.motionEvent.method1405(clientPlayerEntity);
    }

    @Redirect(method = {"tickMovement"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean slow(ClientPlayerEntity instance) {
        this.slowDownEvent = new SlowdownEvent(this.isUsingItem());
        AegisClient.eventManager.onSlow(this.slowDownEvent);
        return this.slowDownEvent.method1432();
    }

    @Redirect(method = {"canStartSprinting"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean sprint(ClientPlayerEntity instance) {
        if (this.slowDownEvent != null && !this.slowDownEvent.method1432()) {
            this.slowDownEvent = null;
            return false;
        }
        return instance.isUsingItem();
    }

    @Inject(method = {"sendMovementPackets"}, at = {@At(value = "TAIL")})
    private void sendMovementPacketsPost(CallbackInfo callbackInf) {
        if (AegisClient.motionManager.field2020) {
            return;
        }
        ClientPlayerEntity clientPlayerEntity = (ClientPlayerEntity) (Object) this;
        this.motionEvent.setState(MotionEvent.Class123.PRE);
        AegisClient.eventManager.onMotion(this.motionEvent);
        this.motionEvent.method1402(clientPlayerEntity);
        AegisClient.field2316.method1519(this.motionEvent);
    }

    @Inject(method = {"tick"}, at = {@At(value = "HEAD")}, cancellable = true)
    public void tick(CallbackInfo callbackInfo) {
        AegisClient.eventManager.onPreTick();
        if (Class226.field2007) {
            if (Class226.field2008 > 18) {
                Class226.field2008 = 0;
                Class226.field2006 = false;
            } else {
                ++Class226.field2008;
                sendMovementPackets();
                callbackInfo.cancel();
            }
        }
    }

    @Overwrite
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (LIVING_FLAGS.equals(data) && !DisablerModule.field1644.method1703()) {
            boolean bl = ((Byte) this.dataTracker.get(LIVING_FLAGS) & 1) > 0;
            Hand hand = ((Byte) this.dataTracker.get(LIVING_FLAGS) & 2) > 0 ? Hand.OFF_HAND : Hand.MAIN_HAND;
            if (bl && !this.usingItem) {
                if (DisablerModule.field1639.method1703()) {
                    Class224.method1451("Using Item");
                }
                this.setCurrentHand(hand);
            } else if (!bl && this.usingItem) {
                if (DisablerModule.field1639.method1703()) {
                    Class224.method1451("Clear Item");
                }
                this.clearActiveItem();
            }
        }
        if (FLAGS.equals(data) && this.isGliding() && !this.falling) {
            this.client.getSoundManager().play((SoundInstance) new ElytraSoundInstance((ClientPlayerEntity) (Object) this));
        }
    }
}
