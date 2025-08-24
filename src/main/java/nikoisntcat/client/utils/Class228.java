package nikoisntcat.client.utils;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.CactusBlock;
import net.minecraft.block.CobwebBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.TntBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;

public class Class228
extends MinecraftUtil {
    public static Block method1485(double x, double y, double z) {
        return Class228.mc.world.getBlockState(Class228.method1489(x, y, z)).getBlock();
    }

    public static BlockPos method1486(Vec3d vec3d) {
        return new BlockPos(MathHelper.floor((double)vec3d.x), MathHelper.floor((double)vec3d.y), MathHelper.floor((double)vec3d.z));
    }

    public static Block method1487(Vec3d vec3d) {
        return Class228.mc.world.getBlockState(Class228.method1486(vec3d)).getBlock();
    }

    public static boolean method1488(BlockPos targetPos, Direction targetFace) {
        BlockPos blockPos = targetPos.add(targetFace.getOffsetX(), targetFace.getOffsetY(), targetFace.getOffsetZ());
        Box box = new Box((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), (double)(blockPos.getX() + 1), (double)blockPos.getY() + (Class228.mc.world.getBlockState(targetPos).getOutlineShape((BlockView)Class228.mc.world, targetPos).isEmpty() ? 1.0 : Class228.mc.world.getBlockState((BlockPos)targetPos).getOutlineShape((BlockView)Class228.mc.world, (BlockPos)targetPos).getBoundingBox().maxY), (double)(blockPos.getZ() + 1));
        return Class228.mc.world.getEntityCollisions(null, box).isEmpty() && !box.intersects(Class228.mc.player.getBoundingBox().offset(Class228.mc.player.getVelocity().getX(), 0.0, Class228.mc.player.getVelocity().getZ())) && !Class228.mc.world.getBlockState(blockPos).isSolid();
    }

    public static BlockPos method1489(double x, double y, double z) {
        return new BlockPos(MathHelper.floor((double)x), MathHelper.floor((double)y), MathHelper.floor((double)z));
    }

    public static Block method1490(BlockPos blockPos) {
        return Class228.mc.world.getBlockState(blockPos) != null ? Class228.mc.world.getBlockState(blockPos).getBlock() : null;
    }

    public static List method1491(double offsetX, double offsetY, double offsetZ) {
        ArrayList<Vec3d> arrayList = new ArrayList<Vec3d>();
        ArrayList arrayList2 = new ArrayList();
        int n = (int)(6.0 + (Math.abs(offsetX) + Math.abs(offsetZ)));
        ArrayList<BlockPos> arrayList3 = new ArrayList<BlockPos>();
        if (!(Class228.method1490(Class228.mc.player.getBlockPos().down()) instanceof AirBlock)) {
            for (Direction direction : Direction.values()) {
                if (!(Class228.method1490(Class228.mc.player.getBlockPos().down().offset(direction)) instanceof AirBlock)) continue;
                arrayList3.add(Class228.mc.player.getBlockPos().down().offset(direction));
            }
            return arrayList3;
        }
        for (int i = -n; i <= n; ++i) {
            for (int j = -n; j <= 0; ++j) {
                for (int k = -n; k <= n; ++k) {
                    int n2;
                    if (MinecraftUtil.mc.world.getBlockState(BlockPos.ofFloored((Position)MinecraftUtil.mc.player.getPos()).add(i, j, k)).getBlock() instanceof AirBlock) continue;
                    for (n2 = -1; n2 <= 1; n2 += 2) {
                        arrayList.add(new Vec3d(MinecraftUtil.mc.player.getPos().x + (double)i + (double)n2, MinecraftUtil.mc.player.getPos().y + (double)j, MinecraftUtil.mc.player.getPos().z + (double)k));
                    }
                    for (n2 = -1; n2 <= 1; n2 += 2) {
                        arrayList.add(new Vec3d(MinecraftUtil.mc.player.getPos().x + (double)i, MinecraftUtil.mc.player.getPos().y + (double)j + (double)n2, MinecraftUtil.mc.player.getPos().z + (double)k));
                    }
                    for (n2 = -1; n2 <= 1; n2 += 2) {
                        arrayList.add(new Vec3d(MinecraftUtil.mc.player.getPos().x + (double)i, MinecraftUtil.mc.player.getPos().y + (double)j, MinecraftUtil.mc.player.getPos().z + (double)k + (double)n2));
                    }
                }
            }
        }
        arrayList.removeIf(vec3 -> {
            BlockPos blockPos = new BlockPos(MathHelper.floor((double)vec3.x), MathHelper.floor((double)vec3.y), MathHelper.floor((double)vec3.z));
            if (BlockPos.ofFloored((Position)Class228.mc.player.getPos()).equals((Object)blockPos)) {
                return true;
            }
            boolean bl = MinecraftUtil.mc.world.getBlockState(blockPos).getBlock() instanceof AirBlock;
            if (!bl) {
                arrayList2.add(new Vec3d((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()));
            }
            if (Math.sqrt(Class228.mc.player.squaredDistanceTo((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5)) > 7.0 || !bl) {
                return true;
            }
            boolean bl2 = false;
            for (Direction direction : Direction.values()) {
                BlockPos blockPos2;
                if (direction == Direction.UP || MinecraftUtil.mc.world.getBlockState(blockPos2 = blockPos.offset(direction)).getBlock() instanceof AirBlock) continue;
                bl2 = true;
            }
            return !bl2;
        });
        arrayList.forEach(vec3d -> arrayList3.add(new BlockPos(MathHelper.floor((double)vec3d.x), MathHelper.floor((double)vec3d.y), MathHelper.floor((double)vec3d.z))));
        return arrayList3;
    }

    public static boolean method1492(Block block) {
        return block instanceof BlockWithEntity || block instanceof CactusBlock || block instanceof CobwebBlock || block instanceof FlowerBlock || block instanceof SnowBlock || block instanceof TntBlock || block instanceof LadderBlock || block instanceof LilyPadBlock;
    }

    public static Block method1493(double offsetX, double offsetY, double offsetZ) {
        return MinecraftUtil.mc.world.getBlockState(new BlockPos((Vec3i)MinecraftUtil.mc.player.getBlockPos()).add(MathHelper.floor((double)offsetX), MathHelper.floor((double)offsetY), MathHelper.floor((double)offsetZ))).getBlock();
    }
}
