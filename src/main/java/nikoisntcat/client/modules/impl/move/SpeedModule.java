package nikoisntcat.client.modules.impl.move;

import java.util.List;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.JumpEvent;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.StrafeEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.modules.impl.Class165;
import nikoisntcat.client.modules.impl.Class197;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.Class228;
import nikoisntcat.client.utils.RotationUtil;
import nikoisntcat.client.utils.MovementUtil;
import nikoisntcat.client.utils.player.Class205;

public class SpeedModule extends Module {

    public ModeSetting mode = new ModeSetting("Mode", "Bhop", List.of("Bhop", "Strafe", "GroundStrafe", "HypixelLowHop", "SpeedTwo"));
    public NumberSetting speedValue = new NumberSetting("Speed", 0.0, 0.5, 0.3);
    public BooleanSetting noScaffold = new BooleanSetting("NoScaffold", true);

    private boolean debugVelocity;            // field1905
    private boolean groundBoostReady;         // field1906
    private boolean speedTwoInitFlag;         // field1907
    private boolean jumpingThisTick;          // field1908
    private boolean lastTickJumped;           // field1909
    private boolean jumpCooldown;             // field1911
    private boolean velocityBoosted;          // field1915

    private int airTicks;                     // field1912
    private double lastAirTicks;              // field1914
    private float yawForStrafe;               // field1916

    public SpeedModule() {
        super("Speed", 0, false, Category.MOVE);
        this.airTicks = 0;
        this.lastAirTicks = 0.0;
        this.groundBoostReady = false;
        this.lastTickJumped = false;
        this.jumpCooldown = false;
        this.jumpingThisTick = false;
        this.velocityBoosted = false;
        this.debugVelocity = false;
        this.yawForStrafe = 0.0F;
        this.speedTwoInitFlag = false;
    }

    @Override
    public boolean method1222() {
        return super.method1222() || this.speedTwoInitFlag;
    }

    @Override
    public void onJump(JumpEvent event) {
        if (!this.jumpingThisTick) {
            event.cancel();
        }

        if (this.mode.getValue().equals("HypixelLowHop")) {
            event.field1978 = this.yawForStrafe;
        }
    }

    @Override
    public void onReceivePacket(PacketReceiveEvent event) {}

    @Override
    public void setState(boolean state) {
        super.setState(state);
    }

    /** Returns the block at a relative offset from the player */
    public static BlockState getBlockStateAtOffset(double x, double y, double z) {
        if (mc.player != null && mc.world != null) {
            BlockPos playerPos = mc.player.getBlockPos();
            BlockPos targetPos = Class228.method1489(playerPos.getX() + x, playerPos.getY() + y, playerPos.getZ() + z);
            return mc.world.getBlockState(targetPos);
        } else {
            return null;
        }
    }

