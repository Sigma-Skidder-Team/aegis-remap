package nikoisntcat.client.modules.impl.misc;

import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;

public class TargetsModule extends Module {
    public static BooleanSetting targetAnimals = new BooleanSetting("Animals", true);
    public static BooleanSetting targetVillagers = new BooleanSetting("Villagers", false, setting -> targetAnimals.getValue());
    public static BooleanSetting targetPlayers = new BooleanSetting("Players", true);
    public static BooleanSetting targetMobs = new BooleanSetting("Mobs", false, setting -> targetAnimals.getValue());

    public TargetsModule() {
        super("Targets", 0, false, Category.MISC);
    }
}
