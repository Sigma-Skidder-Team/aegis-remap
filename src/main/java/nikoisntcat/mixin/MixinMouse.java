package nikoisntcat.mixin;

import net.minecraft.client.Mouse;
import nikoisntcat.AegisClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={Mouse.class})
public class MixinMouse {
    @Inject(method={"onMouseScroll"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerInventory;setSelectedSlot(I)V")})
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        AegisClient.field2310.method1436();
    }

    @Inject(method={"onMouseScroll"}, at={@At(value="INVOKE", target="Lnet/minecraft/entity/player/PlayerInventory;setSelectedSlot(I)V", shift=At.Shift.AFTER)})
    private void onMouseScrollAfter(long window, double horizontal, double vertical, CallbackInfo ci) {
        AegisClient.field2310.method1444();
    }
}