    /** Handles HypixelLowHop / SpeedTwo mode velocity and jump adjustments */
    private void handleLowHop() {
        double yOffset = Math.floor(mc.player.getY() % 1.0 * 10000.0 + 0.5);
        double horizontalSpeed = Math.sqrt(mc.player.getVelocity().getX() * mc.player.getVelocity().getX()
                + mc.player.getVelocity().getZ() * mc.player.getVelocity().getZ());

        boolean class197Enabled = false;
        Class197 var197 = (Class197) AegisClient.moduleManager.field2010.get(Class197.class);
        if (var197 != null) class197Enabled = var197.isEnabled();

        if (mc.player != null && mc.world != null && !mc.player.isInFluid() && !mc.player.isInLava() && !mc.player.isSpectator()) {
            this.yawForStrafe = mc.player.getYaw();
            Class165 var165 = (Class165) AegisClient.moduleManager.field2010.get(Class165.class);
            if (Class165.field1607 != null && !class197Enabled) {
                this.yawForStrafe = RotationUtil.method1539().x;
            }

            // On-ground jump / strafe logic
            if (mc.player.onGround) {
                boolean groundedPrecision = Math.abs(mc.player.getY() - Math.round(mc.player.getY())) <= 0.03;
                this.groundBoostReady = false;

                if (MovementUtil.isMoving()) {
                    if (!this.groundBoostReady) this.lastTickJumped = false;
                    this.jumpingThisTick = true;

                    if (!this.noScaffold.getValue() || !class197Enabled) {
                        mc.player.setSprinting(true);
                        mc.player.jump();
                        MovementUtil.strafe(MovementUtil.getSpeed(), this.yawForStrafe);
                    }

                    this.jumpingThisTick = false;
                    if (this.jumpCooldown) this.jumpCooldown = false;

                    this.groundBoostReady = (!this.noScaffold.getValue() || !class197Enabled) && !this.lastTickJumped
                            && this.lastAirTicks >= 3.0 && groundedPrecision;
                }
            }

            if (this.debugVelocity) {
                System.out.println(this.airTicks + " " + yOffset);
                if (yOffset == 2492.0) {
                    mc.player.addVelocity(0.0, -0.18, 0.0);
                }
                return;
            }

            if (this.groundBoostReady) {
                if (yOffset == 4200.0) mc.player.setVelocity(mc.player.getVelocity().x, 0.39, mc.player.getVelocity().z);
                else if (yOffset == 1138.0) mc.player.addVelocity(0.0, -0.1309, 0.0);
                else if (yOffset == 2022.0) mc.player.addVelocity(0.0, -0.2, 0.0);

                if (yOffset == 7442.0 && Class205.method1387() <= 0.75) {
                    this.velocityBoosted = true;
                    mc.player.addVelocity(0.0, 0.075, 0.0);
                    if (horizontalSpeed < Class205.method1392() && mc.player.getVelocity().x == 0.0 && mc.player.getVelocity().z == 0.0) {
                        MovementUtil.strafe(Class205.method1392() - 0.05, this.yawForStrafe);
                    }
                }

                if (this.velocityBoosted) {
                    if (this.airTicks == 7 && Class205.method1387() <= 0.75) MovementUtil.strafe(MovementUtil.getSpeed(), this.yawForStrafe);
                    if (this.airTicks == 8 && Class205.method1387() <= 0.75) MovementUtil.strafe(Class205.method1392(), this.yawForStrafe);
                } else {
                    if (this.airTicks == 2) {
                        Vec3d vel = mc.player.getVelocity();
                        mc.player.setVelocity((vel.x + vel.x * 2.0) / 3.0, vel.y, (vel.z + vel.z * 2.0) / 3.0);
                    }
                    if (this.airTicks == 9 && Class205.method1387() <= 0.8) {
                        Vec3d vel = mc.player.getVelocity();
                        mc.player.setVelocity(vel.x, vel.y + 0.075, vel.z);
                        MovementUtil.strafe(MovementUtil.getSpeed(), this.yawForStrafe);
                    }
                    if (this.airTicks == 10 && Class205.method1387() <= 0.8) {
                        if (Math.hypot(mc.player.getVelocity().x, mc.player.getVelocity().z) < Class205.method1392()
                                || mc.player.getVelocity().x == 0.0
                                || mc.player.getVelocity().z == 0.0) {
                            MovementUtil.strafe(Class205.method1392(), this.yawForStrafe);
                        }
                        MovementUtil.strafe(MovementUtil.getSpeed(), this.yawForStrafe);
                    }
                    if (getBlockStateAtOffset(0.0, mc.player.getVelocity().y, 0.0).equals(Blocks.AIR) && this.airTicks == 11) {
                        MovementUtil.strafe(0.31, this.yawForStrafe);
                    }
                }
            }
        }
    }

    @Override
    public void onDisable() {
        this.speedTwoInitFlag = true;
    }

    @Override
    public void onMotion(MotionEvent event) {
        if (mc.player.onGround && this.mode.getValue().equals("SpeedTwo")) {
            event.method1409(event.method1403() + 1.0E-14);
        }
    }

    @Override
    public void onStrafe(StrafeEvent event) {
        if (this.mode.getValue().equals("HypixelLowHop")) {
            event.method1400(this.yawForStrafe);
        }
    }

    @Override
    public void onPreTick() {
        if (mc.player.onGround) {
            if (this.speedTwoInitFlag) {
                this.speedTwoInitFlag = false;
                return;
            }
            if (this.airTicks > 0) this.lastAirTicks = this.airTicks;
            this.airTicks = 0;
        } else {
            this.airTicks++;
        }

        boolean class197Enabled = false;
        Class197 var197 = (Class197) AegisClient.moduleManager.field2010.get(Class197.class);
        if (var197 != null) class197Enabled = var197.isEnabled();

        switch (this.mode.getValue()) {
            case "Bhop":
                if ((!this.noScaffold.getValue() || !class197Enabled) && MovementUtil.isMoving()) {
                    if (mc.player.onGround) {
                        this.jumpingThisTick = true;
                        mc.player.jump();
                        this.jumpingThisTick = false;
                        MovementUtil.strafe((float) this.speedValue.getValue() + MovementUtil.getSpeed());
                    } else {
                        MovementUtil.strafe((float) this.speedValue.getValue() * 0.1F + MovementUtil.getSpeed());
                    }
                }
                break;
            case "Strafe":
                if (MovementUtil.isMoving() && (!this.noScaffold.getValue() || !class197Enabled)) {
                    if (mc.player.onGround) {
                        this.jumpingThisTick = true;
                        mc.player.jump();
                        this.jumpingThisTick = false;
                    }
                    MovementUtil.strafe();
                }
                break;
            case "GroundStrafe":
                if (mc.player.onGround && MovementUtil.isMoving() && (!this.noScaffold.getValue() || !class197Enabled)) {
                    this.jumpingThisTick = true;
                    mc.player.jump();
                    this.jumpingThisTick = false;
                }
                break;
            case "SpeedTwo":
            case "HypixelLowHop":
                this.handleLowHop();
                break;
        }
    }
}
