package nikoisntcat.client.settings.impl;

import nikoisntcat.client.settings.Setting;

import java.awt.Color;
import java.util.function.Predicate;

public class ColorSetting {
    public final NumberSetting g;
    public final boolean bl;
    public final NumberSetting a;
    public final NumberSetting r;
    public final NumberSetting b;

    public int getRGB() {
        return new Color((int)this.r.getValue(), (int)this.g.getValue(), (int)this.b.getValue(), (int)this.a.getValue()).getRGB();
    }

    public Color getColor() {
        return new Color((int)this.r.getValue(), (int)this.g.getValue(), (int)this.b.getValue(), (int)this.a.getValue());
    }

    public void setColor(float r, float g, float b, float a) {
        this.r.setValue(r);
        this.g.setValue(g);
        this.b.setValue(b);
        this.a.setValue(a);
    }

    public void setAlpha(float a) {
        this.a.setValue(a);
    }

    public void setColor(Color color) {
        this.r.setValue(color.getRed());
        this.g.setValue(color.getGreen());
        this.b.setValue(color.getBlue());
        this.a.setValue(color.getAlpha());
    }

    public ColorSetting(String string, boolean bl, Predicate<Setting> show) {
        this.r = new NumberSetting(
                string + "_R", 0.0,
                255.0, 0.0,
                1.0, show
        );
        this.g = new NumberSetting(
                string + "_G", 0.0,
                255.0, 0.0,
                1.0, show
        );
        this.b = new NumberSetting(
                string + "_B", 0.0,
                255.0, 0.0,
                1.0, show
        );
        this.a = bl ? new NumberSetting(
                string + "_Alpha", 0.0,
                255.0, 0.0,
                1.0, show
        ) : new NumberSetting(
                string + "_Alpha", 255.0,
                255.0, 0.0,
                1.0, setting -> false
        );
        this.bl = bl;
    }

    public ColorSetting(String string, boolean bl) {
        this.r = new NumberSetting(string + "_R", 0.0, 255.0, 0.0, 1.0);
        this.g = new NumberSetting(string + "_G", 0.0, 255.0, 0.0, 1.0);
        this.b = new NumberSetting(string + "_B", 0.0, 255.0, 0.0, 1.0);
        this.bl = bl;
        this.a = new NumberSetting(string + "_Alpha", 0.0, 255.0, 0.0, 1.0, setting -> bl);
    }
}
