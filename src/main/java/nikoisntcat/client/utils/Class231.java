package nikoisntcat.client.utils;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import nikoisntcat.client.events.impl.JumpEvent;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.MoveInputEvent;
import nikoisntcat.client.events.impl.StrafeEvent;
import nikoisntcat.client.modules.impl.ClientSettingsModule;
import org.joml.Vector2f;
import org.joml.Vector2fc;

public class Class231 extends MinecraftUtil {
    private static Vector2f field2032;
    private static Vector2f field2033;
    private static Vec3d field2034;
    private static Class129 field2035;
    public static boolean field2036;
    private static int field2037;
    static Object field2038;

    public static Vec3d method1504(Vec3d target, Vec3d vec, double y) {
        double d = vec.x - target.x;
        double d2 = vec.y - target.y;
        double d3 = vec.z - target.z;
        if (d2 * d2 < (double) 1.0E-7f) {
            return null;
        }
        double d4 = (y - target.y) / d2;
        return d4 >= 0.0 && d4 <= 1.0 ? new Vec3d(target.x + d * d4, target.y + d2 * d4, target.z + d3 * d4) : null;
    }

    private static boolean method1505(Box box, Vec3d vec) {
        return vec == null ? false : vec.x >= box.minX && vec.x <= box.maxX && vec.y >= box.minY && vec.y <= box.maxY;
    }

    public static Vector2f method1506(Vector2f rotation, Vector2f previousRotation) {
        float f = (float) ((Double) Class231.mc.options.getMouseSensitivity().getValue() * (1.0 + Math.random() / 1.0E7) * (double) 0.6f + (double) 0.2f);
        double d = (double) (f * f * f * 8.0f) * 0.15;
        float f2 = previousRotation.x + (float) ((double) Math.round((double) (rotation.x - previousRotation.x) / d) * d);
        float f3 = previousRotation.y + (float) ((double) Math.round((double) (rotation.y - previousRotation.y) / d) * d);
        return new Vector2f(f2, MathHelper.clamp((float) f3, (float) -90.0f, (float) 90.0f));
    }

    public void onJump(JumpEvent jumpEvent) {
        if (ClientSettingsModule.strafeFixMode.getValue().equals("Normal") && field2032 != null) {
            jumpEvent.field1978 = Class231.field2032.x;
        }
    }

    private static boolean method1507(Box box, Vec3d vec) {
        return vec == null ? false : vec.y >= box.minY && vec.y <= box.maxY && vec.z >= box.minZ && vec.z <= box.maxZ;
    }

    public static Vector2f method1508() {
        return field2033;
    }

    public static double method1509(LivingEntity living) {
        Vec3d vec3d = Class231.mc.player.getCameraPosVec(1.0f);
        return vec3d.distanceTo(Class231.method1528(vec3d, living.getBoundingBox()));
    }

    public static Vec3d method1510(Vec3d target, Vec3d vec, double x) {
        double d = vec.x - target.x;
        double d2 = vec.y - target.y;
        double d3 = vec.z - target.z;
        if (d * d < (double) 1.0E-7f) {
            return null;
        }
        double d4 = (x - target.x) / d;
        return d4 >= 0.0 && d4 <= 1.0 ? new Vec3d(target.x + d * d4, target.y + d2 * d4, target.z + d3 * d4) : null;
    }

