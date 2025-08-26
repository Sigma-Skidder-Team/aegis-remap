package nikoisntcat.client.modules.impl;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.JumpEvent;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.StrafeEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.Class228;
import nikoisntcat.client.utils.Class231;
import nikoisntcat.client.utils.MovementUtil;
import nikoisntcat.client.utils.player.Class205;

public class SpeedModule extends Module {
    public ModeSetting field1904 = new ModeSetting("Mode", "Bhop", List.of("Bhop", "Strafe", "GroundStrafe", "HypixelLowHop", "SpeedTwo"));
    private boolean field1905;
    private boolean field1906;
    private boolean field1907;
    private boolean field1908;
    private boolean field1909;
    public BooleanSetting field1910;
    private boolean field1911;
    private int field1912;
    public NumberSetting field1913 = new NumberSetting("Speed", 0.0, 0.5, 0.3);
    private double field1914;
    private boolean field1915;
    private float field1916;

    @Override
    public boolean method1222() {
        return super.method1222() || this.field1907;
    }

    @Override
    public void onJump(JumpEvent jumpEvent) {
        if (!this.field1908) {
            jumpEvent.cancel();
        }

        if (this.field1904.getValue().equals("HypixelLowHop")) {
            jumpEvent.field1978 = this.field1916;
        }
    }

    @Override
    public void onReceivePacket(PacketReceiveEvent event) {
    }

    @Override
    public void setState(boolean state) {
        super.setState(state);
    }

    public static BlockState method1377(double x, double y, double z) {
        if (mc.player != null && mc.world != null) {
            BlockPos var6 = mc.player.getBlockPos();
            BlockPos var7 = Class228.method1489((double) var6.getX() + x, (double) var6.getY() + y, (double) var6.getZ() + z);
            return mc.world.getBlockState(var7);
        } else {
            return null;
        }
    }

    private void method1378() {
        double var1 = Math.floor(mc.player.getY() % 1.0 * 10000.0 + 0.5);
        double var3 = Math.sqrt(mc.player.getVelocity().getX() * mc.player.getVelocity().getX() + mc.player.getVelocity().getZ() * mc.player.getVelocity().getZ());
        Class197 var5 = (Class197) AegisClient.moduleManager.field2010.get(Class197.class);
        boolean class197Enabled = var5 == null ? false : var5.isEnabled();
        if (mc.player != null && mc.world != null && !mc.player.isInFluid() && !mc.player.isInLava() && !mc.player.isSpectator()) {
            this.field1916 = mc.player.getYaw();
            Class165 var10000 = (Class165) AegisClient.moduleManager.field2010.get(Class165.class);
            if (Class165.field1607 != null && !class197Enabled) {
                this.field1916 = Class231.method1539().x;
            }

            if (mc.player.onGround) {
                boolean var7 = Math.abs(mc.player.getY() - (double) Math.round(mc.player.getY())) <= 0.03;
                this.field1906 = false;
                if (MovementUtil.isMoving()) {
                    if (!this.field1906) {
                        this.field1909 = false;
                    }

                    this.field1908 = true;
                    if (!this.field1910.getValue() || !class197Enabled) {
                        mc.player.setSprinting(true);
                        mc.player.jump();
                        MovementUtil.strafe((double) MovementUtil.getSpeed(), this.field1916);
                    }

                    this.field1908 = false;
                    if (this.field1911) {
                        this.field1911 = false;
                    }

                    this.field1906 = (!this.field1910.getValue() || !class197Enabled) && !this.field1909 && this.field1914 >= 3.0 && var7;
                }
            }

            if (this.field1905) {
                System.out.println(this.field1912 + " " + var1);
                if (var1 == 2492.0) {
                    mc.player.addVelocity(0.0, -0.18, 0.0);
                }

                return;
            }

            if (this.field1906) {
                if (var1 == 4200.0) {
                    mc.player.setVelocity(mc.player.getVelocity().getX(), 0.39, mc.player.getVelocity().getZ());
                } else if (var1 == 1138.0) {
                    mc.player.addVelocity(0.0, -0.1309, 0.0);
                } else if (var1 == 2022.0) {
                    mc.player.addVelocity(0.0, -0.2, 0.0);
                }

                if (var1 == 7442.0 && Class205.method1387() <= 0.75) {
                    this.field1915 = true;
                    mc.player.addVelocity(0.0, 0.075, 0.0);
                    if (var3 < Class205.method1392() && mc.player.getVelocity().getX() == 0.0 && mc.player.getVelocity().getZ() == 0.0) {
                        MovementUtil.strafe(Class205.method1392() - 0.05, this.field1916);
                    }
                }

                if (this.field1915) {
                    if (this.field1912 == 7 && Class205.method1387() <= 0.75) {
                        MovementUtil.strafe((double) MovementUtil.getSpeed(), this.field1916);
                    }

                    if (this.field1912 == 8 && Class205.method1387() <= 0.75) {
                        MovementUtil.strafe(Class205.method1392(), this.field1916);
                    }
                } else {
                    if (this.field1912 == 2) {
                        if (mc.player != null) {
                            Vec3d var8 = mc.player.getVelocity();
                            mc.player.setVelocity((var8.x * 1.0 + var8.x * 2.0) / 3.0, var8.y, (var8.z * 1.0 + var8.z * 2.0) / 3.0);
                        }
                    }

                    if (this.field1912 == 9 && Class205.method1387() <= 0.8) {
                        if (mc.player != null) {
                            Vec3d var11 = mc.player.getVelocity();
                            mc.player.setVelocity(var11.x, var11.y + 0.075, var11.z);
                        }

                        MovementUtil.strafe((double) MovementUtil.getSpeed(), this.field1916);
                    }

                    if (this.field1912 == 10 && Class205.method1387() <= 0.8) {
                        if (Math.hypot(mc.player.getVelocity().x, mc.player.getVelocity().z) < Class205.method1392()
                                || mc.player.getVelocity().x == 0.0
                                || mc.player.getVelocity().z == 0.0) {
                            MovementUtil.strafe(Class205.method1392(), this.field1916);
                        }

                        MovementUtil.strafe((double) MovementUtil.getSpeed(), this.field1916);
                    }

                    if (method1377(0.0, mc.player.getVelocity().y, 0.0).equals(Blocks.AIR) && this.field1912 == 11) {
                        MovementUtil.strafe(0.31, this.field1916);
                    }
                }
            }
        }
    }

