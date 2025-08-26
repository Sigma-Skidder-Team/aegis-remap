package nikoisntcat.client.modules.impl.render;

import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.NumberSetting;

public class NoFovModule extends Module {
    public static NumberSetting fovMultipluer = new NumberSetting("FovMultiplier", 1.0, 2.0, 0.1, 0.1);

    public NoFovModule() {
        super("NoFov", 0, Category.RENDER);
    }
}
