package nikoisntcat.client.events.impl;

import net.minecraft.client.network.ClientPlayerEntity;
import nikoisntcat.client.events.CancellableEvent;

// TODO: the `e(Yaw,Pitch,X,Y,Z)` variables are probably just the previous values lol
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

    public void setEntityYaw(float yaw) {
        this.eYaw = yaw;
    }

    public MotionEvent(double eX, double eY, double eZ, boolean eOnGround, float eYaw, float ePitch, Timing timing) {
        this.eX = eX;
        this.eY = eY;
        this.eZ = eZ;
        this.ePitch = ePitch;
        this.eYaw = eYaw;
        this.eOnGround = eOnGround;
        this.timing = timing;
    }

    public double getEntityX() {
        return this.eX;
    }

    /** updates this player to have all the values from this event. **/
    public void updatePlayer(ClientPlayerEntity ent) {
        ent.setPos(this.x, this.y, this.z);
        ent.setOnGround(this.onGround);
        ent.setYaw(this.yaw);
        ent.setPitch(this.pitch);
    }

    public double getEntityY() {
        return this.eY;
    }

    public void setEntityPitch(float pitch) {
        this.ePitch = pitch;
    }

    public void updatePlayerToPrev(ClientPlayerEntity ent) {
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
