package nikoisntcat.client.events.impl;

import net.minecraft.network.packet.Packet;
import nikoisntcat.client.events.CancellableEvent;

public class PacketSendEvent extends CancellableEvent {
    private final Packet<?> packet;

    public PacketSendEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }
}
