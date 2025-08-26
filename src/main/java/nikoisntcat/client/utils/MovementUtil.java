package nikoisntcat.client.utils;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.util.math.MathHelper;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.BlinkEvent;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.MoveInputEvent;

public class MovementUtil extends MinecraftUtil {
    public static boolean shouldSendPackets;
    public static boolean isBlinking;
    public static int blinkTicks;


    public static void shouldBlink(BlinkEvent event) {
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
        double direction = getDirection();
        mc.player.setVelocity(-Math.sin(direction) * speed, mc.player.getVelocity().y, Math.cos(direction) * speed);
    }

    public static void strafe(double speed, float yaw) {
        if (!isMoving()) {
            return;
        }
        double direction = getDirection(yaw);
        mc.player.setVelocity(-Math.sin(direction) * speed, mc.player.getVelocity().y, Math.cos(direction) * speed);
    }

    public static double getMovementDirection(float rotationYaw, double moveForward, double moveStrafing) {
        if (moveForward < 0.0) {
            rotationYaw += 180.0f;
        }
        float forwardModifier = 1.0f;
        if (moveForward < 0.0) {
            forwardModifier = -0.5f;
        } else if (moveForward > 0.0) {
            forwardModifier = 0.5f;
        }
        if (moveStrafing > 0.0) {
            rotationYaw -= 90.0f * forwardModifier;
        }
        if (moveStrafing < 0.0) {
            rotationYaw += 90.0f * forwardModifier;
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

    /** Adjusts movement inputs to fix direction after rotation (a.k.a. move fix). */
    public static void moveFix(MoveInputEvent event, float yaw) {
        float forward = event.field1979; // likely "forward"
        float strafe = event.field1983;  // likely "strafe"

        double playerDirection = MathHelper.wrapDegrees(Math.toDegrees(getMovementDirection(mc.player.getYaw(), forward, strafe)));

        mc.player.ticksLeftToDoubleTapSprint = -1;

        if (forward == 0.0f && strafe == 0.0f) {
            return;
        }

        float bestForward = 0.0f;
        float bestStrafe = 0.0f;
        float minDifference = Float.MAX_VALUE;

        for (float testForward = -1.0f; testForward <= 1.0f; testForward += 1.0f) {
            for (float testStrafe = -1.0f; testStrafe <= 1.0f; testStrafe += 1.0f) {
                if (testStrafe == 0.0f && testForward == 0.0f) continue;

                double testDirection = MathHelper.wrapDegrees(
                        Math.toDegrees(getMovementDirection(yaw, testForward, testStrafe))
                );
                double difference = Math.abs(playerDirection - testDirection);

                if (difference < minDifference) {
                    minDifference = (float) difference;
                    bestForward = testForward;
                    bestStrafe = testStrafe;
                }
            }
        }

        event.field1979 = bestForward;
        event.field1983 = bestStrafe;
    }

    public static double getDirection() {
        return getDirection(mc.player.getYaw());
    }

    // Handles velocity packet modification when blinking
    public static void method1470(PacketReceiveEvent event) {
        Packet packet = event.getPacket();
        if (mc.player != null && isBlinking && packet instanceof EntityVelocityUpdateS2CPacket velocityPacket
                && velocityPacket.getEntityId() == mc.player.getId()) {

            if (shouldSendPackets) {
                event.cancel();
                mc.player.setVelocity(
                        velocityPacket.getVelocityX() * 0.6,
                        velocityPacket.getVelocityY(),
                        velocityPacket.getVelocityZ() * 0.6
                );
            }
            blinkTicks = 20;
        }
    }

    public static float getSpeed() {
        return (float) Math.sqrt(
                mc.player.getVelocity().x * mc.player.getVelocity().x +
                        mc.player.getVelocity().z * mc.player.getVelocity().z
        );
    }

    public static void startBlink() {
        if (!isBlinking) {
            isBlinking = true;
            blinkTicks = mc.player.ticksSinceLastPositionPacketSent;
            shouldSendPackets = AegisClient.packetUtil.lastTickHadMovementPacket
                    && AegisClient.packetUtil.lastPlayerPacketChangedPos;
        }
    }

    public static boolean isMoving() {
        return mc.player != null &&
                (mc.player.input.movementForward != 0.0f || mc.player.input.movementSideways != 0.0f);
    }

    public static double getDirection(float yaw) {
        float angle = yaw;

        if (mc.player.forwardSpeed < 0.0f) {
            angle += 180.0f;
        }

        float forwardModifier = 1.0f;
        if (mc.player.forwardSpeed < 0.0f) {
            forwardModifier = -0.5f;
        } else if (mc.player.forwardSpeed > 0.0f) {
            forwardModifier = 0.5f;
        }

        if (mc.player.sidewaysSpeed > 0.0f) {
            angle -= 90.0f * forwardModifier;
        }
        if (mc.player.sidewaysSpeed < 0.0f) {
            angle += 90.0f * forwardModifier;
        }

        return Math.toRadians(angle);
    }
}
