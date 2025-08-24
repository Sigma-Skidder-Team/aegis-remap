package nikoisntcat.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value={ClientPlayerInteractionManager.class})
public class MixinClientPlayerInteractionManager {
}
