package nikoisntcat.client.modules.impl.world;

import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;

public class BlinkModule extends Module {
    @Override
    public void onDisable() {
        if (AegisClient.field2315.field2467 == this) {
            AegisClient.field2315.method2047();
        }
    }

    public BlinkModule() {
        super("Blink", 0, Category.WORLD);
    }

    @Override
    public void onEnable() {
        AegisClient.field2315.method2051(this);
        AegisClient.field2315.field2467 = this;
    }
}
