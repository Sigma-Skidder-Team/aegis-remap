package nikoisntcat.client.settings.impl;

import nikoisntcat.client.settings.Setting;

import java.util.List;
import java.util.function.Predicate;

public class ModeSetting
extends Setting {
    private String field2123;
    private List field2124;

    public List method1698() {
        return this.field2124;
    }

    public void method1696(String string) {
        this.field2123 = string;
    }

    public String method1699() {
        return this.field2123;
    }

    public ModeSetting(String string, String string2, List list) {
        this(string, string2, list, null);
    }

    public ModeSetting(String string, String string2, List list, Predicate predicate) {
        this.name = string;
        this.field2124 = list;
        this.field2123 = string2;
        this.field2125 = predicate;
    }
}
