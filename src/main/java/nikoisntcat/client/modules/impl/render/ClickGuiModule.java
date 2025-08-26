package nikoisntcat.client.modules.impl.render;

import net.minecraft.client.gui.screen.Screen;
import nikoisntcat.AegisClient;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.NumberSetting;

public class ClickGuiModule
extends Module {
    public NumberSetting field1829 = new NumberSetting("MaxModule", 1.0, 30.0, 1.0, 1.0);

    @Override
    public void onEnable() {
        mc.setScreenAndRender((Screen) AegisClient.clickGui);
    }

    public ClickGuiModule() {
        super("ClickGui", 344, false, Category.RENDER);
    }

    @Override
    public void onTick() {
        if (ClickGuiModule.mc.currentScreen == null) {
            this.setState(false);
        }
    }
}
