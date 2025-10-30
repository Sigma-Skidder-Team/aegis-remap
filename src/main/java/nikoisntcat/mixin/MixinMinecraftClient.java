package nikoisntcat.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.HitResult;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.impl.world.FastPlaceModule;
import nikoisntcat.client.utils.PlayerUtil;
import nikoisntcat.client.utils.font.FontManager;
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

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    void initHook(RunArgs args, CallbackInfo ci) {
        try {
            FontManager.icon48 = FontManager.createFont(48.0f, "icon");
            FontManager.thin18 = FontManager.createFont(18.0f, "thin");
            FontManager.thin20 = FontManager.createFont(20.0f, "thin");
            FontManager.thin30 = FontManager.createFont(30.0f, "thin");
            FontManager.thin22 = FontManager.createFont(22.0f, "thin");
            FontManager.thin24 = FontManager.createFont(24.0f, "thin");
            FontManager.thin26 = FontManager.createFont(26.0f, "thin");
            FontManager.thin28 = FontManager.createFont(28.0f, "thin");
            FontManager.light18 = FontManager.createFont(18.0f, "light");
            FontManager.light20 = FontManager.createFont(20.0f, "light");
            FontManager.light22 = FontManager.createFont(22.0f, "light");
            FontManager.light24 = FontManager.createFont(24.0f, "light");
            FontManager.light26 = FontManager.createFont(26.0f, "light");
            FontManager.light28 = FontManager.createFont(28.0f, "light");
            FontManager.light30 = FontManager.createFont(30.0f, "light");
            FontManager.jelloLight14 = FontManager.createFont(14.0f, "jelloLight");
            FontManager.jelloLight25 = FontManager.createFont(25.0f, "jelloLight");
            FontManager.medium16 = FontManager.createFont(16.0f, "medium");
            FontManager.medium20 = FontManager.createFont(20.0f, "medium");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method={"doItemUse"}, at={@At(value="FIELD", target="Lnet/minecraft/client/MinecraftClient;itemUseCooldown:I", shift=At.Shift.AFTER)})
    private void hookItemUseCooldown(CallbackInfo callbackInfo) {
        if (FastPlaceModule.instance.isEnabled() && this.crosshairTarget.getType() == HitResult.Type.BLOCK) {
            this.itemUseCooldown = (int)FastPlaceModule.delay.getValue();
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
        if (PlayerUtil.getStateOrCounter() > 0) {
            PlayerUtil.updatePlayerRenderState();
            info.cancel();
            return;
        }

        if (PlayerUtil.playerStateSaved) {
            PlayerUtil.restorePlayerPosition();
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