package nikoisntcat.mixin;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;
import nikoisntcat.client.utils.interfaces.IArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={ArmorItem.class})
public class MixinArmorItem
implements IArmorItem {
    @Unique
    private ArmorMaterial material;
    @Unique
    private EquipmentType equipmentType;

    @Inject(method={"<init>"}, at={@At(value="TAIL")})
    public void ArmorItem(ArmorMaterial material, EquipmentType type, Item.Settings settings, CallbackInfo ci) {
        this.material = material;
        this.equipmentType = type;
    }

    @Override
    public EquipmentType getEquipmentType() {
        return this.equipmentType;
    }

    @Override
    public ArmorMaterial getArmorMaterial() {
        return this.material;
    }
}