package nikoisntcat.client.events.impl;

import net.minecraft.network.packet.Packet;
import nikoisntcat.client.events.CancellableEvent;

public class Class212
extends CancellableEvent {
    private Packet field1975;

    public Class212(Packet class_25962) {
        this.field1975 = class_25962;
    }

    public Packet method1420() {
        return this.field1975;
    }
}
