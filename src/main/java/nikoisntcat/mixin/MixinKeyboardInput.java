package nikoisntcat.mixin;

import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.PlayerInput;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.MoveInputEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={KeyboardInput.class})
public class MixinKeyboardInput
extends Input {

    @Inject(method={"tick"}, at={@At(value="TAIL")})
    public void tick(CallbackInfo ci) {
        MoveInputEvent moveInputEvent = new MoveInputEvent(this.movementForward, this.movementSideways, this.playerInput.jump(), this.playerInput.sneak());
        AegisClient.eventManager.onMoveInput(moveInputEvent);
        this.movementForward = moveInputEvent.field1979;
        this.movementSideways = moveInputEvent.field1983;
        this.playerInput = new PlayerInput(MixinKeyboardInput.getKeyStateFromValue(this.movementForward), MixinKeyboardInput.getKeyStateFromValue(-this.movementForward), MixinKeyboardInput.getKeyStateFromValue(this.movementSideways), MixinKeyboardInput.getKeyStateFromValue(-this.movementSideways), moveInputEvent.field1981, moveInputEvent.field1980, MixinKeyboardInput.getSprint(this.playerInput.sprint(), this.movementForward));
    }

    @Unique
    private static boolean getKeyStateFromValue(float value) {
        if (value == 0.0f) {
            return false;
        }
        return value > 0.0f;
    }

    @Unique
    private static boolean getSprint(boolean sprint, float forward) {
        if (forward <= 0.0f) {
            return false;
        }
        return sprint;
    }
}
