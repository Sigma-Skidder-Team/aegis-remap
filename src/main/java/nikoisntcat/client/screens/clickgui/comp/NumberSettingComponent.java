package nikoisntcat.client.screens.clickgui.comp;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import net.minecraft.client.gui.DrawContext;
import nikoisntcat.client.settings.Setting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.math.Class250;
import nikoisntcat.client.utils.math.Class288;

public class NumberSettingComponent
extends SettingComponent {
    private int field2092;
    private int field2093;
    private Class288 field2094 = new Class288(4.0f, 0.0f);
    public NumberSetting field2095;
    private boolean field2096 = false;
    private int field2097;

    @Override
    public void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (Class250.method1684(mouseX, mouseY, this.field2097 + 5, this.field2092 + 13, this.field2097 + this.field2093 - 5, this.field2092 + 19)) {
            if (verticalAmount > 0.0) {
                this.field2095.setValue(this.field2095.getValue() + this.field2095.getMinPerc());
            } else {
                this.field2095.setValue(this.field2095.getValue() - this.field2095.getMinPerc());
            }
        }
    }

    public static double method1676(double num) {
        return new BigDecimal(num).setScale(6, RoundingMode.HALF_UP).doubleValue();
    }

    public NumberSettingComponent(Setting value) {
        super(value);
        this.field2095 = (NumberSetting)value;
    }

    @Override
    public void method1672(double mouseX, double mouseY, int button) {
        this.field2096 = false;
    }

    @Override
    public void method1668(double mouseX, double mouseY, int button) {
        if (Class250.method1684(mouseX, mouseY, this.field2097 + 5, this.field2092 + 13, this.field2097 + this.field2093 - 5, this.field2092 + 19)) {
            this.field2096 = true;
        }
    }

    @Override
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.field2096) {
            int n = (int)((this.field2095.getMax() - this.field2095.getMin()) / this.field2095.getMinPerc());
            double d = (mouseX - (double)this.field2097 - 5.0) / (double)(this.field2093 - 10);
            this.field2095.setValue(NumberSettingComponent.method1676(Math.max(this.field2095.getMin(), Math.min((double)((int)(d * (double)n)) * this.field2095.getMinPerc() + this.field2095.getMin(), this.field2095.getMax()))));
        }
    }

    @Override
    public int method1671(DrawContext context, int x, int y, int width, int mouseX, int mouseY) {
        this.field2097 = x;
        this.field2092 = y;
        this.field2093 = width;
        context.drawText(this.fr, this.field2095.getName(), x + 5, y + 5, -1, true);
        context.drawText(this.fr, String.valueOf(this.field2095.getValue()), x + width - this.fr.getWidth(String.valueOf(this.field2095.getValue())) - 5, y + 5, -1, true);
        int n = width - 10;
        int n2 = (int)((double)n * ((this.field2095.getValue() - this.field2095.getMin()) / (this.field2095.getMax() - this.field2095.getMin())));
        this.field2094.method1847(Math.max(Math.min(n2, n), 0));
        context.fill(x + 5, y + 15, x + 5 + n, y + 17, new Color(200, 200, 200, 30).getRGB());
        context.fill(x + 5, y + 15, (int)((float)(x + 5) + this.field2094.method1845()), y + 17, -1);
        context.fill((int)((float)(x + 5) + this.field2094.method1845() - 3.0f), y + 13, (int)((float)(x + 5) + this.field2094.method1845() + 3.0f), y + 19, new Color(76, 194, 255).getRGB());
        return 18;
    }

    @Override
    public int method1673() {
        return this.fr.getWidth(this.field2095.getName()) + 50;
    }
}
