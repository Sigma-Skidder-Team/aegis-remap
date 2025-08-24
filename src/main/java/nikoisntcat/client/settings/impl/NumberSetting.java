package nikoisntcat.client.settings.impl;

import java.util.function.Predicate;
import nikoisntcat.client.settings.Setting;

public class NumberSetting
extends Setting {
    private double min;
    private double max;
    private double minPerc;
    private double value;

    public double getMin() {
        return this.min;
    }

    public void setValue(double d) {
        this.value = d;
    }

    public NumberSetting(String name, double normalValue, double maxValue, double minValue) {
        this(name, normalValue, maxValue, minValue, 0.01);
    }

    public double getValue() {
        return this.value;
    }

    public NumberSetting(String name, double normalValue, double maxValue, double minValue, double minPerc, Predicate pred) {
        this.value = normalValue;
        this.name = name;
        this.min = minValue;
        this.max = maxValue;
        this.minPerc = minPerc;
        this.pred = pred;
    }

    public double getMax() {
        return this.max;
    }

    public NumberSetting(String name, double normalValue, double maxValue, double minValue, double minPerc) {
        this(name, normalValue, maxValue, minValue, minPerc, null);
    }

    public double getMinPerc() {
        return this.minPerc;
    }
}
