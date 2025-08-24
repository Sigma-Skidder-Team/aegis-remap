package nikoisntcat.client.managers;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.utils.MinecraftUtil;

public class Class230
extends MinecraftUtil {
    public boolean field2024 = false;
    public boolean field2025 = false;
    public boolean field2026 = false;
    public boolean field2027 = false;
    public boolean field2028 = false;
    private boolean field2029;
    public int field2030 = 0;
    public boolean field2031 = false;

    public void method1501(Packet class_25962) {
        this.field2029 = true;
        try {
            mc.method_1562().method_52787(class_25962);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        this.field2029 = false;
    }

    public boolean method1502() {
        return this.field2029;
    }

    public void onPacketSend(PacketSendEvent packetSendEvent) {
        ClientCommandC2SPacket ClientCommandC2SPacket2;
        if (packetSendEvent.isCancelled()) {
            return;
        }
        Packet<?> class_25962 = packetSendEvent.getPacket();
        if (class_25962 instanceof PlayerInteractEntityC2SPacket && ((PlayerInteractEntityC2SPacket)class_25962).type == PlayerInteractEntityC2SPacket.ATTACK) {
            this.field2024 = true;
        }
        if ((class_25962 = packetSendEvent.getPacket()) instanceof PlayerMoveC2SPacket && ((PlayerMoveC2SPacket)class_25962).changesPosition()) {
            this.field2030 = 0;
            this.field2028 = true;
        } else {
            ++this.field2030;
            this.field2028 = false;
        }
        class_25962 = packetSendEvent.getPacket();
        if (class_25962 instanceof ClientCommandC2SPacket && (ClientCommandC2SPacket2 = (ClientCommandC2SPacket)class_25962).getEntityId() == mc.player.getId()) {
            if (ClientCommandC2SPacket2.getMode() == ClientCommandC2SPacket.Mode.START_SPRINTING) {
                this.field2026 = true;
            } else if (ClientCommandC2SPacket2.getMode() == ClientCommandC2SPacket.Mode.STOP_SPRINTING) {
                this.field2026 = false;
            }
        }
    }

    public void method1503() {
        this.field2027 = true;
    }

    public void method1196() {
        this.field2025 = this.field2024;
        this.field2031 = this.field2027;
        this.field2024 = false;
        this.field2027 = false;
    }
}
