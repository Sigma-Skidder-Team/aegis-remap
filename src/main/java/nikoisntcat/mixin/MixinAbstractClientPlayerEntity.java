package nikoisntcat.mixin;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.impl.NoFovModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={AbstractClientPlayerEntity.class})
public class MixinAbstractClientPlayerEntity {
    @Inject(method={"getFovMultiplier"}, at={@At(value="HEAD")}, cancellable=true)
    public void getFovMultiplier(boolean firstPerson, float fovEffectScale, CallbackInfoReturnable<Float> cir) {
        NoFovModule noFov = (NoFovModule) AegisClient.moduleManager.field2010.get(NoFovModule.class);
        if (noFov != null && noFov.isEnabled()) {
            cir.setReturnValue((float) NoFovModule.fovMultipluer.getValue());
        }
    }
}