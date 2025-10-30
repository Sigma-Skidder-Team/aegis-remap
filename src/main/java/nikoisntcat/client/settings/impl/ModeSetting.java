package nikoisntcat.client.settings.impl;

import nikoisntcat.client.settings.Setting;

import java.util.List;
import java.util.function.Predicate;

public class ModeSetting
        extends Setting {
    private String value;
    private List<String> values;

    public List<String> getValues() {
        return this.values;
    }

    public void setValue(String string) {
        this.value = string;
    }

    public String getValue() {
        return this.value;
    }

    public ModeSetting(String string, String string2, List<String> list) {
        this(string, string2, list, null);
    }

    public ModeSetting(String string, String string2, List<String> list, Predicate predicate) {
        this.name = string;
        this.values = list;
        this.value = string2;
        this.show = predicate;
    }
}
