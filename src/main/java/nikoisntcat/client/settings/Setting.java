package nikoisntcat.client.settings;

import java.util.function.Predicate;

public class Setting {
    protected Predicate<Setting> show;
    protected String name;

    public Predicate<Setting> getShow() {
        return this.show;
    }

    public boolean shouldShow() {
        return this.getShow() == null || this.getShow().test(this);
    }

    public String getName() {
        return this.name;
    }
}