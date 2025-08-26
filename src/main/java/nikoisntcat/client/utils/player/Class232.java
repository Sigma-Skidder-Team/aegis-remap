package nikoisntcat.client.utils.player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import nikoisntcat.client.utils.Class228;
import nikoisntcat.client.utils.Class231;
import nikoisntcat.client.utils.MinecraftUtil;
import org.joml.Vector2f;

public class Class232 extends MinecraftUtil {
    private Direction field2039;
    private boolean field2040 = false;
    private Vector2f field2041;
    private BlockPos field2042;
    private Vec3d field2043;

    public Class232(BlockPos blockPos, Direction direction) {
        this.field2039 = direction;
        this.field2042 = blockPos;
    }

    public void method1544(boolean checkPos, boolean checkFace, Vec3d vec3, Vector2f rotation) {
        this.field2040 = false;
        Vec3d var5 = Class231.method1526(rotation);
        BlockHitResult var6 = mc.world
                .raycast(new RaycastContext(vec3, vec3.add(var5.x * 5.0, var5.y * 5.0, var5.z * 5.0), ShapeType.OUTLINE, FluidHandling.NONE, mc.player));
        if (var6.getBlockPos().equals(this.field2042) || !checkPos && !checkFace) {
            if (var6.getSide() == this.method1553() || !checkFace) {
                this.field2040 = true;
            }
        }
    }

    public static List<BlockPos> method1545(List<BlockPos> posList, Vec3d playerPos, boolean sortMethod, boolean downSort) {
        double var4 = playerPos.x;
        double var6 = playerPos.y;
        double var8 = playerPos.z;
        List<Class278> var10 = new ArrayList<>(posList.size());

        for (BlockPos var12 : posList) {
            double var13 = (double) var12.getX() + 0.5;
            double var15 = (double) var12.getY() + 0.5;
            double var17 = (double) var12.getZ() + 0.5;
            if (sortMethod) {
                float var19 = (float) (var4 - var13);
                float var20 = (float) (var8 - var17);
                float var21 = var19 * var19 + var20 * var20;
                float var22 = (float) (var6 - var15);
                var10.add(new Class278(var12, var21, var22));
            } else {
                float var26 = (float) (var4 - var13);
                float var27 = (float) (var6 - var15);
                float var28 = (float) (var8 - var17);
                float var29 = var26 * var26 + var27 * var27 + var28 * var28;
                var10.add(new Class278(var12, var29, 0.0F));
            }
        }

        var10.sort((a, b) -> {
            int var4x = Float.compare(a.field2174, b.field2174);
            if (var4x != 0) {
                return var4x;
            } else if (downSort && b.field2172 < 3.0F && a.field2172 < 3.0F) {
                return Float.compare(Math.abs(b.field2172), Math.abs(a.field2172));
            } else {
                return sortMethod ? Float.compare(Math.abs(a.field2172), Math.abs(b.field2172)) : 0;
            }
        });
        ListIterator var24 = posList.listIterator();

        for (Class278 var23 : var10) {
            var24.next();
            var24.set(var23.field2173);
        }

        return posList;
    }

    public boolean method1546() {
        return this.field2040;
    }

    public Vector2f method1547() {
        return this.field2041;
    }

    public void method1548(Vector2f rotation) {
        this.field2041 = rotation;
    }

    public static List method1549(BlockPos pos) {
        ArrayList var1 = new ArrayList();
        double var2 = mc.player.getY();
        int var4 = Math.max(2, 0);
        int var5 = pos.getX();
        int var6 = pos.getY();
        int var7 = pos.getZ();

        for (int var8 = -var4; var8 <= 0; var8++) {
            int var9 = var6 + var8;
            if (var2 >= (double) var9) {
                method1552(var1, var5, var9, var7, var4);
            }
        }

        return var1;
    }

