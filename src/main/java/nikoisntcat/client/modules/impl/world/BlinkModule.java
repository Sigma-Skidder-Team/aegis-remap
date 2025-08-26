package nikoisntcat.client.modules.impl.world;

import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;

public class BlinkModule extends Module {
    @Override
    public void onDisable() {
        if (AegisClient.blinkUtil.activeModule == this) {
            AegisClient.blinkUtil.stopBlinking();
        }
    }

    public BlinkModule() {
        super("Blink", 0, Category.WORLD);
    }

    @Override
    public void onEnable() {
        AegisClient.blinkUtil.startBlinking(this);
        AegisClient.blinkUtil.activeModule = this;
    }
}
