package nikoisntcat.client.screens;

import java.awt.Color;
import net.minecraft.client.gui.DrawContext;
import nikoisntcat.client.settings.Setting;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.utils.math.Class250;
import nikoisntcat.client.utils.math.Class288;

public class Class248
extends Class245 {
    private boolean field2107 = false;
    private int field2108;
    private int field2109;
    public BooleanSetting field2110;
    private int field2111;
    private Class288 field2112 = new Class288(2.0f, 0.0f);

    @Override
    public void init() {
        super.init();
        this.field2112.method1844(this.field2110.method1703() ? 13.0f : 0.0f);
    }

    @Override
    public int method1673() {
        return this.fr.getWidth(this.field2110.getName()) + 30;
    }

    public Class248(Setting value) {
        super(value);
        this.field2110 = (BooleanSetting)value;
    }

    @Override
    public int method1671(DrawContext context, int x, int y, int width, int mouseX, int mouseY) {
        this.field2108 = x;
        this.field2109 = y;
        this.field2111 = width;
        context.drawText(this.fr, this.field2110.getName(), x + 5, y + 5, -1, true);
        this.field2112.method1847(this.field2110.method1703() ? 13.0f : 0.0f);
        context.fill(x + width - 24, y + 4, x + width - 4, y + 11, -1);
        context.fill((int)((float)(x + width - 23) + this.field2112.method1845()), y + 5, (int)((float)(x + width - 18) + this.field2112.method1845()), y + 10, this.field2110.method1703() ? new Color(20, 255, 20).getRGB() : new Color(255, 20, 20).getRGB());
        return 15;
    }

    @Override
    public void method1668(double mouseX, double mouseY, int button) {
        if (Class250.method1684(mouseX, mouseY, this.field2108 + this.field2111 - 24, this.field2109 + 4, this.field2108 + this.field2111 - 4, this.field2109 + 11)) {
            this.field2107 = true;
        }
    }

    @Override
    public void method1672(double mouseX, double mouseY, int button) {
        if (this.field2107 && Class250.method1684(mouseX, mouseY, this.field2108 + this.field2111 - 24, this.field2109 + 4, this.field2108 + this.field2111 - 4, this.field2109 + 11)) {
            this.field2110.method1704(!this.field2110.method1703());
        }
    }
}
