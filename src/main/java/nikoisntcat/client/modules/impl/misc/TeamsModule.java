package nikoisntcat.client.modules.impl.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.AbstractTeam;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;

public class TeamsModule extends Module {

    // Settings
    public BooleanSetting scoreboardCheck;
    public BooleanSetting armorCheck = new BooleanSetting("Armor", false);
    public BooleanSetting colorCheck = new BooleanSetting("Color", false);

    public TeamsModule() {
        super("Teams", "Skip your teammates", 0, Category.MISC);
        this.scoreboardCheck = new BooleanSetting("ScoareBoard", true);
    }

    private static boolean isSameNameColor(PlayerEntity other) {
        if (other.getDisplayName() == null
                || other.getDisplayName().getStyle().getColor() == null
                || mc.player == null
                || mc.player.getDisplayName() == null
                || mc.player.getDisplayName().getStyle().getColor() == null) {
            return false;
        }
        return other.getDisplayName().getStyle().getColor().getRgb() ==
                mc.player.getDisplayName().getStyle().getColor().getRgb();
    }

    private static boolean isSameArmorColor(PlayerEntity other) {
        if (mc.player != null
                && !mc.player.getInventory().armor.get(3).isEmpty()
                && !other.getInventory().armor.get(3).isEmpty()) {

            ItemStack selfHelmet = mc.player.getInventory().armor.get(3);
            ItemStack otherHelmet = other.getInventory().armor.get(3);

            ArmorItem selfArmor = (ArmorItem) selfHelmet.getItem();
            ArmorItem otherArmor = (ArmorItem) otherHelmet.getItem();

            // Special check for default team-colored leather armor (string "10511680" = greenish)
            if (String.valueOf(otherArmor.getItemBarColor(otherHelmet)).equals("10511680")) {
                return true;
            }

            return selfArmor.getItemBarColor(selfHelmet) == otherArmor.getItemBarColor(otherHelmet);
        }
        return false;
    }
    private static boolean isSameScoreboardTeam(PlayerEntity other) {
        if (mc.player != null && other.getScoreboardTeam() != null && mc.player.getScoreboardTeam() != null) {
            return other.isTeamPlayer((AbstractTeam) mc.player.getScoreboardTeam())
                    || other.getScoreboardTeam().getColor().getColorIndex() ==
                    mc.player.getScoreboardTeam().getColor().getColorIndex();
        }
        return false;
    }
    
    public static boolean isTeammate(Entity entity) {
        TeamsModule teamsModule = (TeamsModule) AegisClient.moduleManager.moduleMap.get(TeamsModule.class);

        if (!teamsModule.isEnabled()) {
            return false;
        }

        if (entity instanceof PlayerEntity player) {
            return (teamsModule.armorCheck.getValue() && isSameArmorColor(player))
                    || (teamsModule.colorCheck.getValue() && isSameNameColor(player))
                    || (teamsModule.scoreboardCheck.getValue() && isSameScoreboardTeam(player));
        }
        return false;
    }
}
