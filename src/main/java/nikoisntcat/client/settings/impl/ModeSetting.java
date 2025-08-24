package nikoisntcat.client.settings.impl;

import nikoisntcat.client.settings.Setting;

import java.util.List;
import java.util.function.Predicate;

public class ModeSetting
extends Setting {
    private String field2123;
    private List<String> field2124;

    public List<String> method1698() {
        return this.field2124;
    }

    public void method1696(String string) {
        this.field2123 = string;
    }

    public String getValue() {
        return this.field2123;
    }

    public ModeSetting(String string, String string2, List<String> list) {
        this(string, string2, list, null);
    }

    public ModeSetting(String string, String string2, List<String> list, Predicate predicate) {
        this.name = string;
        this.field2124 = list;
        this.field2123 = string2;
        this.pred = predicate;
    }
}
