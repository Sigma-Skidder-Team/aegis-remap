package nikoisntcat.client.events.impl;

import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.events.CancellableEvent;

public class Class211 extends CancellableEvent {
    float field1970;
    float field1971;
    Vec3d field1972;
    Vec3d field1973;
    float field1974;

    public void method1415(Vec3d Vec3d2) {
        this.field1972 = Vec3d2;
    }

    public void method1400(float f) {
        this.field1974 = f;
    }

    public Vec3d method1416() {
        return this.field1972;
    }

    public float method1417() {
        return this.field1970;
    }

    public void method1277() {
        mc.player.setYaw(this.field1971);
    }

    public float method1410() {
        return this.field1974;
    }

    public Vec3d method1418() {
        return this.field1973;
    }

    public Class211(Vec3d vec, float f, float f2, Vec3d vec2) {
        this.field1973 = vec;
        this.field1970 = f;
        this.field1971 = this.field1974 = f2;
        this.field1972 = vec2;
    }

    public void method1419() {
        mc.player.setYaw(this.field1974);
        mc.player.setVelocity(this.field1972);
    }
}
