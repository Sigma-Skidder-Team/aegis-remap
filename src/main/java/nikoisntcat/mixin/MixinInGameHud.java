package nikoisntcat.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.Render2DEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={InGameHud.class})
public class MixinInGameHud {
    @Inject(method={"renderHeldItemTooltip"}, at={@At(value="HEAD")})
    private void renderMainHud(DrawContext context, CallbackInfo ci) {
        Render2DEvent render2DEvent = new Render2DEvent(1.0f, context);
        render2DEvent.getDrawContext().getMatrices().push();
        AegisClient.eventManager.onRender2D(render2DEvent);
        render2DEvent.getDrawContext().getMatrices().pop();
    }
}