package nikoisntcat.client.modules.impl.world;

import java.awt.Color;
import java.util.Objects;

import net.minecraft.block.AirBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.Render3DEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.Priority;
import nikoisntcat.client.utils.PlayerUtil;
import nikoisntcat.client.utils.Class228;
import nikoisntcat.client.utils.RotationUtil;
import nikoisntcat.client.utils.math.TimerUtil;

public class FuckerModule extends Module {
    public BooleanSetting field1740;
    public BooleanSetting field1741;
    public NumberSetting field1742;
    public BooleanSetting field1743;
    public Vec3d field1744;
    public BooleanSetting field1745;
    public BooleanSetting field1746;
    public Vec3d field1747;
    public boolean field1748;
    private TimerUtil field1749;
    public double field1750;
    public Vec3d field1751;
    public BooleanSetting field1752;
    public BooleanSetting field1753;
    public BooleanSetting field1754;
    public BooleanSetting field1755;
    private BlockPos field1756;
    public BooleanSetting field1757 = new BooleanSetting("Instant", false);
    public NumberSetting field1758;
    private boolean field1759;
    static Object field1760;

    public FuckerModule() {
        super("Fucker", 0, Category.WORLD);
        this.field1754 = new BooleanSetting("instantOnlyBed", false);
        this.field1745 = new BooleanSetting("throughWalls", false);
        this.field1746 = new BooleanSetting("emptySurrounding", false);
        this.field1743 = new BooleanSetting("rotationOnlyBed", false);
        this.field1740 = new BooleanSetting("HypixelRotation", false);
        this.field1755 = new BooleanSetting("rotations", false);
        this.field1752 = new BooleanSetting("whiteListOwnBed", false);
        this.field1741 = new BooleanSetting("civBreak", false);
        this.field1753 = new BooleanSetting("swing", false);
        this.field1758 = new NumberSetting("range", 5.0, 6.0, 1.0);
        this.field1742 = new NumberSetting("delay", 1000.0, 2000.0, 0.0, 5.0);
        this.field1748 = false;
        this.field1759 = false;
        this.field1749 = new TimerUtil();
    }

    @Override
    public void onReceivePacket(PacketReceiveEvent event) {
        if (!PlayerUtil.nullCheck()) {
            if (event.getPacket() instanceof PlayerPositionLookS2CPacket var3 && Math.sqrt(mc.player.squaredDistanceTo(var3.change().position())) > 40.0) {
                this.field1747 = var3.change().position();
            }
        }
    }

    public void method1292(BlockPos blockPos) {
        float var2 = mc.world.getBlockState(blockPos).calcBlockBreakingDelta(mc.player, mc.world, blockPos);
        this.field1750 += (double) var2;
        mc.world.setBlockBreakingInfo(mc.player.getId(), blockPos, (int) (this.field1750 * 10.0 - 1.0));
    }

