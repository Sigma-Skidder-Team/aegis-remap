package nikoisntcat.client.events.impl;

import nikoisntcat.client.events.Event;

public class SlowdownEvent
extends Event {
    private boolean field1993;

    public boolean method1432() {
        return this.field1993;
    }

    public SlowdownEvent(boolean bl) {
        this.field1993 = bl;
    }

    public void method1433(boolean bl) {
        this.field1993 = bl;
    }
}