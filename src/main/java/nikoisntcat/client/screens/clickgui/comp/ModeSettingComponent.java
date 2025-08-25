package nikoisntcat.client.screens.clickgui.comp;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import nikoisntcat.client.screens.Class313;
import nikoisntcat.client.settings.Setting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.utils.math.Class250;
import nikoisntcat.client.utils.math.Class288;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModeSettingComponent
extends SettingComponent {
    public List<Class313> field2098 = new ArrayList<>();
    private int field2099;
    private int field2100;
    private int field2101 = 0;
    private boolean field2102 = false;
    public ModeSetting field2103;
    public Class288 field2104 = new Class288(2.0f, 0.0f);
    private int field2105;
    static Object field2106;

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Override
    public void method1672(double mouseX, double mouseY, int button) {
        if (this.field2102) {
            for (Class313 class313 : this.field2098) {
                if (!class313.method1972(mouseX, mouseY, button)) continue;
                this.field2103.method1696(class313.field2396);
                this.field2102 = false;
            }
        }
        super.method1672(mouseX, mouseY, button);
    }

    @Override
    public int method1671(DrawContext context, int x, int y, int width, int mouseX, int mouseY) {
        this.field2099 = x;
        this.field2105 = y;
        this.field2101 = width;
        context.drawText(MinecraftClient.getInstance().textRenderer, this.field2103.getName(), x + 5, y + 5, -1, true);
        context.fill(x + width - this.field2100 - 12, y + 1, x + width - 1, (int)((float)y + this.field2104.method1845() + 1.0f), new Color(56, 174, 235).getRGB());
        context.fill(x + width - this.field2100 - 11, y + 2, x + width - 2, (int)((float)y + this.field2104.method1845()), new Color(20, 20, 20).getRGB());
        int n = 12;
        context.drawText(MinecraftClient.getInstance().textRenderer, this.field2103.getValue(), x + width - this.field2100 - 6, y + 5, -1, true);
        context.drawText(MinecraftClient.getInstance().textRenderer, this.field2102 ? "-" : "+", x + width - 10, y + 5, -1, false);
        if (this.field2102) {
            Iterator<Class313> iterator = this.field2098.iterator();
            while (iterator.hasNext()) {
                iterator.next().method1971(context, x + width - this.field2100 - 11, y + 2 + n, this.field2100, mouseX, mouseY);
                n += 10;
            }
            this.field2104.method1847(n + 2);
        } else {
            this.field2104.method1847(16.0f);
        }
        return (int)this.field2104.method1845() + 1;
    }

    @Override
    public void init() {
        for (String string : this.field2103.method1698()) {
            this.field2100 = Math.max(this.field2100, MinecraftClient.getInstance().textRenderer.getWidth(string) + 10);
        }
    }
    @Override
    public void method1668(double mouseX, double mouseY, int button) {
        if (Class250.method1684(mouseX, mouseY, this.field2099 + this.field2101 - this.field2100 - 11, this.field2105 + 2, this.field2099 + this.field2101 - 2, this.field2105 + 16) && button == 1) {
            boolean bl = this.field2102 = !this.field2102;
        }
        if (this.field2102) {
            this.field2098.forEach(listElement -> listElement.method1973(mouseX, mouseY, button));
        }
        super.method1668(mouseX, mouseY, button);
    }

    @Override
    public void method1674() {
        for (String string : this.field2103.method1698()) {
            this.field2098.add(new Class313(string));
        }
    }

    public ModeSettingComponent(Setting value) {
        super(value);
        this.field2103 = (ModeSetting)value;
    }

    @Override
    public int method1673() {
        return MinecraftClient.getInstance().textRenderer.getWidth(this.field2103.getName()) + this.field2100 + 30;
    }
}
