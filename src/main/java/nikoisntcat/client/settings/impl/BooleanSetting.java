package nikoisntcat.client.settings.impl;

import nikoisntcat.client.settings.Setting;

import java.util.function.Predicate;

public class BooleanSetting
extends Setting {
    private boolean value;

    public BooleanSetting(String string, boolean bl, Predicate predicate) {
        this.name = string;
        this.value = bl;
        this.pred = predicate;
    }

    public boolean getValue() {
        return this.value;
    }

    public BooleanSetting(String string, boolean bl) {
        this(string, bl, null);
    }

    public void setValue(boolean bl) {
        this.value = bl;
    }
}