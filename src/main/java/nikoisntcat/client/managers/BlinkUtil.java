package nikoisntcat.client.managers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketSendEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import nikoisntcat.client.modules.Module;

public class BlinkUtil {
    // ?????? why
    public final LinkedList<LinkedBlockingDeque<Packet<?>>> previousHeldPackets = new LinkedList<>();
    private boolean blinking = false;
    private LinkedBlockingDeque<Packet<?>> heldPackets;
    public Module field2467 = null;
    private final List<Packet<?>> field2468 = new ArrayList<>();

    public boolean method2044() {
        return this.blinking;
    }

    public void onSendPacket(PacketSendEvent packetSendEvent) {
        if (packetSendEvent.isCancelled() || !this.blinking) {
            return;
        }
        for (Object o : this.field2468) {
            if (o != packetSendEvent.getPacket().getClass()) continue;
            return;
        }
        this.heldPackets.add(packetSendEvent.getPacket());
        packetSendEvent.cancel();
    }

    public void method2046() {
        if (!this.previousHeldPackets.isEmpty()) {
            var linkedBlockingDeque = this.previousHeldPackets.removeFirst();
            while (!linkedBlockingDeque.isEmpty()) {
                AegisClient.packetUtil.sendPacketSilently(linkedBlockingDeque.poll());
            }
        } else {
            while (!this.heldPackets.isEmpty()) {
                AegisClient.packetUtil.sendPacketSilently(this.heldPackets.poll());
            }
        }
    }

    public void method2047() {
        this.blinking = false;
        this.method2052();
        this.method2050();
    }

    public void method2048(Packet<?>... classArray) {
        this.field2468.addAll(List.of(classArray));
    }

    public void onTick() {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            this.blinking = false;
            this.method2050();
        }
        if (!this.blinking) {
            return;
        }
        this.previousHeldPackets.add(this.heldPackets);
        this.heldPackets = new LinkedBlockingDeque();
    }

    public void method2050() {
        this.previousHeldPackets.clear();
        this.heldPackets.clear();
        this.field2468.clear();
        this.blinking = false;
        this.field2467 = null;
    }

    public BlinkUtil() {
        this.heldPackets = new LinkedBlockingDeque();
    }

    public void method2051(Module module) {
        if (!this.blinking) {
            this.method2050();
            this.field2467 = module;
        }
        this.blinking = true;
    }

    public void method2052() {
        while (!this.previousHeldPackets.isEmpty()) {
            this.method2046();
        }
        this.method2046();
    }
}
