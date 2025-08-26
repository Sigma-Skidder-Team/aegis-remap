package nikoisntcat.client.modules.impl;

import java.util.List;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.MathHelper;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.*;
import nikoisntcat.client.managers.Class223;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.Class259;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.PlayerUtil;
import nikoisntcat.client.utils.Class228;
import nikoisntcat.client.utils.RotationUtil;
import nikoisntcat.client.utils.MovementUtil;
import nikoisntcat.client.utils.math.TimerUtil;
import nikoisntcat.client.utils.player.Class205;
import nikoisntcat.client.utils.player.Class232;
import org.joml.Vector2f;

public class Class197 extends Module {
    private int field1862;
    public BooleanSetting field1863;
    public NumberSetting field1864;
    public NumberSetting field1865;
    public ModeSetting field1866;
    public ModeSetting field1867;
    public BooleanSetting field1868;
    public NumberSetting field1869;
    public ModeSetting field1870;
    public NumberSetting field1871;
    private Vector2f field1872;
    private int field1873;
    public NumberSetting field1874;
    public ModeSetting field1875;
    private boolean field1876;
    public BooleanSetting field1877;
    public NumberSetting field1878;
    private TimerUtil field1879;
    public NumberSetting field1880;
    public ModeSetting field1881;
    public NumberSetting field1882;
    public ModeSetting field1883;
    private Class232 field1884;
    private float field1885;
    private boolean field1886;
    public ModeSetting field1887 = new Class259(this);
    public BooleanSetting field1888;
    private float field1889;
    public boolean field1890;
    public NumberSetting field1891;
    private long field1892;
    public NumberSetting field1893;
    public NumberSetting field1894;
    public BooleanSetting field1895;
    private boolean field1896;
    public static boolean $skidonion$452611618;

    @Override
    public void onReceivePacket(PacketReceiveEvent event) {
        if (this.field1883.getValue().equals((String) method1370('\u0001'))) {
            Packet var2 = event.getPacket();
            if (var2 instanceof PlayerPositionLookS2CPacket) {
                this.field1876 = true;
            }
        }
    }

