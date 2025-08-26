package nikoisntcat.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import nikoisntcat.client.modules.impl.misc.DisablerModule;
import nikoisntcat.client.utils.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={PlayerEntity.class})
public class MixinPlayerEntity {
    @Inject(method={"equipStack"}, at={@At(value="HEAD")}, cancellable=true)
    public void equipStack(EquipmentSlot slot, ItemStack stack, CallbackInfo callbackInfo) {
        if (DisablerModule.cancelServerShield.getValue() && ((PlayerEntity) (Object) this).getId() == MinecraftUtil.mc.player.getId() && slot == EquipmentSlot.OFFHAND && !stack.isEmpty() && stack.getItem() instanceof ShieldItem) {
            callbackInfo.cancel();
        }
    }
}