    public static Vector2f method1511(BlockPos pos, int predictTicks) {
        Vector2f vector2f;
        float f = Class231.mc.player.getYaw() - 180.0f;
        int n = 0;
        while (true) {
            vector2f = n == 0 ? new Vector2f(Class231.mc.player.lastYaw, Class231.mc.player.lastPitch) : Class231.method1532(new Class207().method1396((int) predictTicks, (float) f, (boolean) true).field2177.add(0.0, (double) Class231.mc.player.getStandingEyeHeight(), 0.0), pos, null);
            Vec3d vec3d = new Class207().method1396((int) predictTicks, (float) vector2f.x, (boolean) true).field2177.add(0.0, (double) Class231.mc.player.getStandingEyeHeight(), 0.0);
            Vec3d vec3d2 = Class231.method1526(vector2f);
            Vec3d vec3d3 = vec3d.add(vec3d2.x * 4.5, vec3d2.y * 4.5, vec3d2.z * 4.5);
            BlockHitResult blockHitResult = Class231.mc.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, (Entity) Class231.mc.player));
            Vec3d vec3d4 = Class231.mc.player.getCameraPosVec(1.0f);
            Vec3d vec3d5 = Class231.method1526(vector2f);
            Vec3d vec3d6 = vec3d4.add(vec3d5.x * 4.5, vec3d5.y * 4.5, vec3d5.z * 4.5);
            BlockHitResult blockHitResult2 = Class231.mc.world.raycast(new RaycastContext(vec3d4, vec3d6, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, (Entity) Class231.mc.player));
            if (!blockHitResult.getBlockPos().equals((Object) pos) || !blockHitResult2.getBlockPos().equals((Object) pos)) {
                f = vector2f.x;
                for (float f2 = 40.0f; f2 < 90.0f; f2 += 0.01f) {
                    vec3d5 = vec3d2 = Class231.method1526(new Vector2f(vector2f.x, f2));
                    vec3d3 = vec3d.add(vec3d2.x * 4.5, vec3d2.y * 4.5, vec3d2.z * 4.5);
                    blockHitResult = Class231.mc.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, (Entity) Class231.mc.player));
                    vec3d6 = vec3d4.add(vec3d5.x * 4.5, vec3d5.y * 4.5, vec3d5.z * 4.5);
                    blockHitResult2 = Class231.mc.world.raycast(new RaycastContext(vec3d4, vec3d6, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, (Entity) Class231.mc.player));
                    if (!blockHitResult.getBlockPos().equals((Object) pos) || !blockHitResult2.getBlockPos().equals((Object) pos))
                        continue;
                    vector2f.y = f2;
                    return vector2f;
                }
                if (n > 10) {
                    break;
                }
            } else {
                return vector2f;
            }
            ++n;
        }

        System.out.println("CNM");
        return vector2f;
    }

    static {
        field2037 = 0;
        field2032 = null;
        field2033 = null;
        field2034 = null;
        field2036 = true;
    }

    public static Vector2f method1512(BlockPos pos, Direction direction) {
        Vector2f vector2f;
        float f = Class231.mc.player.getYaw() - 180.0f;
        int n = 0;
        while (true) {
            vector2f = Class231.method1535(new Class207().method1396((int) 1, (float) f, (boolean) true).field2177.add(0.0, (double) Class231.mc.player.getStandingEyeHeight(), 0.0), pos, direction, null);
            Vec3d vec3d = new Class207().method1396((int) 1, (float) vector2f.x, (boolean) true).field2177.add(0.0, (double) Class231.mc.player.getStandingEyeHeight(), 0.0);
            Vec3d vec3d2 = Class231.method1526(vector2f);
            Vec3d vec3d3 = vec3d.add(vec3d2.x * 4.5, vec3d2.y * 4.5, vec3d2.z * 4.5);
            if (Class231.mc.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, (Entity) Class231.mc.player)).getSide() != direction) {
                f = vector2f.x;
                for (float f2 = 0.0f; f2 < 90.0f; f2 += 0.01f) {
                    vec3d2 = Class231.method1526(new Vector2f(vector2f.x, f2));
                    vec3d3 = vec3d.add(vec3d2.x * 4.5, vec3d2.y * 4.5, vec3d2.z * 4.5);
                    if (Class231.mc.world.raycast(new RaycastContext(vec3d, vec3d3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, (Entity) Class231.mc.player)).getSide() != direction)
                        continue;
                    return new Vector2f(vector2f.x, f2);
                }
                if (n > 10) {
                    break;
                }
            } else {
                return vector2f;
            }
            ++n;
        }
        return vector2f;
    }

    public static boolean method1513(Vec3d vec3) {
        Vec3d vec3d = Class231.mc.player.getCameraPosVec(1.0f);
        return Class231.mc.world.raycast(new RaycastContext(vec3d, vec3, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, (Entity) Class231.mc.player)) == null;
    }

    public static Vec3d method1514(double range) {
        Vector2f vector2f = Class231.method1539();
        Vec3d vec3d = Class231.mc.player.getCameraPosVec(1.0f);
        Vec3d vec3d2 = Class231.method1526(vector2f);
        return vec3d.add(vec3d2.x * range, vec3d2.y * range, vec3d2.z * range);
    }

    public static float method1515(float yaw, BlockPos pos, Direction direction, Vec3d eyesPos) {
        float f = 75.0f;
        float f2 = 55.0f;
        while ((double) f2 < 79.5) {
            BlockHitResult blockHitResult = Class231.method1517(new Vector2f(yaw, f2), 4.5);
            if (blockHitResult.getBlockPos().equals((Object) pos)) {
                if (blockHitResult.getSide() == direction) {
                    return f2;
                }
                f = f2;
            }
            f2 = (float) ((double) f2 + 0.01);
        }
        return f;
    }

    public static float method1516(float a, float b) {
        float f;
        float f2 = MathHelper.wrapDegrees((float) a);
        float f3 = f2 - (f = MathHelper.wrapDegrees((float) b));
        if (Math.abs(f3) > 180.0f) {
            f3 = f3 > 0.0f ? (f3 -= 360.0f) : (f3 += 360.0f);
        }
        return f3;
    }

    public static BlockHitResult method1517(Vector2f toRotation, double reach) {
        Vec3d vec3d = Class231.mc.player.getCameraPosVec(1.0f);
        Vec3d vec3d2 = Class231.method1526(toRotation);
        return Class231.mc.world.raycast(new RaycastContext(vec3d, vec3d.add(new Vec3d(vec3d2.x * reach, vec3d2.y * reach, vec3d2.z * reach)), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, (Entity) Class231.mc.player));
    }

    public static BlockHitResult method1518(Box targetBox, Vec3d eyesPos, Vec3d hitVec) {
        Vec3d vec3d = Class231.method1510(eyesPos, hitVec, targetBox.minX);
        Vec3d vec3d2 = Class231.method1510(eyesPos, hitVec, targetBox.maxX);
        Vec3d vec3d3 = Class231.method1504(eyesPos, hitVec, targetBox.minY);
        Vec3d vec3d4 = Class231.method1504(eyesPos, hitVec, targetBox.maxY);
        Vec3d vec3d5 = Class231.method1521(eyesPos, hitVec, targetBox.minZ);
        Vec3d vec3d6 = Class231.method1521(eyesPos, hitVec, targetBox.maxZ);
        if (!Class231.method1507(targetBox, vec3d)) {
            vec3d = null;
        }
        if (!Class231.method1507(targetBox, vec3d2)) {
            vec3d2 = null;
        }
        if (!Class231.method1533(targetBox, vec3d3)) {
            vec3d3 = null;
        }
        if (!Class231.method1533(targetBox, vec3d4)) {
            vec3d4 = null;
        }
        if (!Class231.method1505(targetBox, vec3d5)) {
            vec3d5 = null;
        }
        if (!Class231.method1505(targetBox, vec3d6)) {
            vec3d6 = null;
        }
        Vec3d vec3d7 = null;
        if (vec3d != null) {
            vec3d7 = vec3d;
        }
        if (vec3d2 != null && (vec3d7 == null || eyesPos.squaredDistanceTo(vec3d2) < eyesPos.squaredDistanceTo(vec3d7))) {
            vec3d7 = vec3d2;
        }
        if (vec3d3 != null && (vec3d7 == null || eyesPos.squaredDistanceTo(vec3d3) < eyesPos.squaredDistanceTo(vec3d7))) {
            vec3d7 = vec3d3;
        }
        if (vec3d4 != null && (vec3d7 == null || eyesPos.squaredDistanceTo(vec3d4) < eyesPos.squaredDistanceTo(vec3d7))) {
            vec3d7 = vec3d4;
        }
        if (vec3d5 != null && (vec3d7 == null || eyesPos.squaredDistanceTo(vec3d5) < eyesPos.squaredDistanceTo(vec3d7))) {
            vec3d7 = vec3d5;
        }
        if (vec3d6 != null && (vec3d7 == null || eyesPos.squaredDistanceTo(vec3d6) < eyesPos.squaredDistanceTo(vec3d7))) {
            vec3d7 = vec3d6;
        }
        if (vec3d7 == null) {
            return null;
        }
        Direction direction = vec3d7 == vec3d ? Direction.WEST : (vec3d7 == vec3d2 ? Direction.EAST : (vec3d7 == vec3d3 ? Direction.DOWN : (vec3d7 == vec3d4 ? Direction.UP : (vec3d7 == vec3d5 ? Direction.NORTH : Direction.SOUTH))));
        return new BlockHitResult(vec3d7, direction, BlockPos.ORIGIN, false);
    }

    public void method1519(MotionEvent motionEvent) {
        if (motionEvent.method1399() == MotionEvent.Class123.PRE) {
            if (field2032 != null) {
                motionEvent.method1400(Class231.mc.player.lastYaw + Class231.method1516(Class231.field2032.x, Class231.mc.player.lastYaw));
                Class231.field2032.x = motionEvent.method1410();
                motionEvent.method1404(Class231.field2032.y);
                --field2037;
            }
        } else {
            if (field2037 <= 0) {
                Class231.method1277();
            }
            Class231.mc.player.renderYaw = Class231.mc.player.getYaw() + Class231.method1516(Class231.mc.player.renderYaw, Class231.mc.player.getYaw());
            Class231.mc.player.prevYaw = Class231.mc.player.getYaw() + Class231.method1516(Class231.mc.player.prevYaw, Class231.mc.player.getYaw());
            Class231.mc.player.lastRenderYaw = Class231.mc.player.getYaw() + Class231.method1516(Class231.mc.player.lastRenderYaw, Class231.mc.player.getYaw());
            field2033 = new Vector2f(motionEvent.method1410(), motionEvent.method1414());
            Vec3d vec3d = Class231.mc.player.getCameraPosVec(1.0f);
            Vec3d vec3d2 = Class231.method1526(field2033);
            field2034 = vec3d.add(vec3d2.x * 5.0, vec3d2.y * 5.0, vec3d2.z * 5.0);
        }
    }

    public static void method1277() {
        if (field2032 != null) {
            Class231.mc.player.setYaw(Class231.field2032.x + Class231.method1516(Class231.mc.player.getYaw(), Class231.field2032.x));
        }
        field2032 = null;
        field2037 = 0;
        field2035 = Class129.field1509;
    }

    public static Vector2f method1520() {
        return field2032;
    }

    public static Vec3d method1521(Vec3d target, Vec3d vec, double z) {
        double d = vec.x - target.x;
        double d2 = vec.y - target.y;
        double d3 = vec.z - target.z;
        if (d3 * d3 < (double) 1.0E-7f) {
            return null;
        }
        double d4 = (z - target.z) / d3;
        return d4 >= 0.0 && d4 <= 1.0 ? new Vec3d(target.x + d * d4, target.y + d2 * d4, target.z + d3 * d4) : null;
    }

    public static Vec3d method1522() {
        return field2034;
    }

    public static double method1523(Vector2f a, Vector2f b) {
        float f = Math.abs(MathHelper.wrapDegrees((float) a.x) - MathHelper.wrapDegrees((float) b.x));
        if (f > 180.0f) {
            f = 360.0f - f;
        }
        return f;
    }

    public static void method1524(Vector2f targetRotation, int rotationTicks, Class129 priority) {
        if (field2035 != null && priority.ordinal() > field2035.ordinal()) {
            return;
        }
        if (field2033 == null) {
            field2033 = new Vector2f(Class231.mc.player.getYaw(), Class231.mc.player.getPitch());
        }
        targetRotation.set(MathHelper.wrapDegrees((float) targetRotation.x) - MathHelper.wrapDegrees((float) Class231.field2033.x) + Class231.field2033.x, targetRotation.y);
        targetRotation.set((Vector2fc) Class231.method1506(targetRotation, field2033));
        field2032 = targetRotation;
        field2037 = rotationTicks;
        field2035 = priority;
    }

    public void onStrafe(StrafeEvent strafeEvent) {
        if (field2032 != null && ClientSettingsModule.strafeFixMode.getValue().equals("Normal")) {
            strafeEvent.method1400(Class231.field2032.x);
        }
    }

    public static Vec3d method1526(Vector2f rotation) {
        float f = (float) Math.cos(-rotation.x * ((float) Math.PI / 180) - (float) Math.PI);
        float f2 = (float) Math.sin(-rotation.x * ((float) Math.PI / 180) - (float) Math.PI);
        float f3 = (float) (-Math.cos(-rotation.y * ((float) Math.PI / 180)));
        float f4 = (float) Math.sin(-rotation.y * ((float) Math.PI / 180));
        return new Vec3d((double) (f2 * f3), (double) f4, (double) (f * f3));
    }

    public void onMoveInput(MoveInputEvent moveInputEvent) {
        if (ClientSettingsModule.strafeFixMode.getValue().equals("Normal") && field2032 != null && field2036) {
            MovementUtil.method1468(moveInputEvent, Class231.field2032.x);
        }
    }

    public static Vector2f method1527(Vec3d vec, boolean predict) {
        Vec3d vec3d = Class231.mc.player.getCameraPosVec(1.0f);
        if (predict) {
            vec3d.add(Class231.mc.player.getVelocity());
        }
        double d = vec.x - vec3d.x;
        double d2 = vec.y - vec3d.y;
        double d3 = vec.z - vec3d.z;
        return new Vector2f(MathHelper.wrapDegrees((float) ((float) Math.toDegrees(Math.atan2(d3, d)) - 90.0f)), MathHelper.wrapDegrees((float) ((float) (-Math.toDegrees(Math.atan2(d2, Math.sqrt(d * d + d3 * d3)))))));
    }

    public static Vec3d method1528(Vec3d eye, Box box) {
        double[] dArray = new double[]{eye.x, eye.y, eye.z};
        double[] dArray2 = new double[]{box.minX, box.minY, box.minZ};
        double[] dArray3 = new double[]{box.maxX, box.maxY, box.maxZ};
        for (int i = 0; i <= 2; ++i) {
            if (dArray[i] > dArray3[i]) {
                dArray[i] = dArray3[i];
                continue;
            }
            if (!(dArray[i] < dArray2[i])) continue;
            dArray[i] = dArray2[i];
        }
        return new Vec3d(dArray[0], dArray[1], dArray[2]);
    }

    public static Vector2f method1529(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double d = (y2 - y1) / (x2 - x1);
        double d2 = (y4 - y3) / (x4 - x3);
        if (d == d2) {
            return null;
        }
        double d3 = y1 - d * x1;
        double d4 = y3 - d2 * x3;
        double d5 = (d4 - d3) / (d - d2);
        double d6 = d * d5 + d3;
        return new Vector2f((float) d5, (float) d6);
    }

    public static float method1530() {
        Vector2f vector2f = new Vector2f(Class231.mc.player.getYaw(), Class231.mc.player.getPitch());
        if (Class231.method1523(Class231.method1539(), vector2f) > 135.0 && Class231.mc.player.onGround) {
            double d;
            Vector2f vector2f2 = new Vector2f(Class231.method1539().x + 145.0f, Class231.mc.player.getPitch());
            Vector2f vector2f3 = new Vector2f(Class231.method1539().x - 145.0f, Class231.mc.player.getPitch());
            double d2 = Class231.method1523(vector2f2, new Vector2f(Class231.mc.player.getYaw(), Class231.mc.player.getPitch()));
            if (d2 > (d = Class231.method1523(vector2f3, new Vector2f(Class231.mc.player.getYaw(), Class231.mc.player.getPitch())))) {
                return vector2f3.x;
            }
            return vector2f2.x;
        }
        return Class231.mc.player.getYaw();
    }

    public static Entity method1531(double range) {
        Entity entity2 = mc.getCameraEntity();
        float f = Class231.method1539().x;
        float f2 = Class231.method1539().y;
        if (entity2 != null && Class231.mc.world != null) {
            double d = range;
            Vec3d vec3d = entity2.getCameraPosVec(1.0f);
            float f3 = (float) Math.cos((double) (-f * ((float) Math.PI / 180)) - Math.PI);
            float f4 = (float) Math.sin((double) (-f * ((float) Math.PI / 180)) - Math.PI);
            float f5 = (float) (-Math.cos(-f2 * ((float) Math.PI / 180)));
            float f6 = (float) Math.sin(-f2 * ((float) Math.PI / 180));
            Vec3d vec3d2 = new Vec3d((double) (f4 * f5), (double) f6, (double) (f3 * f5));
            Vec3d vec3d3 = vec3d.add(vec3d2.x * d, vec3d2.y * d, vec3d2.z * d);
            List<Entity> list = Class231.mc.world.getOtherEntities(entity2, entity2.getBoundingBox().expand(vec3d2.x * d, vec3d2.y * d, vec3d2.z * d).expand(1.0, 1.0, 1.0), entity -> entity != null && (entity.getId() != Class231.mc.player.getId() || !entity.isSpectator()) && entity.canHit());
            Entity entity3 = null;
            for (Entity entity4 : list) {
                double d2;
                Box box = entity4.getBoundingBox().expand(0.0, 0.0, 0.0);
                BlockHitResult blockHitResult = Class231.method1518(box, vec3d, vec3d3);
                if (box.contains(vec3d)) {
                    if (!(d >= 0.0)) continue;
                    entity3 = entity4;
                    d = 0.0;
                    continue;
                }
                if (blockHitResult == null || !((d2 = vec3d.distanceTo(blockHitResult.getPos())) < d) && d != 0.0)
                    continue;
                if (entity4 == entity2.getVehicle() && !entity2.canHit()) {
                    if (d != 0.0) continue;
                    entity3 = entity4;
                    continue;
                }
                entity3 = entity4;
                d = d2;
            }
            return entity3;
        }
        return null;
    }

    public static Vector2f method1532(Vec3d eyesPos, BlockPos targetPos, Vec3d helpVector) {
        VoxelShape voxelShape = Class231.mc.world.getBlockState(targetPos).getSidesShape((BlockView) Class231.mc.world, targetPos);
        Box box = voxelShape.isEmpty() ? new Box(0.0, 0.0, 0.0, 1.0, 1.0, 1.0) : voxelShape.getBoundingBox();
        double d = box.maxY - box.minY;
        double d2 = box.maxX - box.minX;
        double d3 = box.maxZ - box.minZ;
        Vec3d vec3d = new Vec3d((double) targetPos.getX() + box.minX, (double) targetPos.getY() + box.minY, (double) targetPos.getZ() + box.minZ).add(d2 / 2.0, d / 2.0, d3 / 2.0);
        Vec3i vec3i = new Vec3i(0, 0, 0);
        Vec3d vec3d2 = new Vec3d((double) vec3i.getX() * (d2 / 2.0), (double) vec3i.getY() * (d / 2.0), (double) vec3i.getZ() * (d3 / 2.0));
        vec3d = vec3d.add(vec3d2);
        double d4 = 0.0;
        double d5 = 0.0;
        double d6 = 0.0;
        if (helpVector != null) {
            if (vec3d2.getX() == 0.0) {
                d4 = 0.0 + Math.min(-d2 / 2.0 * 0.4, Math.max(d2 / 2.0 * 0.4, helpVector.getX() - vec3d.getX()));
            }
            if (vec3d2.getY() == 0.0) {
                d6 = 0.0 + Math.min(-d / 2.0 * 0.4, Math.max(d / 2.0 * 0.4, helpVector.getY() - vec3d.getY()));
            }
            if (vec3d2.getZ() == 0.0) {
                d5 = 0.0 + Math.min(-d3 / 2.0 * 0.4, Math.max(d3 / 2.0 * 0.4, helpVector.getZ() - vec3d.getZ()));
            }
        } else {
            Random random = new Random();
            if (vec3d2.getX() == 0.0) {
                d4 = 0.0 + Math.min(-d2 / 2.0 * 0.4, Math.max(d2 / 2.0 * 0.4, random.nextDouble() * 0.8 - 0.4));
            }
            if (vec3d2.getY() == 0.0) {
                d6 = 0.0 + Math.min(-d / 2.0 * 0.4, Math.max(d / 2.0 * 0.4, random.nextDouble() * 0.8 - 0.4));
            }
            if (vec3d2.getZ() == 0.0) {
                d5 = 0.0 + Math.min(-d3 / 2.0 * 0.4, Math.max(d3 / 2.0 * 0.4, random.nextDouble() * 0.8 - 0.4));
            }
        }
        return Class231.method1536(vec3d.add(d4, d6, d5), eyesPos, false);
    }

    private static boolean method1533(Box box, Vec3d vec) {
        return vec == null ? false : vec.x >= box.minX && vec.x <= box.maxX && vec.z >= box.minZ && vec.z <= box.maxZ;
    }

    public static void method1534(Vector2f targetRotation, int rotationTicks, Class129 priority, float yawSpeed, float pitchSpeed) {
        float f = Class231.method1516(targetRotation.x, Class231.field2033.x);
        float f2 = targetRotation.y - Class231.field2033.y;
        if (Math.abs(f) > yawSpeed) {
            targetRotation.x = Class231.field2033.x + (f > 0.0f ? yawSpeed : -yawSpeed);
        }
        if (Math.abs(f2) > pitchSpeed) {
            targetRotation.y = Class231.field2033.y + (f2 > 0.0f ? pitchSpeed : -pitchSpeed);
        }
        Class231.method1524(targetRotation, rotationTicks, priority);
    }

    public static Vector2f method1535(Vec3d eyesPos, BlockPos targetPos, Direction targetFace, Vec3d helpVector) {
        VoxelShape voxelShape = Class231.mc.world.getBlockState(targetPos).getSidesShape((BlockView) Class231.mc.world, targetPos);
        Box box = voxelShape.isEmpty() ? new Box(0.0, 0.0, 0.0, 1.0, 1.0, 1.0) : voxelShape.getBoundingBox();
        double d = box.maxY - box.minY;
        double d2 = box.maxX - box.minX;
        double d3 = box.maxZ - box.minZ;
        Vec3d vec3d = new Vec3d((double) targetPos.getX() + box.minX, (double) targetPos.getY() + box.minY, (double) targetPos.getZ() + box.minZ).add(d2 / 2.0, d / 2.0, d3 / 2.0);
        Vec3i vec3i = targetFace.getVector();
        Vec3d vec3d2 = new Vec3d((double) vec3i.getX() * (d2 / 2.0), (double) vec3i.getY() * (d / 2.0), (double) vec3i.getZ() * (d3 / 2.0));
        vec3d = vec3d.add(vec3d2);
        double d4 = 0.0;
        double d5 = 0.0;
        double d6 = 0.0;
        if (helpVector != null) {
            if (vec3d2.getX() == 0.0) {
                d4 = 0.0 + Math.min(-d2 / 2.0 * 0.4, Math.max(d2 / 2.0 * 0.4, helpVector.getX() - vec3d.getX()));
            }
            if (vec3d2.getY() == 0.0) {
                d6 = 0.0 + Math.min(-d / 2.0 * 0.4, Math.max(d / 2.0 * 0.4, helpVector.getY() - vec3d.getY()));
            }
            if (vec3d2.getZ() == 0.0) {
                d5 = 0.0 + Math.min(-d3 / 2.0 * 0.4, Math.max(d3 / 2.0 * 0.4, helpVector.getZ() - vec3d.getZ()));
            }
        }
        return Class231.method1536(vec3d.add(d4, d6, d5), eyesPos, false);
    }

    public static Vector2f method1536(Vec3d vec, Vec3d eyesPos, boolean predict) {
        if (predict) {
            eyesPos.add(Class231.mc.player.getVelocity());
        }
        double d = vec.x - eyesPos.x;
        double d2 = vec.y - eyesPos.y;
        double d3 = vec.z - eyesPos.z;
        return new Vector2f(MathHelper.wrapDegrees((float) ((float) Math.toDegrees(Math.atan2(d3, d)) - 90.0f)), MathHelper.wrapDegrees((float) ((float) (-Math.toDegrees(Math.atan2(d2, Math.sqrt(d * d + d3 * d3)))))));
    }

    public static Vector2f method1537(Box bb, boolean predict, boolean throughWalls, float distance) {
        Vec3d vec3d;
        Vec3d vec3d2 = Class231.mc.player.getCameraPosVec(1.0f);
        if (vec3d2.distanceTo(vec3d = Class231.method1528(vec3d2, bb)) <= (double) distance && (throughWalls || Class231.method1513(vec3d))) {
            return new Vector2f((Vector2fc) Class231.method1527(vec3d, predict));
        }
        return null;
    }

    public static BlockHitResult method1538(float reach) {
        return Class231.method1517(Class231.method1539(), reach);
    }

    public static Vector2f method1539() {
        return field2032 != null ? field2032 : new Vector2f(Class231.mc.player.getYaw(), Class231.mc.player.getPitch());
    }
}