    public static Class232 method1550(boolean placeUp, int range, Object... settings) {
        List<Vec3d> var3 = new ArrayList();
        boolean var4 = settings != null && settings.length > 0 && (Boolean) settings[0];
        boolean var5 = settings != null && settings.length > 1 && (Boolean) settings[1];

        for (int var6 = -range; var6 <= range; var6++) {
            for (int var7 = -3; var7 <= 1; var7++) {
                for (int var8 = -range; var8 <= range; var8++) {
                    BlockPos var9 = Class228.method1489(mc.player.getX() + (double) var6, mc.player.getY() + (double) var7, mc.player.getZ() + (double) var8);
                    if (mc.world.getBlockState(var9).getBlock().getDefaultState().isReplaceable()) {
                        boolean var11 = false;

                        for (Direction var15 : Direction.values()) {
                            if (!mc.world.getBlockState(var9.offset(var15)).isReplaceable()) {
                                var11 = true;
                                break;
                            }
                        }

                        if (var11) {
                            var3.add(new Vec3d((double) var9.getX(), (double) var9.getY(), (double) var9.getZ()));
                        }
                    }
                }
            }
        }

        if (var3.isEmpty()) {
            return null;
        } else {
            List<Vec3d> var17 = var3.stream()
                    .filter(
                            vec3 -> mc.world.getBlockState(Class228.method1489(vec3.x, vec3.y, vec3.z)).isReplaceable()
                                    && mc.player.getPos().distanceTo(vec3) <= (double) range
                    )
                    .sorted(Comparator.comparingDouble(vec3 -> {
                        BlockPos var1 = Class228.method1489(vec3.x, vec3.y, vec3.z);
                        Vec3d var2 = new Vec3d((double) var1.getX() + 0.5, (double) var1.getY() + 0.5, (double) var1.getZ() + 0.5);
                        double var3x = mc.player.getX() - var2.x;
                        double var5x = mc.player.getY() - var2.y;
                        double var7x = mc.player.getZ() - var2.z;
                        return Math.sqrt(var3x * var3x + var5x * var5x + var7x * var7x);
                    }))
                    .toList();
            Class232 var18 = null;
            BlockPos var19 = Class228.method1486(mc.player.getPos());

            for (Vec3d var21 : var17) {
                Class232 var16 = method1555(Class228.method1489(var21.x, var21.y, var21.z), true, true, var5);
                if (var16 != null) {
                    if (var18 == null || var18.field2039 != Direction.UP && !Class228.method1488(var18.method1556(), var18.method1553())) {
                        var18 = var16;
                    }

                    BlockPos var22 = var18.field2042;
                    if (mc.player.getBlockPos().getX() == var22.getX() && mc.player.getBlockPos().getZ() == var22.getZ() && !var16.field2042.equals(var18.field2042)
                            || !var18.field2042.equals(var19) && !mc.world.getBlockState(var19).isReplaceable()) {
                        return null;
                    }

                    if (!((double) (var16.field2042.getY() + 1) > mc.player.getY()) && (var16.field2039 != Direction.UP || placeUp || var4 || mc.player.onGround)) {
                        if (var4) {
                            var16.field2039 = Direction.UP;
                        }

                        Vector2f var23 = Class231.method1535(mc.player.getCameraPosVec(1.0F), var16.method1556(), var16.method1553(), null);
                        var23.x = MathHelper.wrapDegrees(var23.x) + (float) (Math.round(mc.player.getYaw() / 360.0F) * 360);
                        BlockHitResult var24 = Class231.method1517(var23, 4.5);
                        if (var24.getSide() == var16.method1553() && var24.getBlockPos().equals(var16.method1556())) {
                            var16.field2041 = var23;
                            var16.field2043 = var24.getPos();
                            return var16;
                        }
                    }
                }
            }

            return null;
        }
    }

    public Vec3d method1551() {
        return this.field2043;
    }

    public Class232(BlockPos blockPos, Direction direction, Vec3d hitVec, Vector2f rotation) {
        this.field2039 = direction;
        this.field2042 = blockPos;
        this.field2043 = hitVec;
        this.field2041 = rotation;
    }

    private static void method1552(List result, int baseX, int currentY, int baseZ, int range) {
        for (int var5 = -range; var5 <= range; var5++) {
            for (int var6 = -range; var6 <= range; var6++) {
                BlockPos var7 = new BlockPos(baseX + var5, currentY, baseZ + var6);
                result.add(var7);
            }
        }
    }

    public Direction method1553() {
        return this.field2039;
    }

    public static boolean method1554(BlockPos blockPos) {
        if (blockPos == null) {
            return false;
        } else {
            BlockState var1 = mc.world.getBlockState(blockPos);
            Block var2 = var1.getBlock();
            return (var1.isSolid() || !var1.isReplaceable()) && !(var2 instanceof SnowBlock);
        }
    }

    public static Class232 method1555(BlockPos searchPos, boolean excludedown, boolean sortMethod, boolean downSort) {
        for (Object var5 : method1545(method1549(searchPos), new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ()), sortMethod, downSort)) {
            BlockPos var = (BlockPos) var5;
            for (Direction var9 : Direction.values()) {
                if ((var9 != Direction.DOWN || !excludedown) && !method1554(var) && method1554(var.offset(var9, -1))) {
                    return new Class232(var.offset(var9, -1), var9);
                }
            }
        }

        return null;
    }

    public BlockPos method1556() {
        return this.field2042;
    }
}
