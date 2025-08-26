package nikoisntcat.client.events.impl;

import net.minecraft.client.network.ClientPlayerEntity;
import nikoisntcat.client.events.CancellableEvent;

public class MotionEvent extends CancellableEvent {
    private State state;
    private boolean onGround;
    private float field1959;
    private float pitch;
    private double y;
    private float field1962;
    private double field1963;
    private double x;
    private double field1965;
    private double field1966;
    private boolean field1967;
    private double z;
    private float yaw;

    public State getState() {
        return this.state;
    }

    public void method1400(float f) {
        this.field1959 = f;
    }

    public MotionEvent(double d, double d2, double d3, boolean bl, float f, float f2, State state) {
        this.field1966 = d;
        this.field1963 = d2;
        this.field1965 = d3;
        this.field1962 = f2;
        this.field1959 = f;
        this.field1967 = bl;
        this.state = state;
    }

    public double method1401() {
        return this.field1966;
    }

    public void method1402(ClientPlayerEntity ent) {
        ent.setPos(this.x, this.y, this.z);
        ent.setOnGround(this.onGround);
        ent.setYaw(this.yaw);
        ent.setPitch(this.pitch);
    }

    public double method1403() {
        return this.field1963;
    }

    public void method1404(float f) {
        this.field1962 = f;
    }

    public void method1405(ClientPlayerEntity ent) {
        this.x = ent.getX();
        this.y = ent.getY();
        this.z = ent.getZ();
        this.yaw = ent.getYaw();
        this.pitch = ent.getPitch();
        this.onGround = ent.isOnGround();
        ent.setPos(this.field1966, this.field1963, this.field1965);
        ent.setOnGround(this.field1967);
        ent.setYaw(this.field1959);
        ent.setPitch(this.field1962);
    }

    public void method1406(boolean bl) {
        this.field1967 = bl;
    }

    public double method1407() {
        return this.field1965;
    }

    public void method1408(double d) {
        this.field1966 = d;
    }

    public void method1409(double d) {
        this.field1963 = d;
    }

    public float method1410() {
        return this.field1959;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean method1412() {
        return this.field1967;
    }

    public void method1413(double d) {
        this.field1965 = d;
    }

    public float method1414() {
        return this.field1962;
    }

    public static enum State {
        PRE, POST
    }
}
