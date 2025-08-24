package nikoisntcat.client.events.impl;

import nikoisntcat.client.events.CancellableEvent;

public class ChatMessageEvent
extends CancellableEvent {
    private String field1976;

    public ChatMessageEvent(String string) {
        this.field1976 = string;
    }

    public String method1421() {
        return this.field1976;
    }
}