package nikoisntcat.client.events.impl;

import net.minecraft.client.network.ClientPlayerEntity;
import nikoisntcat.client.events.CancellableEvent;

public class MotionEvent extends CancellableEvent {
    private Timing timing;
    private boolean onGround;
    private float eYaw;
    private float pitch;
    private double y;
    private float ePitch;
    private double eY;
    private double x;
    private double eZ;
    private double eX;
    private boolean eOnGround;
    private double z;
    private float yaw;

    public Timing getTiming() {
        return this.timing;
    }

    public void method1400(float f) {
        this.eYaw = f;
    }

    public MotionEvent(double d, double d2, double d3, boolean bl, float f, float f2, Timing timing) {
        this.eX = d;
        this.eY = d2;
        this.eZ = d3;
        this.ePitch = f2;
        this.eYaw = f;
        this.eOnGround = bl;
        this.timing = timing;
    }

    public double method1401() {
        return this.eX;
    }

    public void method1402(ClientPlayerEntity ent) {
        ent.setPos(this.x, this.y, this.z);
        ent.setOnGround(this.onGround);
        ent.setYaw(this.yaw);
        ent.setPitch(this.pitch);
    }

    public double method1403() {
        return this.eY;
    }

    public void method1404(float f) {
        this.ePitch = f;
    }

    public void method1405(ClientPlayerEntity ent) {
        this.x = ent.getX();
        this.y = ent.getY();
        this.z = ent.getZ();
        this.yaw = ent.getYaw();
        this.pitch = ent.getPitch();
        this.onGround = ent.isOnGround();
        ent.setPos(this.eX, this.eY, this.eZ);
        ent.setOnGround(this.eOnGround);
        ent.setYaw(this.eYaw);
        ent.setPitch(this.ePitch);
    }

    public void setEOnGround(boolean onGround) {
        this.eOnGround = onGround;
    }

    public double getEntityZ() {
        return this.eZ;
    }

    public void setEntityX(double x) {
        this.eX = x;
    }

    public void setEntityY(double y) {
        this.eY = y;
    }

    public float getEntityYaw() {
        return this.eYaw;
    }

    public void setTiming(Timing timing) {
        this.timing = timing;
    }

    public boolean getEntityOnGround() {
        return this.eOnGround;
    }

    public void setEntityZ(double z) {
        this.eZ = z;
    }

    public float getEntityPitch() {
        return this.ePitch;
    }

    public enum Timing {
        PRE, POST
    }
}