    public SpeedModule() {
        super("Speed", 0, false, Category.MOVE);
        this.field1910 = new BooleanSetting("NoScaffold", true);
        this.field1912 = 0;
        this.field1914 = 0.0;
        this.field1906 = false;
        this.field1909 = false;
        this.field1911 = false;
        this.field1908 = false;
        this.field1915 = false;
        this.field1905 = false;
        this.field1916 = 0.0F;
        this.field1907 = false;
    }

    @Override
    public void onDisable() {
        this.field1907 = true;
    }

    @Override
    public void onMotion(MotionEvent motionEvent) {
        if (mc.player.onGround && this.field1904.getValue().equals("SpeedTwo")) {
            motionEvent.method1409(motionEvent.method1403() + 1.0E-14);
        }
    }

    @Override
    public void onStrafe(StrafeEvent strafeEvent) {
        if (this.field1904.getValue().equals("HypixelLowHop")) {
            strafeEvent.method1400(this.field1916);
        }
    }

    @Override
    public void onPreTick() {
        if (mc.player.onGround) {
            if (this.field1907) {
                this.field1907 = false;
                return;
            }

            if (this.field1912 > 0) {
                this.field1914 = (double) this.field1912;
            }

            this.field1912 = 0;
        } else {
            this.field1912++;
        }

        Class197 var1 = (Class197) AegisClient.moduleManager.field2010.get(Class197.class);
        boolean class197Enabled = var1 == null ? false : var1.isEnabled();
        String var2 = this.field1904.getValue();
        switch (var2) {
            case "Bhop":
                if ((!this.field1910.getValue() || !class197Enabled) && MovementUtil.isMoving()) {
                    if (mc.player.onGround) {
                        this.field1908 = true;
                        mc.player.jump();
                        this.field1908 = false;
                        MovementUtil.strafe((double) ((float) this.field1913.getValue() + MovementUtil.getSpeed()));
                    } else {
                        MovementUtil.strafe((double) ((float) this.field1913.getValue() * 0.1F + MovementUtil.getSpeed()));
                    }
                }
                break;
            case "Strafe":
                if (MovementUtil.isMoving() && (!this.field1910.getValue() || !class197Enabled)) {
                    if (mc.player.onGround) {
                        this.field1908 = true;
                        mc.player.jump();
                        this.field1908 = false;
                    }

                    MovementUtil.strafe();
                }
                break;
            case "GroundStrafe":
                if (mc.player.onGround && MovementUtil.isMoving() && (!this.field1910.getValue() || !class197Enabled)) {
                    this.field1908 = true;
                    mc.player.jump();
                    this.field1908 = false;
                }
                break;
            case "SpeedTwo":
            case "HypixelLowHop":
                this.method1378();
        }
    }
}
