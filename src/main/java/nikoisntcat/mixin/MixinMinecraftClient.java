package nikoisntcat.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.HitResult;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.impl.FastPlaceModule;
import nikoisntcat.client.utils.Class224;
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
            FontManager.field2425 = FontManager.createFont(48.0f, "icon");
            FontManager.field2422 = FontManager.createFont(18.0f, "thin");
            FontManager.field2414 = FontManager.createFont(20.0f, "thin");
            FontManager.field2415 = FontManager.createFont(30.0f, "thin");
            FontManager.field2426 = FontManager.createFont(22.0f, "thin");
            FontManager.field2412 = FontManager.createFont(24.0f, "thin");
            FontManager.field2408 = FontManager.createFont(26.0f, "thin");
            FontManager.field2413 = FontManager.createFont(28.0f, "thin");
            FontManager.field2424 = FontManager.createFont(18.0f, "light");
            FontManager.field2409 = FontManager.createFont(20.0f, "light");
            FontManager.field2406 = FontManager.createFont(22.0f, "light");
            FontManager.field2420 = FontManager.createFont(24.0f, "light");
            FontManager.field2417 = FontManager.createFont(26.0f, "light");
            FontManager.field2407 = FontManager.createFont(28.0f, "light");
            FontManager.field2421 = FontManager.createFont(30.0f, "light");
            FontManager.field2410 = FontManager.createFont(14.0f, "jelloLight");
            FontManager.field2419 = FontManager.createFont(25.0f, "jelloLight");
            FontManager.field2423 = FontManager.createFont(16.0f, "medium");
            FontManager.field2411 = FontManager.createFont(20.0f, "medium");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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