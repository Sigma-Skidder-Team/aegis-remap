package nikoisntcat.client.settings.impl;

import java.awt.Color;
import java.util.function.Predicate;

public class ColorSetting {
    public final NumberSetting field2324;
    public final boolean field2325;
    public final NumberSetting field2326;
    public final NumberSetting field2327;
    public final NumberSetting field2328;

    public int method1907() {
        return new Color((int)this.field2327.getValue(), (int)this.field2324.getValue(), (int)this.field2328.getValue(), (int)this.field2326.getValue()).getRGB();
    }

    public Color method1908() {
        return new Color((int)this.field2327.getValue(), (int)this.field2324.getValue(), (int)this.field2328.getValue(), (int)this.field2326.getValue());
    }

    public void method1909(float f, float f2, float f3, float f4) {
        this.field2327.setValue(f);
        this.field2324.setValue(f2);
        this.field2328.setValue(f3);
        this.field2326.setValue(f4);
    }

    public void method1910(float f) {
        this.field2326.setValue(f);
    }

    public void method1912(Color color) {
        this.field2327.setValue(color.getRed());
        this.field2324.setValue(color.getGreen());
        this.field2328.setValue(color.getBlue());
        this.field2326.setValue(color.getAlpha());
    }

    public ColorSetting(String string, boolean bl, Predicate predicate) {
        this.field2327 = new NumberSetting(string + "_R", 0.0, 255.0, 0.0, 1.0, predicate);
        this.field2324 = new NumberSetting(string + "_G", 0.0, 255.0, 0.0, 1.0, predicate);
        this.field2328 = new NumberSetting(string + "_B", 0.0, 255.0, 0.0, 1.0, predicate);
        this.field2326 = bl ? new NumberSetting(string + "_Alpha", 0.0, 255.0, 0.0, 1.0, predicate) : new NumberSetting(string + "_Alpha", 255.0, 255.0, 0.0, 1.0, setting -> false);
        this.field2325 = bl;
    }

    public ColorSetting(String string, boolean bl) {
        this.field2327 = new NumberSetting(string + "_R", 0.0, 255.0, 0.0, 1.0);
        this.field2324 = new NumberSetting(string + "_G", 0.0, 255.0, 0.0, 1.0);
        this.field2328 = new NumberSetting(string + "_B", 0.0, 255.0, 0.0, 1.0);
        this.field2325 = bl;
        this.field2326 = new NumberSetting(string + "_Alpha", 0.0, 255.0, 0.0, 1.0, setting -> bl);
    }
}
