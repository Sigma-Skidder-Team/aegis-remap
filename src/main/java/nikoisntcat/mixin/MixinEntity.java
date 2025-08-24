package nikoisntcat.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.StrafeEvent;
import nikoisntcat.client.utils.interfaces.IEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Entity.class})
public abstract class MixinEntity
implements IEntity {
    @Unique
    private StrafeEvent strafeEvent;

    @Shadow
    public abstract int getId();

    @Shadow
    public abstract boolean saveNbt(NbtCompound var1);

    @Shadow
    public abstract float getYaw();

    @Shadow
    public abstract Vec3d getVelocity();

    @Override
    public Vec3d getAllowedMovement(Vec3d movement) {
        return this.adjustMovementForCollisions(movement);
    }

    @Shadow
    private Vec3d adjustMovementForCollisions(Vec3d movement) {
        return null;
    }

    @Shadow
    protected abstract void fall(double var1, boolean var3, BlockState var4, BlockPos var5);

    @Inject(method={"updateVelocity"}, at={@At(value="HEAD")})
    public void updateVelocity(float speed, Vec3d movementInput, CallbackInfo ci) {
        if (!MinecraftClient.getInstance().isInSingleplayer() && MinecraftClient.getInstance().player != null && this.getId() == MinecraftClient.getInstance().player.getId()) {
            this.strafeEvent = new StrafeEvent(movementInput, speed, this.getYaw(), this.getVelocity());
            AegisClient.eventManager.onStrafe(this.strafeEvent);
            this.strafeEvent.method1419();
        }
    }

    @Inject(method={"updateVelocity"}, at={@At(value="TAIL")})
    public void updateVelocityTail(float speed, Vec3d movementInput, CallbackInfo ci) {
        if (!MinecraftClient.getInstance().isInSingleplayer() && MinecraftClient.getInstance().player != null && this.getId() == MinecraftClient.getInstance().player.getId()) {
            this.strafeEvent.method1277();
        }
    }

    @Redirect(method={"move"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;isControlledByPlayer()Z"))
    public boolean fall(Entity instance) {
        if (instance instanceof ClientPlayerEntity) {
            return false;
        }
        return instance.isControlledByPlayer();
    }
}
