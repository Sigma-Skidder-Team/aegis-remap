package nikoisntcat.client.modules.impl;

import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;

public class SprintModule
extends Module {
    public SprintModule() {
        super("Sprint", 0, true, Category.MOVE);
    }

    @Override
    public void onTick() {
        SprintModule.mc.options.sprintKey.setPressed(true);
    }
}
