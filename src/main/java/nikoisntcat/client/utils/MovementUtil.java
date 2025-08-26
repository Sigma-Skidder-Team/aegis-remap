package nikoisntcat.client.utils;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.math.MathHelper;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.Class213;
import nikoisntcat.client.events.impl.MoveInputEvent;

public class MovementUtil extends MinecraftUtil {
    public static boolean shouldSendPackets;
    public static boolean isBlinking;
    public static int blinkTicks;

    public static void method1462(Class213 event) {
        if (shouldSendPackets) {
            if (isBlinking) {
                event.cancel();
            }
        } else {
            shouldSendPackets = true;
        }
    }

    public static void stopBlink() {
        isBlinking = false;
        blinkTicks = 0;
    }

    public static void strafe(double speed) {
        if (!isMoving()) {
            return;
        }
        double d = getDirection();
        mc.player.setVelocity(-Math.sin(d) * speed, mc.player.getVelocity().y, Math.cos(d) * speed);
    }

    public static void strafe(double speed, float yaw) {
        if (!isMoving()) {
            return;
        }
        double d = getDirection(yaw);
        mc.player.setVelocity(-Math.sin(d) * speed, mc.player.getVelocity().y, Math.cos(d) * speed);
    }

    public static double getMovementDirection(float rotationYaw, double moveForward, double moveStrafing) {
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
        isBlinking = false;
        blinkTicks = 0;
        shouldSendPackets = false;
    }

    public static void strafe() {
        strafe(getSpeed());
    }

    public static void moveFix(MoveInputEvent event, float yaw) {
        float f = event.field1979;
        float f2 = event.field1983;
        double d = MathHelper.wrapDegrees((double) Math.toDegrees(getMovementDirection(mc.player.getYaw(), f, f2)));
        if (!mc.player.isSprinting()) {
            // empty if block
        }
        mc.player.ticksLeftToDoubleTapSprint = -1;
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
                if (f7 == 0.0f && f6 == 0.0f || !((d3 = Math.abs(d - (d2 = MathHelper.wrapDegrees((double) Math.toDegrees(getMovementDirection(yaw, f6, f7)))))) < (double) f5))
                    continue;
                f5 = (float) d3;
                f3 = f6;
                f4 = f7;
            }
        }
        event.field1979 = f3;
        event.field1983 = f4;
    }

    public static double getDirection() {
        return getDirection(mc.player.getYaw());
    }

    public static void method1470(PacketReceiveEvent event) {
        EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket;
        Packet packet;
        if (mc.player != null && isBlinking && (packet = event.getPacket()) instanceof EntityVelocityUpdateS2CPacket && (entityVelocityUpdateS2CPacket = (EntityVelocityUpdateS2CPacket) packet).getEntityId() == mc.player.getId()) {
            if (shouldSendPackets) {
                event.cancel();
                mc.player.setVelocity(entityVelocityUpdateS2CPacket.getVelocityX() * 0.6, entityVelocityUpdateS2CPacket.getVelocityY(), entityVelocityUpdateS2CPacket.getVelocityZ() * 0.6);
            }
            blinkTicks = 20;
        }
    }

    public static float getSpeed() {
        return (float) Math.sqrt(mc.player.getVelocity().x * mc.player.getVelocity().x + mc.player.getVelocity().z * mc.player.getVelocity().z);
    }

    public static void startBlink() {
        if (!isBlinking) {
            isBlinking = true;
            blinkTicks = mc.player.ticksSinceLastPositionPacketSent;
            shouldSendPackets = AegisClient.packetUtil.lastTickHadMovementPacket && AegisClient.packetUtil.lastPlayerPacketChangedPos;
        }
    }

    public static boolean isMoving() {
        return mc.player != null && (mc.player.input.movementForward != 0.0f || mc.player.input.movementSideways != 0.0f);
    }

    public static double getDirection(float yaw) {
        float f = yaw;
        if (mc.player.forwardSpeed < 0.0f) {
            f += 180.0f;
        }
        float f2 = 1.0f;
        if (mc.player.forwardSpeed < 0.0f) {
            f2 = -0.5f;
        } else if (mc.player.forwardSpeed > 0.0f) {
            f2 = 0.5f;
        }
        if (mc.player.sidewaysSpeed > 0.0f) {
            f -= 90.0f * f2;
        }
        if (mc.player.sidewaysSpeed < 0.0f) {
            f += 90.0f * f2;
        }
        return Math.toRadians(f);
    }
}
