package nikoisntcat.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.HitResult;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.impl.FastPlaceModule;
import nikoisntcat.client.utils.Class224;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

    @Shadow
    private int itemUseCooldown;
    @Shadow
    @Nullable
    public HitResult crosshairTarget;

    @Shadow
    @Nullable
    public ClientWorld world;

    @Inject(at={@At(value="HEAD")}, method={"setWorld"})
    private void setWorld(ClientWorld world, CallbackInfo ci) {
        AegisClient.eventManager.onSetWorld();
    }

    @Inject(method={"doItemUse"}, at={@At(value="FIELD", target="Lnet/minecraft/client/MinecraftClient;itemUseCooldown:I", shift=At.Shift.AFTER)})
    private void hookItemUseCooldown(CallbackInfo callbackInfo) {
        if (FastPlaceModule.instance.isEnabled() && this.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            this.itemUseCooldown = (int)FastPlaceModule.field1806.getValue();
        }
    }

    @Inject(at={@At(value="HEAD")}, method={"run"})
    private void run(CallbackInfo info) {
        try {
            AegisClient.initClient();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(at={@At(value="HEAD")}, method={"tick"}, cancellable=true)
    public void tick(CallbackInfo info) {
        if (Class224.method1452() > 0) {
            Class224.method1447();
            info.cancel();
            return;
        }

        if (Class224.field2001) {
            Class224.method1277();
        }

        if (AegisClient.eventManager != null && world != null) {
            AegisClient.eventManager.onTick(info);
        }
    }

    @Inject(at={@At(value="TAIL")}, method={"tick"})
    public void tickTail(CallbackInfo info) {
        AegisClient.field2310.method1438();
    }

}