    @Override
    public void onEnable() {
        this.field1759 = false;
        this.field1749.update();
        this.field1744 = null;
        this.field1747 = new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ());
        this.field1750 = 0.0;
    }

    public void method1293() {
        BlockPos var1 = Class228.method1489(this.field1744.getX(), this.field1744.getY(), this.field1744.getZ());
        if (!Objects.equals(this.field1756, var1)) {
            this.field1750 = 0.0;
            this.field1748 = false;
        }

        Block var2 = mc.world.getBlockState(var1).getBlock();
        float var3 = (float) Math.sqrt(mc.player.squaredDistanceTo((double) var1.getX() + 0.5, (double) var1.getY() + 0.5, (double) var1.getZ() + 0.5));
        if (!this.field1757.getValue() && (!this.field1754.getValue() || !(var2 instanceof BedBlock))) {
            if (this.field1750 <= 0.0 && !this.field1748) {
                if (var3 < 5.0F && (this.field1759 || !this.field1740.getValue())) {
                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, var1, Direction.UP));
                    this.field1756 = var1;
                    this.field1748 = true;
                }
            } else if (this.field1750 > 1.0 && var3 < 5.0F) {
                this.field1749.update();
                this.field1748 = false;
                this.field1744 = null;
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, var1, Direction.UP));
                this.field1759 = false;
                this.field1750 = 0.0;
                Class228.method1490(var1).onBreak(mc.world, var1, mc.world.getBlockState(var1), mc.player);
                if (mc.world.setBlockState(var1, mc.world.getFluidState(var1).getBlockState(), 11)) {
                    Class228.method1490(var1).onBroken(mc.world, var1, mc.world.getBlockState(var1));
                }

                mc.world.setBlockBreakingInfo(mc.player.getId(), var1, -1);
            } else if (this.field1750 <= 1.0) {
                this.method1292(var1);
            }

            if (var3 < 5.0F) {
                if (this.field1753.getValue()) {
                    mc.player.swingHand(Hand.MAIN_HAND);
                } else {
                    mc.getNetworkHandler().sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
                }
            }
        } else {
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, var1, Direction.UP));
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, var1, Direction.UP));
        }
    }

    @Override
    public void onPreTick() {
        if (PlayerUtil.nullCheck() || this.field1744 == null) {
            ;
        }
    }

    @Override
    public void onSetWorld() {
        this.field1748 = false;
    }

    public void method1294() {
        if (!this.field1743.getValue() || Class228.method1485(this.field1744.x, this.field1744.y, this.field1744.z) instanceof BedBlock) {
            RotationUtil.rotate(RotationUtil.method1527(this.field1744, false), 2, Priority.field1513);
            this.field1759 = true;
        }
    }

    @Override
    public void onTick() {
        if (!PlayerUtil.nullCheck() && this.field1749.passed((long) this.field1742.getValue())) {
            this.field1751 = this.field1744;
            Vec3d var1 = this.method1295();
            if (!this.field1741.getValue() || this.field1744 == null || this.field1750 <= 0.0) {
                this.field1744 = var1;
            }

            if (var1 != null) {
                BlockPos var2 = Class228.method1489(var1.x, var1.y, var1.z);
                if (mc.world.getBlockState(var2).getBlock() instanceof BedBlock) {
                    this.field1744 = var1;
                }
            }

            if (this.field1744 == null) {
                this.field1748 = false;
            } else if (Math.sqrt(mc.player.squaredDistanceTo(this.field1744.x, this.field1744.y, this.field1744.z)) > this.field1758.getValue()
                    && !this.field1741.getValue()) {
                this.field1744 = null;
            } else {
                this.method1293();
                if (this.field1755.getValue() && (!this.field1759 || !this.field1740.getValue())) {
                    this.method1294();
                }
            }
        }
    }

    public Vec3d method1295() {
        if (this.field1747 != null
                && mc.player.squaredDistanceTo(this.field1747.getX(), this.field1747.getY(), this.field1747.getZ()) < 1600.0
                && this.field1752.getValue()) {
            return null;
        } else {
            Vec3d var1 = null;

            for (int var2 = -5; var2 <= 5; var2++) {
                for (int var3 = -5; var3 <= 5; var3++) {
                    for (int var4 = -5; var4 <= 5; var4++) {
                        Block var5 = Class228.method1493((double) var2, (double) var3, (double) var4);
                        Vec3d var6 = new Vec3d(mc.player.getX() + (double) var2, mc.player.getY() + (double) var3, mc.player.getZ() + (double) var4);
                        if (var5 instanceof BedBlock) {
                            BlockHitResult var7 = RotationUtil.method1517(RotationUtil.method1527(var6, false), 4.5);
                            if (var7 != null && !(var7.getPos().distanceTo(new Vec3d(mc.player.getX(), mc.player.getY(), mc.player.getZ())) > 4.5)) {
                                if (this.field1745.getValue()) {
                                    if (this.field1746.getValue()) {
                                        Vec3d var18 = var6;
                                        double var9 = Double.MAX_VALUE;
                                        boolean var11 = false;

                                        for (int var12 = -1; var12 <= 1; var12++) {
                                            for (int var13 = 0; var13 <= 1; var13++) {
                                                for (int var14 = -1; var14 <= 1; var14++) {
                                                    if (!var11
                                                            && !(
                                                            mc.player.squaredDistanceTo(var6.getX() + (double) var12, var6.getY() + (double) var13, var6.getZ() + (double) var14)
                                                                    + 4.0
                                                                    > 20.25
                                                    )
                                                            && Math.abs(var12) + Math.abs(var13) + Math.abs(var14) == 1) {
                                                        Block var15 = Class228.method1485(
                                                                var6.getX() + (double) var12, var6.getY() + (double) var13, var6.getZ() + (double) var14
                                                        );
                                                        if (!(var15 instanceof BedBlock)) {
                                                            if (var15 instanceof AirBlock) {
                                                                var11 = true;
                                                            } else {
                                                                double var16 = (double) var15.getHardness();
                                                                if (var16 < var9) {
                                                                    var9 = var16;
                                                                    var18 = var6.add(new Vec3d((double) var12, (double) var13, (double) var14));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if (!var11) {
                                            if (var18.equals(var6)) {
                                                return null;
                                            }

                                            return var18;
                                        }
                                    }

                                    return var6;
                                }

                                BlockPos var8 = var7.getBlockPos();
                                if (var8 == null || var8.equals(Class228.method1486(var6))) {
                                    return var6;
                                }

                                var1 = new Vec3d((double) var8.getX(), (double) var8.getY(), (double) var8.getZ());
                            }
                        }
                    }
                }
            }

            return var1;
        }
    }

    @Override
    public void onDisable() {
        this.field1744 = null;
        this.field1748 = false;
    }

    @Override
    public void onRender3D(Render3DEvent render3DEvent) {
        if (this.field1744 != null) {
            BlockPos var2 = Class228.method1489(this.field1744.getX(), this.field1744.getY(), this.field1744.getZ());
            Box var3 = new Box(var2);
            Vec3i var31 = new Vec3i((int) var3.getLengthX(), (int) var3.getLengthY(), (int) var3.getLengthZ());
            render3DEvent.method1425(Vec3d.of(var31), new Color(255, 0, 0, 75));
            var3 = var3.expand(Math.max(-0.99, Math.min(-0.001, -this.field1750)) * 0.5);
            var31 = new Vec3i((int) var3.getLengthX(), (int) var3.getLengthY(), (int) var3.getLengthZ());
            render3DEvent.method1425(Vec3d.of(var31), new Color(255, 255, 255, 75));
        }
    }
}
