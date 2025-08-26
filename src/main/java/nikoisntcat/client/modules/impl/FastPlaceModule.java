package nikoisntcat.client.modules.impl;

import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.NumberSetting;

public class FastPlaceModule extends Module {
    public static NumberSetting delay = new NumberSetting("Delay", 0.0, 3.0, 0.0, 1.0);
    public static FastPlaceModule instance = new FastPlaceModule();

    public FastPlaceModule() {
        super("FastPlace", 0, Category.WORLD);
    }
}