package nikoisntcat.client.modules.impl.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerPosition;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.utils.Priority;
import nikoisntcat.client.utils.RotationUtil;
import org.joml.Vector2f;

import java.util.Set;

public class NoRotationSetModule extends Module {

    public NoRotationSetModule() {
        super("NoRotationSet", 0, false, Category.MISC);
    }

    public void method1298(PlayerPositionLookS2CPacket packet, PlayerEntity playerEntity, ClientConnection clientPlayNetworkHandler) {
        float f = playerEntity.getYaw();
        float f2 = playerEntity.getPitch();
        playerEntity.getVelocity();
        if (!playerEntity.hasVehicle()) {
            method1297(packet.change(), packet.relatives(), (Entity)playerEntity, false);
        }
        clientPlayNetworkHandler.send((Packet)new TeleportConfirmC2SPacket(packet.teleportId()));
        clientPlayNetworkHandler.send((Packet)new PlayerMoveC2SPacket.Full(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), playerEntity.getYaw(), playerEntity.getPitch(), false, false));
        RotationUtil.rotate(new Vector2f(playerEntity.getYaw(), playerEntity.getPitch()), 0, Priority.field1511);
        playerEntity.setYaw(f);
        playerEntity.setPitch(f2);
    }

    private static boolean method1297(PlayerPosition pos, Set flags, Entity entity, boolean bl) {
        boolean bl2;
        PlayerPosition playerPosition = PlayerPosition.fromEntityLerpTarget((Entity)entity);
        PlayerPosition playerPosition2 = PlayerPosition.apply((PlayerPosition)playerPosition, (PlayerPosition)pos, (Set)flags);
        boolean bl3 = bl2 = playerPosition.position().squaredDistanceTo(playerPosition2.position()) > 4096.0;
        if (bl && !bl2) {
            entity.updateTrackedPositionAndAngles(playerPosition2.position().getX(), playerPosition2.position().getY(), playerPosition2.position().getZ(), playerPosition2.yaw(), playerPosition2.pitch(), 3);
            entity.setVelocity(playerPosition2.deltaMovement());
            return true;
        }
        entity.setPosition(playerPosition2.position());
        entity.setVelocity(playerPosition2.deltaMovement());
        entity.setYaw(playerPosition2.yaw());
        entity.setPitch(playerPosition2.pitch());
        PlayerPosition playerPosition3 = PlayerPosition.apply((PlayerPosition)new PlayerPosition(entity.getLastRenderPos(), Vec3d.ZERO, entity.prevYaw, entity.prevPitch), (PlayerPosition)pos, (Set)flags);
        entity.setPrevPositionAndAngles(playerPosition3.position(), playerPosition3.yaw(), playerPosition3.pitch());
        return false;
    }


}
