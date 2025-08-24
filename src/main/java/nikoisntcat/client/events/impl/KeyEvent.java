package nikoisntcat.client.events.impl;

import nikoisntcat.client.events.Event;

public class KeyEvent
extends Event {
    private boolean field1991;
    private int field1992;

    public int method1430() {
        return this.field1992;
    }

    public boolean method1431() {
        return this.field1991;
    }

    public KeyEvent(int n, boolean bl) {
        this.field1992 = n;
        this.field1991 = bl;
    }

    public void method1201(int n) {
        this.field1992 = n;
    }
}
