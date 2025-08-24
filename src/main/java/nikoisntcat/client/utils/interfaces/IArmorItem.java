package nikoisntcat.client.utils.interfaces;

import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentType;

public interface IArmorItem {
    public EquipmentType getEquipmentType();

    public ArmorMaterial getArmorMaterial();
}
