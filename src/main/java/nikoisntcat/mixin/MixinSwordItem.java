package nikoisntcat.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.consume.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import nikoisntcat.client.utils.interfaces.ISwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={SwordItem.class})
public class MixinSwordItem
extends Item
implements ISwordItem {
    @Unique
    private double attackDamage;
    @Unique
    private ToolMaterial toolMaterial;

    public MixinSwordItem(Item.Settings settings) {
        super(settings);
    }

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    public void SwordItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings, CallbackInfo ci) {
        this.attackDamage = attackDamage;
        this.toolMaterial = material;
    }

    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
       // if (Class178.instance.isEnabled()) {
       //     return Integer.MAX_VALUE;
       // }
        return super.getMaxUseTime(stack, user);
    }

    public UseAction getUseAction(ItemStack stack) {
       // if (Class178.instance.isEnabled()) {
       //     return UseAction.NONE;
        //}
        return super.getUseAction(stack);
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        //if (Class178.instance.isEnabled()) {
      //      user.setCurrentHand(hand);
        //    return ActionResult.CONSUME;
        //}
        return super.use(world, user, hand);
    }

    @Override
    public double getAttackDamage() {
        return this.attackDamage + (double)this.toolMaterial.attackDamageBonus() + 1.0;
    }

    @Override
    public ToolMaterial getToolMaterial() {
        return this.toolMaterial;
    }
}
