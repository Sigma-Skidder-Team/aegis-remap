package nikoisntcat.client.settings.impl;

import java.util.function.Predicate;
import nikoisntcat.client.settings.Setting;

public class NumberSetting
extends Setting {
    private double field2128;
    private double field2129;
    private double field2130;
    private double field2131;

    public double method1705() {
        return this.field2128;
    }

    public void method1706(double d) {
        this.field2131 = d;
    }

    public NumberSetting(String string, double d, double d2, double d3) {
        this(string, d, d2, d3, 0.01);
    }

    public double method1707() {
        return this.field2131;
    }

    public NumberSetting(String string, double d, double d2, double d3, double d4, Predicate predicate) {
        this.field2131 = d;
        this.name = string;
        this.field2128 = d3;
        this.field2129 = d2;
        this.field2130 = d4;
        this.field2125 = predicate;
    }

    public double method1708() {
        return this.field2129;
    }

    public NumberSetting(String string, double d, double d2, double d3, double d4) {
        this(string, d, d2, d3, d4, null);
    }

    public double method1709() {
        return this.field2130;
    }
}
