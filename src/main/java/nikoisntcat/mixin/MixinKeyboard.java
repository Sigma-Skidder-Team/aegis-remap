package nikoisntcat.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Keyboard.class})
public class MixinKeyboard {
    @Inject(at={@At(value="HEAD")}, method={"onKey"})
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {
        if (AegisClient.eventManager != null && action == 1) {
            KeyEvent event = new KeyEvent(key, MinecraftClient.getInstance().currentScreen != null);
            AegisClient.eventManager.method2022(event);
        }
    }
}
