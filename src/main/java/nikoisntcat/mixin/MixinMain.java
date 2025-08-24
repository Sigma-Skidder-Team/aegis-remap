package nikoisntcat.mixin;

import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {Main.class})
public class MixinMain {
    @Inject(method = {"main"}, at = {@At(value = "HEAD")})
    private static void onMain(String[] args, CallbackInfo ci) {
        System.setProperty("java.awt.headless", "false");
    }
}