    public Class197() {
        super((String) method1370('\u0002'), 0, false, Category.MOVE);
        this.field1881 = new ModeSetting(
                (String) method1370('\u0003'),
                (String) method1370('\u0004'),
                List.of(
                        (String) method1370('\u0005'), (String) method1370('\u0004'), (String) method1370('\u0006'), (String) method1370('\u0007'), (String) method1370('\b')
                ),
                e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000'))
        );
        this.field1883 = new ModeSetting(
                (String) method1370('\t'),
                (String) method1370('\n'),
                List.of(
                        (String) method1370('\n'),
                        (String) method1370('\u000b'),
                        (String) method1370('\f'),
                        (String) method1370('\r'),
                        (String) method1370('\u0001'),
                        (String) method1370('\u000e')
                ),
                e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000'))
        );
        this.field1866 = new ModeSetting(
                (String) method1370('\u000f'),
                (String) method1370('\u0010'),
                List.of((String) method1370('\u0010'), (String) method1370('\u0011'), (String) method1370('\u0012')),
                e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000'))
        );
        this.field1868 = new BooleanSetting((String) method1370('\u0013'), true, e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000')));
        this.field1882 = new NumberSetting((String) method1370('\u0014'), 0.0, 4.0, 0.0, 1.0, e -> this.field1887.getValue().equals((String) method1370('\u0000')));
        this.field1893 = new NumberSetting(
                (String) method1370('\u0015'), 0.0, 10.0, 0.0, 1.0, e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000'))
        );
        this.field1878 = new NumberSetting(
                (String) method1370('\u0016'), 0.0, 10.0, 0.0, 1.0, e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000'))
        );
        this.field1870 = new ModeSetting(
                (String) method1370('\u0017'),
                (String) method1370('\u0010'),
                List.of((String) method1370('\u0010'), (String) method1370('\u0018'), (String) method1370('\u0019')),
                e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000'))
        );
        this.field1875 = new ModeSetting(
                (String) method1370('\u001a'),
                (String) method1370('\u0010'),
                List.of((String) method1370('\u001b'), (String) method1370('\u0010'), (String) method1370('\u001c'), (String) method1370('\u001d')),
                e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000'))
        );
        this.field1867 = new ModeSetting(
                (String) method1370('\u001e'),
                (String) method1370('\u0010'),
                List.of((String) method1370('\u0010'), (String) method1370('\u001f'), (String) method1370(' '), (String) method1370('!'), (String) method1370('"')),
                e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000'))
        );
        this.field1895 = new BooleanSetting((String) method1370('#'), false);
        this.field1888 = new BooleanSetting((String) method1370('$'), false, e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000')));
        this.field1863 = new BooleanSetting((String) method1370('%'), false);
        this.field1869 = new NumberSetting((String) method1370('&'), 1.0, 2.0, 0.0, 0.01, e -> this.field1863.getValue());
        this.field1894 = new NumberSetting((String) method1370('\''), 1.0, 10.0, 0.0, 1.0);
        this.field1891 = new NumberSetting((String) method1370('('), 0.0, 200.0, 0.0, 1.0);
        this.field1880 = new NumberSetting((String) method1370(')'), 0.0, 200.0, 0.0, 1.0);
        this.field1865 = new NumberSetting((String) method1370('*'), 180.0, 180.0, 0.0);
        this.field1864 = new NumberSetting((String) method1370('+'), 180.0, 180.0, 0.0);
        this.field1874 = new NumberSetting((String) method1370(','), 180.0, 180.0, 0.0);
        this.field1871 = new NumberSetting((String) method1370('-'), 180.0, 180.0, 0.0);
        this.field1877 = new BooleanSetting((String) method1370('.'), false, e -> this.field1887.getValue().equalsIgnoreCase((String) method1370('\u0000')));
        this.field1884 = null;
        this.field1872 = null;
        this.field1886 = false;
        this.field1862 = 0;
        this.field1873 = 0;
        this.field1890 = false;
        this.field1889 = 0.0F;
        this.field1876 = false;
        this.field1885 = 0.0F;
        this.field1896 = false;
        this.field1892 = 0L;
        this.field1879 = new TimerUtil();
    }

    private native boolean method1348();

    private void method1349() {
        boolean var1 = this.field1875.getValue().equals((String) method1370('\u001b'))
                || this.field1875.getValue().equals((String) method1370('\u001c')) && mc.player.onGround
                || this.field1875.getValue().equals((String) method1370('\u001d')) && !mc.player.onGround;
        mc.options.sprintKey.setPressed(var1 || this.field1888.getValue());
        if (!var1) {
            mc.player.setSprinting(false);
        }
    }

    @Override
    public void onEnable() {
        this.field1886 = false;
        this.field1884 = null;
        this.field1862 = 0;
        this.field1876 = false;
        this.field1885 = 0.0F;
        if (!mc.player.onGround) {
            this.field1862 = 114514;
        }
    }

    @Override
    public native void onMoveInput(MoveInputEvent moveInputEvent);

    @Override
    public void onSendPacket(PacketSendEvent event) {
        if (this.field1883.getValue().equals((String) method1370('\u0001'))) {
            if (event.getPacket() instanceof Full var3 && this.field1876) {
                event.cancel();
                this.field1876 = false;
                mc.getNetworkHandler()
                        .sendPacket(new Full(var3.getX(0.0), var3.getY(0.0), var3.getZ(0.0), var3.getYaw(0.0F), 110.0F, var3.isOnGround(), var3.horizontalCollision()));
            }

            if (event.getPacket() instanceof PlayerMoveC2SPacket var5 && var5.changesLook() && !event.isCancelled()) {
                this.field1885 = var5.getPitch(0.0F);
            }
        }
    }

    @Override
    public void onDisable() {
        mc.options.jumpKey.setPressed(PlayerUtil.isKeyPressed(mc.options.jumpKey));
        Class223.method1277();
        AegisClient.blinkUtil.method2047();
        this.field1890 = false;
    }

    private void method1359() {
        this.method1367();
        this.method1365(
                (double) this.field1862 > this.field1893.getValue()
                        || !this.field1881.getValue().equals((String) method1370('\u0007')) && !this.field1881.getValue().equals((String) method1370('\b'))
        );
    }

    private native int method1360();

    private void method1361() {
        int var1 = Class205.method1391((int) this.field1894.getValue());
        if (var1 == -1) {
            this.field1890 = true;
        } else {
            Class223.method1439(var1, false);
        }
    }

    private native void method1362();

    private native void method1365(boolean canRotate);

    @Override
    public void onPreTick() {
        if (!PlayerUtil.nullCheck()) {
            if (mc.player.onGround && this.field1863.getValue()) {
                MovementUtil.strafe((double) ((float) ((double) MovementUtil.getSpeed() * this.field1869.getValue())));
            }

            if (this.field1888.getValue()) {
                mc.player.setSprinting(true);
            } else {
                this.method1349();
            }

            this.method1366();
            this.field1896 = false;
            if ((double) this.field1862 > this.field1878.getValue()
                    || !this.field1881.getValue().equals((String) method1370('\u0007')) && !this.field1881.getValue().equals((String) method1370('\b'))) {
                this.method1362();

                for (int var1 = 0; (double) var1 < this.field1882.getValue(); var1++) {
                    this.field1884 = Class232.method1550(this.field1886, 5);
                    this.method1362();
                }
            }

            if (!this.field1896 && this.field1881.getValue().equals((String) method1370('\b'))) {
                BlockHitResult var2 = RotationUtil.method1517(RotationUtil.method1539(), 4.5);
                if (!mc.world.getBlockState(var2.getBlockPos()).isReplaceable()
                        && !Class228.method1488(var2.getBlockPos(), var2.getSide())
                        && mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(var2.getPos(), var2.getSide(), var2.getBlockPos(), false))
                        == ActionResult.SUCCESS) {
                    mc.player.swingHand(Hand.MAIN_HAND);
                }
            }

            RotationUtil.field2036 = true;
        }
    }

    private void method1366() {
        String var1 = this.field1870.getValue();
        //TODO: This
        switch (var1) {
            case "(String)method1370('\u0018')":
                mc.options.jumpKey.setPressed(mc.player.onGround && mc.options.forwardKey.isPressed() || PlayerUtil.isKeyPressed(mc.options.jumpKey));
                break;
            case "(String)method1370('\u0019')":
                if (mc.player.onGround && mc.options.forwardKey.isPressed()) {
                    mc.player.jump();
                }
        }
    }

    @Override
    public void onTick() {
        if (!PlayerUtil.nullCheck()) {
            this.field1873++;
            if (mc.player.onGround) {
                this.field1862 = 0;
                this.field1886 = PlayerUtil.isKeyPressed(mc.options.jumpKey)
                        || !this.field1881.getValue().equals((String) method1370('\u0006'))
                        && !this.field1881.getValue().equals((String) method1370('\u0007'))
                        && !this.field1881.getValue().equals((String) method1370('\b'));
                this.field1889 = mc.player.getYaw();
            } else {
                this.field1862++;
            }

            this.method1359();
            this.method1361();
            if (this.field1881.getValue().equals((String) method1370('\u0005')) && !MovementUtil.isMoving()) {
                AegisClient.blinkUtil.method2047();
            }
        }
    }

    private native void method1367();

    @Override
    public native void onMotion(MotionEvent motionEvent);

    @Override
    public void onJump(JumpEvent jumpEvent) {
    }

    private long method1369() {
        return (long) MathHelper.nextBetween(AegisClient.field2320, (int) this.field1891.getValue(), (int) this.field1880.getValue());
    }

    private static native Object method1370(char var0);
}
