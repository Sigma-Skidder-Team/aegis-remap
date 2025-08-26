package nikoisntcat.client.managers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.modules.Module;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

public class BlinkUtil {

    public final LinkedList<LinkedBlockingDeque<Packet<?>>> previousPacketQueues = new LinkedList<>();
    private boolean isBlinking = false;
    private LinkedBlockingDeque<Packet<?>> heldPackets;
    public Module activeModule = null;
    private final List<Packet<?>> ignoredPacketTypes = new ArrayList<>();

    public BlinkUtil() {
        this.heldPackets = new LinkedBlockingDeque<>();
    }

    public boolean isBlinking() {
        return this.isBlinking;
    }

    public void onSendPacket(PacketSendEvent event) {
        if (event.isCancelled() || !this.isBlinking) return;

        for (Object ignored : this.ignoredPacketTypes) {
            if (ignored != event.getPacket().getClass()) continue;
            return;
        }

        this.heldPackets.add(event.getPacket());
        event.cancel();
    }

    public void sendHeldPackets() {
        if (!this.previousPacketQueues.isEmpty()) {
            var queue = this.previousPacketQueues.removeFirst();
            while (!queue.isEmpty()) {
                AegisClient.packetUtil.sendPacketSilently(queue.poll());
            }
        } else {
            while (!this.heldPackets.isEmpty()) {
                AegisClient.packetUtil.sendPacketSilently(this.heldPackets.poll());
            }
        }
    }

    public void stopBlinking() {
        this.isBlinking = false;
        this.flushPreviousPackets();
        this.clearAll();
    }

    public void ignorePackets(Packet<?>... packets) {
        this.ignoredPacketTypes.addAll(List.of(packets));
    }

    public void onTick() {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            this.isBlinking = false;
            this.clearAll();
            return;
        }

        if (!this.isBlinking) return;

        this.previousPacketQueues.add(this.heldPackets);
        this.heldPackets = new LinkedBlockingDeque<>();
    }

    public void clearAll() {
        this.previousPacketQueues.clear();
        this.heldPackets.clear();
        this.ignoredPacketTypes.clear();
        this.isBlinking = false;
        this.activeModule = null;
    }

    public void startBlinking(Module module) {
        if (!this.isBlinking) {
            this.clearAll();
            this.activeModule = module;
        }
        this.isBlinking = true;
    }

    public void flushPreviousPackets() {
        while (!this.previousPacketQueues.isEmpty()) {
            this.sendHeldPackets();
        }
        this.sendHeldPackets();
    }
}
