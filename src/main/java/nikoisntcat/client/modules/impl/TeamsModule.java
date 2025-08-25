package nikoisntcat.client.modules.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.AbstractTeam;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;

import java.util.Objects;

public class TeamsModule extends Module {

    public BooleanSetting field1900;
    public BooleanSetting field1901 = new BooleanSetting("Armor", false);
    public BooleanSetting field1902 = new BooleanSetting("Color", false);

    public TeamsModule() {
        super("Teams", "Skip your teammates", 0, Category.COMBAT);
        this.field1900 = new BooleanSetting("ScoareBoard", true);
    }

    private static boolean method1372(PlayerEntity class_16572) {
        if (class_16572.getDisplayName() == null || class_16572.getDisplayName().getStyle().getColor() == null || mc.player == null || mc.player.getDisplayName() == null || mc.player.getDisplayName().getStyle().getColor() == null) {
            return false;
        }
        return class_16572.getDisplayName().getStyle().getColor().getRgb() == mc.player.getDisplayName().getStyle().getColor().getRgb();
    }

    private static boolean method1373(PlayerEntity entity) {
        if (mc.player != null && !((ItemStack) mc.player.getInventory().armor.get(3)).isEmpty() && !((ItemStack) entity.getInventory().armor.get(3)).isEmpty()) {
            ItemStack class_17992 = (ItemStack) mc.player.getInventory().armor.get(3);
            ArmorItem class_17382 = (ArmorItem) class_17992.getItem();
            ItemStack class_17993 = (ItemStack) entity.getInventory().armor.get(3);
            ArmorItem class_17383 = (ArmorItem) class_17993.getItem();
            if (String.valueOf(class_17383.getItemBarColor(class_17993)).equals("10511680")) {
                return true;
            }
            return class_17382.getItemBarColor(class_17992) == class_17383.getItemBarColor(class_17993);
        }
        return false;
    }

    private static boolean method1374(PlayerEntity coy) {
        if (mc.player != null && coy.getScoreboardTeam() != null && mc.player.getScoreboardTeam() != null) {
            return coy.isTeamPlayer((AbstractTeam)mc.player.getScoreboardTeam()) || coy.getScoreboardTeam().getColor().getColorIndex() == mc.player.getScoreboardTeam().getColor().getColorIndex();
        }
        return false;
    }

    public static boolean method1375(Entity bum) {
        TeamsModule class199 = (TeamsModule) AegisClient.moduleManager.field2010.get(TeamsModule.class);
        if (!class199.isEnabled()) {
            return false;
        }
        if (bum instanceof PlayerEntity) {
            PlayerEntity class_16572 = (PlayerEntity) bum;
            return Objects.requireNonNull(class199).isEnabled() && (class199.field1901.getValue() && TeamsModule.method1373(class_16572) || class199.field1902.getValue() && TeamsModule.method1372(class_16572) || class199.field1900.getValue() && TeamsModule.method1374(class_16572));
        }
        return false;
    }

}
