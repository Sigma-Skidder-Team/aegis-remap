package nikoisntcat.client.managers;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.utils.MinecraftUtil;

import java.util.Objects;

// TODO: this is probably used by the KillAura and Velocity modules, which are obfuscated inside natives
public class PacketUtil extends MinecraftUtil {

    public boolean sentAttack = false;
    public boolean sentAttackLastTick = false;
    public boolean serverSprinting = false;
    public boolean movementPacketSent = false;
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

    public void onSendPacket(PacketSendEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Packet<?> pkt = event.getPacket();

        if (pkt instanceof PlayerInteractEntityC2SPacket interact
                && interact.type == PlayerInteractEntityC2SPacket.ATTACK) {
            this.sentAttack = true;
        }

        if (pkt instanceof PlayerMoveC2SPacket move && move.changesPosition()) {
            this.ticksSincePositionChange = 0;
            this.lastPlayerPacketChangedPos = true;
        } else {
            ++this.ticksSincePositionChange;
            this.lastPlayerPacketChangedPos = false;
        }

        if (pkt instanceof ClientCommandC2SPacket cmd
                && cmd.getEntityId() == mc.player.getId()) {
            if (cmd.getMode() == ClientCommandC2SPacket.Mode.START_SPRINTING) {
                this.serverSprinting = true;
            } else if (cmd.getMode() == ClientCommandC2SPacket.Mode.STOP_SPRINTING) {
                this.serverSprinting = false;
            }
        }
    }

    public void markMovementPacket() {
        this.movementPacketSent = true;
    }

    public void onTick() {
        this.sentAttackLastTick = this.sentAttack;
        this.lastTickHadMovementPacket = this.movementPacketSent;
        this.sentAttack = false;
        this.movementPacketSent = false;
    }
}
