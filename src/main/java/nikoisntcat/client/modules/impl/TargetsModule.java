package nikoisntcat.client.modules.impl;

import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;

public class TargetsModule extends Module {
    public static BooleanSetting field1772 = new BooleanSetting("Animal", true);
    public static BooleanSetting field1771 = new BooleanSetting("Villagers", false, setting -> field1772.getValue());
    public static BooleanSetting field1773 = new BooleanSetting("Player", true);
    public static BooleanSetting field1774 = new BooleanSetting("Mobs", false, setting -> field1772.getValue());

    public TargetsModule() {
        super("Targets", 0, false, Category.COMBAT);
    }
}