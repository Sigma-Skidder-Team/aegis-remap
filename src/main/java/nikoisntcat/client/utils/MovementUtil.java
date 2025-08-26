package nikoisntcat.client.utils;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.math.MathHelper;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.Class213;
import nikoisntcat.client.events.impl.MoveInputEvent;

public class MovementUtil extends MinecraftUtil {
    public static boolean field2006;
    public static boolean field2007;
    public static int field2008;

    public static void method1462(Class213 event) {
        if (field2006) {
            if (field2007) {
                event.cancel();
            }
        } else {
            field2006 = true;
        }
    }

    public static void method1463() {
        field2007 = false;
        field2008 = 0;
    }

    public static void strafe(double speed) {
        if (!MovementUtil.method1472()) {
            return;
        }
        double d = MovementUtil.getDirection();
        MovementUtil.mc.player.setVelocity(-Math.sin(d) * speed, MovementUtil.mc.player.getVelocity().y, Math.cos(d) * speed);
    }

    public static void strafe(double speed, float yaw) {
        if (!MovementUtil.method1472()) {
            return;
        }
        double d = MovementUtil.getDirection(yaw);
        MovementUtil.mc.player.setVelocity(-Math.sin(d) * speed, MovementUtil.mc.player.getVelocity().y, Math.cos(d) * speed);
    }

    public static double method1466(float rotationYaw, double moveForward, double moveStrafing) {
        if (moveForward < 0.0) {
            rotationYaw += 180.0f;
        }
        float f = 1.0f;
        if (moveForward < 0.0) {
            f = -0.5f;
        } else if (moveForward > 0.0) {
            f = 0.5f;
        }
        if (moveStrafing > 0.0) {
            rotationYaw -= 90.0f * f;
        }
        if (moveStrafing < 0.0) {
            rotationYaw += 90.0f * f;
        }
        return Math.toRadians(rotationYaw);
    }

    static {
        field2007 = false;
        field2008 = 0;
        field2006 = false;
    }

    public static void method1467() {
        MovementUtil.strafe(MovementUtil.method1417());
    }

    public static void method1468(MoveInputEvent event, float yaw) {
        float f = event.field1979;
        float f2 = event.field1983;
        double d = MathHelper.wrapDegrees((double)Math.toDegrees(MovementUtil.method1466(MovementUtil.mc.player.getYaw(), f, f2)));
        if (!MovementUtil.mc.player.isSprinting()) {
            // empty if block
        }
        MovementUtil.mc.player.ticksLeftToDoubleTapSprint = -1;
        if (f == 0.0f && f2 == 0.0f) {
            return;
        }
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = Float.MAX_VALUE;
        for (float f6 = -1.0f; f6 <= 1.0f; f6 += 1.0f) {
            for (float f7 = -1.0f; f7 <= 1.0f; f7 += 1.0f) {
                double d2;
                double d3;
                if (f7 == 0.0f && f6 == 0.0f || !((d3 = Math.abs(d - (d2 = MathHelper.wrapDegrees((double)Math.toDegrees(MovementUtil.method1466(yaw, f6, f7)))))) < (double)f5)) continue;
                f5 = (float)d3;
                f3 = f6;
                f4 = f7;
            }
        }
        event.field1979 = f3;
        event.field1983 = f4;
    }

    public static double getDirection() {
        return MovementUtil.getDirection(MovementUtil.mc.player.getYaw());
    }

    public static void method1470(PacketReceiveEvent event) {
        EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket;
        Packet packet;
        if (MovementUtil.mc.player != null && field2007 && (packet = event.getPacket()) instanceof EntityVelocityUpdateS2CPacket && (entityVelocityUpdateS2CPacket = (EntityVelocityUpdateS2CPacket)packet).getEntityId() == MovementUtil.mc.player.getId()) {
            if (field2006) {
                event.cancel();
                MovementUtil.mc.player.setVelocity(entityVelocityUpdateS2CPacket.getVelocityX() * 0.6, entityVelocityUpdateS2CPacket.getVelocityY(), entityVelocityUpdateS2CPacket.getVelocityZ() * 0.6);
            }
            field2008 = 20;
        }
    }

    public static float method1417() {
        return (float)Math.sqrt(MovementUtil.mc.player.getVelocity().x * MovementUtil.mc.player.getVelocity().x + MovementUtil.mc.player.getVelocity().z * MovementUtil.mc.player.getVelocity().z);
    }

    public static void method1471() {
        if (!field2007) {
            field2007 = true;
            field2008 = MovementUtil.mc.player.ticksSinceLastPositionPacketSent;
            field2006 = AegisClient.packetUtil.field2027LastTick && AegisClient.packetUtil.lastPlayerPacketChangedPos;
        }
    }

    public static boolean method1472() {
        return MovementUtil.mc.player != null && (MovementUtil.mc.player.input.movementForward != 0.0f || MovementUtil.mc.player.input.movementSideways != 0.0f);
    }

    public static double getDirection(float yaw) {
        float f = yaw;
        if (MovementUtil.mc.player.forwardSpeed < 0.0f) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (MovementUtil.mc.player.forwardSpeed < 0.0f) {
            f2 = -0.5f;
        } else if (MovementUtil.mc.player.forwardSpeed > 0.0f) {
            f2 = 0.5f;
        }
        if (MovementUtil.mc.player.sidewaysSpeed > 0.0f) {
            f -= 90.0f * f2;
        }
        if (MovementUtil.mc.player.sidewaysSpeed < 0.0f) {
            f += 90.0f * f2;
        }
        return Math.toRadians(f);
    }
}
