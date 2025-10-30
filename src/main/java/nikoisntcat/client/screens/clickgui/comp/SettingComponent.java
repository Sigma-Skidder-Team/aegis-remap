package nikoisntcat.client.screens.clickgui.comp;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import nikoisntcat.client.settings.Setting;

public class SettingComponent {
    public TextRenderer fr;
    public Setting setting;

    public void init() {
        this.fr = MinecraftClient.getInstance().textRenderer;
    }

    public void method1668(double mouseX, double mouseY, int button) {
    }

    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
    }

    public boolean method1670() {
        return this.setting.shouldShow();
    }

    public int method1671(DrawContext context, int x, int y, int width, int mouseX, int mouseY) {
        return this.setting.getShow() != null && !this.setting.getShow().test(this.setting) ? 0 : 10;
    }

    public void method1672(double mouseX, double mouseY, int button) {
    }

    public int method1673() {
        return 0;
    }

    public SettingComponent(Setting value) {
        this.setting = value;
    }

    public void method1674() {
    }

    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
    }
}
