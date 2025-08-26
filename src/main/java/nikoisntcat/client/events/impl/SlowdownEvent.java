package nikoisntcat.client.events.impl;

import nikoisntcat.client.events.Event;

public class SlowdownEvent
extends Event {
    private boolean slowdown;

    public boolean getSlowdown() {
        return this.slowdown;
    }

    public SlowdownEvent(boolean bl) {
        this.slowdown = bl;
    }

    public void setSlowdown(boolean bl) {
        this.slowdown = bl;
    }
}