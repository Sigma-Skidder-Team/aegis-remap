package nikoisntcat.client.managers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.Packet;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketSendEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import nikoisntcat.client.modules.Module;

public class Class326 {
    public final LinkedList field2464 = new LinkedList();
    private boolean field2465 = false;
    private LinkedBlockingDeque field2466;
    public Module field2467 = null;
    private final List field2468 = new ArrayList();

    public boolean method2044() {
        return this.field2465;
    }

    public void onSendPacket(PacketSendEvent packetSendEvent) {
        if (packetSendEvent.isCancelled() || !this.field2465) {
            return;
        }
        Iterator iterator = this.field2468.iterator();
        while (iterator.hasNext()) {
            if ((Class)iterator.next() != packetSendEvent.getPacket().getClass()) continue;
            return;
        }
        this.field2466.add(packetSendEvent.getPacket());
        packetSendEvent.cancel();
    }

    public void method2046() {
        if (!this.field2464.isEmpty()) {
            LinkedBlockingDeque linkedBlockingDeque = (LinkedBlockingDeque)this.field2464.removeFirst();
            while (!linkedBlockingDeque.isEmpty()) {
                AegisClient.field2321.method1501((Packet)linkedBlockingDeque.poll());
            }
        } else {
            while (!this.field2466.isEmpty()) {
                AegisClient.field2321.method1501((Packet)this.field2466.poll());
            }
        }
    }

    public void method2047() {
        this.field2465 = false;
        this.method2052();
        this.method2050();
    }

    public void method2048(Class ... classArray) {
        this.field2468.addAll(List.of(classArray));
    }

    public void onTick() {
        if (MinecraftClient.getInstance().getNetworkHandler() == null) {
            this.field2465 = false;
            this.method2050();
        }
        if (!this.field2465) {
            return;
        }
        this.field2464.add(this.field2466);
        this.field2466 = new LinkedBlockingDeque();
    }

    public void method2050() {
        this.field2464.clear();
        this.field2466.clear();
        this.field2468.clear();
        this.field2465 = false;
        this.field2467 = null;
    }

    public Class326() {
        this.field2466 = new LinkedBlockingDeque();
    }

    public void method2051(Module module) {
        if (!this.field2465) {
            this.method2050();
            this.field2467 = module;
        }
        this.field2465 = true;
    }

    public void method2052() {
        while (!this.field2464.isEmpty()) {
            this.method2046();
        }
        this.method2046();
    }
}
