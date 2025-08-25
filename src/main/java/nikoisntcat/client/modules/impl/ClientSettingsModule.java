package nikoisntcat.client.modules.impl;

import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;

import java.util.List;

public class ClientSettingsModule
        extends Module {
    public static BooleanSetting noShader;
    public static ModeSetting strafeFixMode;
    public static ClientSettingsModule instance;
    public static BooleanSetting shieldSounds;

    static {
        instance = new ClientSettingsModule();
        shieldSounds = new BooleanSetting("ShieldSounds", false);
        strafeFixMode = new ModeSetting("StrafeFixMode", "None", List.of("None", "Normal"));
        noShader = new BooleanSetting("NoShader", false);
    }

    protected ClientSettingsModule() {
        super("ClientSettings", 0, false, Category.COMBAT);
    }
}
