package nikoisntcat.client.managers;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.utils.MinecraftUtil;
import org.joml.Vector2f;

public class MotionManager extends MinecraftUtil {
    private float field2012;
    private float field2013;
    private boolean field2014;
    private Vector2f field2015;
    private float field2016;
    private float field2017;
    private Vec3d field2018;
    private Vector2f field2019;
    public boolean field2020;
    private Vec3d field2021;
    private boolean field2022;
    private Vec3d field2023;

    public void method1496() {
        try {
            mc.world.tickEntity((Entity)mc.player);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onPacketSend(PacketSendEvent packetSendEvent) {
        if (this.field2020) {
            packetSendEvent.cancel();
        }
    }

    public void method1499() {
        this.field2023 = new Vec3d(mc.player.getPos().getX(), mc.player.getPos().getY(), mc.player.getPos().getZ());
        this.field2018 = new Vec3d(mc.player.prevX, mc.player.prevY, mc.player.prevZ);
        this.field2019 = new Vector2f(mc.player.getYaw(), mc.player.getPitch());
        this.field2015 = new Vector2f(mc.player.prevYaw, mc.player.prevPitch);
        this.field2021 = new Vec3d(mc.player.getVelocity().getX(), mc.player.getVelocity().getY(), mc.player.getVelocity().getZ());
        this.field2013 = mc.player.fallDistance;
        this.field2022 = mc.player.isSprinting();
        this.field2017 = mc.player.forwardSpeed;
        this.field2016 = mc.player.sidewaysSpeed;
        this.field2014 = mc.player.isOnGround();
        this.field2012 = mc.player.speed;
        this.field2020 = true;
        AegisClient.moduleManager.field2009.forEach(module -> {
            module.field1594 = false;
        });
    }

    public void method1500() {
        mc.player.sidewaysSpeed = this.field2016;
        mc.player.speed = this.field2012;
        mc.player.setSprinting(this.field2022);
        mc.player.forwardSpeed = this.field2017;
        mc.player.setOnGround(this.field2014);
        mc.player.fallDistance = this.field2013;
        mc.player.setVelocity(this.field2021);
        mc.player.setPosition(this.field2023);
        mc.player.prevX = this.field2018.x;
        mc.player.prevY = this.field2018.y;
        mc.player.prevZ = this.field2018.z;
        mc.player.setYaw(this.field2019.x);
        mc.player.setYaw(this.field2019.y);
        mc.player.prevYaw = this.field2015.x;
        mc.player.prevPitch = this.field2015.y;
        this.field2020 = false;
        AegisClient.moduleManager.field2009.forEach(module -> {
            module.field1594 = true;
        });
    }

}
