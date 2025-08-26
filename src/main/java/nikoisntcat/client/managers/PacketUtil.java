package nikoisntcat.client.managers;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.utils.MinecraftUtil;

import java.util.Objects;

// TODO: this is probably used by the KillAura and Velocity modules, which are obfuscated inside natives
public class PacketUtil
        extends MinecraftUtil {
    public boolean sentAttack = false;
    public boolean sentAttackLastTick = false;
    public boolean serverSprinting = false;
    public boolean field2027 = false;
    public boolean lastPlayerPacketChangedPos = false;
    private boolean sendPacketWithoutEvent;
    public int ticksSincePositionChange = 0;
    public boolean lastTickHadMovementPacket = false;

    public void sendPacketSilently(Packet<?> pkt) {
        this.sendPacketWithoutEvent = true;
        try {
            Objects.requireNonNull(mc.getNetworkHandler()).sendPacket(pkt);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.sendPacketWithoutEvent = false;
    }

    public boolean sendPacketWithoutEvent() {
        return this.sendPacketWithoutEvent;
    }

    public void onSendPacket(PacketSendEvent packetSendEvent) {
        ClientCommandC2SPacket cmd;
        if (packetSendEvent.isCancelled()) {
            return;
        }
        Packet<?> pkt = packetSendEvent.getPacket();
        if (pkt instanceof PlayerInteractEntityC2SPacket && ((PlayerInteractEntityC2SPacket) pkt).type == PlayerInteractEntityC2SPacket.ATTACK) {
            this.sentAttack = true;
        }
        if ((pkt = packetSendEvent.getPacket()) instanceof PlayerMoveC2SPacket && ((PlayerMoveC2SPacket) pkt).changesPosition()) {
            this.ticksSincePositionChange = 0;
            this.lastPlayerPacketChangedPos = true;
        } else {
            ++this.ticksSincePositionChange;
            this.lastPlayerPacketChangedPos = false;
        }
        pkt = packetSendEvent.getPacket();
        if (pkt instanceof ClientCommandC2SPacket && (cmd = (ClientCommandC2SPacket) pkt).getEntityId() == mc.player.getId()) {
            if (cmd.getMode() == ClientCommandC2SPacket.Mode.START_SPRINTING) {
                this.serverSprinting = true;
            } else if (cmd.getMode() == ClientCommandC2SPacket.Mode.STOP_SPRINTING) {
                this.serverSprinting = false;
            }
        }
    }

    public void method1503() {
        this.field2027 = true;
    }

    public void onTick() {
        this.sentAttackLastTick = this.sentAttack;
        this.lastTickHadMovementPacket = this.field2027;
        this.sentAttack = false;
        this.field2027 = false;
    }
}
