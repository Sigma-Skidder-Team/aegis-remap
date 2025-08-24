package nikoisntcat.client.modules.impl;

import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;

import java.util.List;

public class ClientSettingsModule
extends Module {
    public static BooleanSetting field1846;
    public static ModeSetting field1847;
    public static ClientSettingsModule instance;
    public static BooleanSetting field1849;

    static {
        instance = new ClientSettingsModule();
        field1849 = new BooleanSetting("ShieldSounds", false);
        field1847 = new ModeSetting("StrafeFixMode", "None", List.of("None", "Normal"));
        field1846 = new BooleanSetting("NoShader", false);
    }

    protected ClientSettingsModule() {
        super("ClientSettings", 0, false, Category.COMBAT);
    }
}
