package nikoisntcat.client.managers;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.utils.MinecraftUtil;
import org.joml.Vector2f;

public class MotionManager extends MinecraftUtil {
    private float speed;
    private float fallDistance;
    private boolean onGround;
    private Vector2f pretRots;
    private float sidewaysSpeed;
    private float forwardsSpeed;
    private Vec3d prevPos;
    private Vector2f rots;
    public boolean field2020;
    private Vec3d velocity;
    private boolean sprinting;
    private Vec3d pos;

    public void method1496() {
        try {
            mc.world.tickEntity((Entity)mc.player);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onSendPacket(PacketSendEvent packetSendEvent) {
        if (this.field2020) {
            packetSendEvent.cancel();
        }
    }

    public void method1499() {
        this.pos = new Vec3d(mc.player.getPos().getX(), mc.player.getPos().getY(), mc.player.getPos().getZ());
        this.prevPos = new Vec3d(mc.player.prevX, mc.player.prevY, mc.player.prevZ);
        this.rots = new Vector2f(mc.player.getYaw(), mc.player.getPitch());
        this.pretRots = new Vector2f(mc.player.prevYaw, mc.player.prevPitch);
        this.velocity = new Vec3d(mc.player.getVelocity().getX(), mc.player.getVelocity().getY(), mc.player.getVelocity().getZ());
        this.fallDistance = mc.player.fallDistance;
        this.sprinting = mc.player.isSprinting();
        this.forwardsSpeed = mc.player.forwardSpeed;
        this.sidewaysSpeed = mc.player.sidewaysSpeed;
        this.onGround = mc.player.isOnGround();
        this.speed = mc.player.speed;
        this.field2020 = true;
        AegisClient.moduleManager.modules.forEach(module -> {
            module.active = false;
        });
    }

    public void set() {
        mc.player.sidewaysSpeed = this.sidewaysSpeed;
        mc.player.speed = this.speed;
        mc.player.setSprinting(this.sprinting);
        mc.player.forwardSpeed = this.forwardsSpeed;
        mc.player.setOnGround(this.onGround);
        mc.player.fallDistance = this.fallDistance;
        mc.player.setVelocity(this.velocity);
        mc.player.setPosition(this.pos);
        mc.player.prevX = this.prevPos.x;
        mc.player.prevY = this.prevPos.y;
        mc.player.prevZ = this.prevPos.z;
        mc.player.setYaw(this.rots.x);
        mc.player.setYaw(this.rots.y);
        mc.player.prevYaw = this.pretRots.x;
        mc.player.prevPitch = this.pretRots.y;
        this.field2020 = false;
        AegisClient.moduleManager.modules.forEach(module -> {
            module.active = true;
        });
    }

}
