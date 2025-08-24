package nikoisntcat.client.settings;

import java.util.function.Predicate;

public class Setting {
    protected Predicate field2125;
    protected String name;

    public Predicate method1700() {
        return this.field2125;
    }

    public boolean method1701() {
        return this.method1700() == null || this.method1700().test(this);
    }

    public String getName() {
        return this.name;
    }
}