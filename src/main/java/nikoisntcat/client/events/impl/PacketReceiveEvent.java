package nikoisntcat.client.events.impl;

import net.minecraft.network.packet.Packet;
import nikoisntcat.client.events.CancellableEvent;

public class PacketReceiveEvent
extends CancellableEvent {
    private Packet<?> packet;

    public PacketReceiveEvent(Packet<?> class_25962) {
        this.packet = class_25962;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }
}
