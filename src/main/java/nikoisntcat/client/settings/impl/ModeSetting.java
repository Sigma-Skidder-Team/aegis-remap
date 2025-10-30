package nikoisntcat.client.settings.impl;

import nikoisntcat.client.settings.Setting;

import java.util.List;
import java.util.function.Predicate;

public class ModeSetting
        extends Setting {
    private String value;
    private final List<String> values;

    public List<String> getValues() {
        return this.values;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public ModeSetting(String name, String value, List<String> values) {
        this(name, value, values, null);
    }

    public ModeSetting(String string, String value, List<String> values, Predicate<Setting> show) {
        this.name = string;
        this.values = values;
        this.value = value;
        this.show = show;
    }
}
