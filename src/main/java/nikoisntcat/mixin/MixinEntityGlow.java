package nikoisntcat.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.impl.render.ESPModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {Entity.class})
public abstract class MixinEntityGlow {
    @Inject(method = {"isGlowing"}, at = {@At(value = "HEAD")}, cancellable = true)
    private void aegis$forceGlow(CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity) (Object) this;
        if (!(self instanceof LivingEntity) || AegisClient.moduleManager == null) {
            return;
        }
        ESPModule esp = (ESPModule) AegisClient.moduleManager.moduleMap.get(ESPModule.class);
        if (ESPModule.shouldHighlight(esp, (LivingEntity) self)) {
            cir.setReturnValue(true);
        }
    }
}
