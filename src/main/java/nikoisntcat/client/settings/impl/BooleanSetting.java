package nikoisntcat.client.settings.impl;

import nikoisntcat.client.settings.Setting;

import java.util.function.Predicate;

public class BooleanSetting
extends Setting {
    private boolean field2127;

    public BooleanSetting(String string, boolean bl, Predicate predicate) {
        this.name = string;
        this.field2127 = bl;
        this.pred = predicate;
    }

    public boolean method1703() {
        return this.field2127;
    }

    public BooleanSetting(String string, boolean bl) {
        this(string, bl, null);
    }

    public void method1704(boolean bl) {
        this.field2127 = bl;
    }
}