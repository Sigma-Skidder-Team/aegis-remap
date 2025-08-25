package nikoisntcat.client.modules.impl;

import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.utils.Class224;
import nikoisntcat.client.utils.Class231;
import nikoisntcat.client.utils.render.Notification;

public class DisablerModule extends Module {
    private float field1633;
    private float field1634;
    public static BooleanSetting field1635;
    public static BooleanSetting field1636;
    private boolean field1637 = false;
    private boolean field1638 = false;
    public static BooleanSetting field1639;
    public static BooleanSetting field1640;
    private int field1641 = 0;
    public static BooleanSetting field1642;
    private float field1643;
    public static BooleanSetting field1644;
    private int field1645 = 0;
    public static BooleanSetting field1646;
    private int field1647 = 0;
    static Object field1648;

    @Override
    public void onSendPacket(PacketSendEvent event) {
        Packet packet = event.getPacket();
        if (packet instanceof UpdateSelectedSlotC2SPacket) {
            UpdateSelectedSlotC2SPacket updateSelectedSlotC2SPacket = (UpdateSelectedSlotC2SPacket)packet;
            if (updateSelectedSlotC2SPacket.getSelectedSlot() == this.field1647) {
                event.cancel();
            }
            this.field1647 = updateSelectedSlotC2SPacket.getSelectedSlot();
        }
    }

    @Override
    public void onPreTick() {
        this.field1645 = mc.player.onGround ? 0 : ++this.field1645;
    }

    @Override
    public void onReceivePacket(PacketReceiveEvent event) {
        Packet packet = event.getPacket();
        if (packet instanceof PlayerPositionLookS2CPacket) {
            if (field1646.method1703()) {
                event.cancel();
            }
            if (field1635.method1703() && !this.field1637) {
                ++this.field1641;
            }
        }
    }

    @Override
    public void onMotion(MotionEvent motionEvent) {
        if (field1635.method1703()) {
            if (mc.player.getInventory().getStack(8).getItem() == Items.NETHER_STAR) {
                this.field1637 = false;
                this.field1641 = 0;
                return;
            }
            if (!this.field1637 && motionEvent.method1399() == MotionEvent.Class123.PRE) {
                if (this.field1641 < 24) {
                    if (this.field1645 >= 9 && this.field1645 % 2 == 0) {
                        motionEvent.method1413(motionEvent.method1407() - (double)MathHelper.nextBetween((Random)Random.create(), (float)0.09f, (float)0.12f));
                    }
                    if (this.field1645 >= 9) {
                        mc.player.setVelocity(0.0, 0.0, 0.0);
                    }
                    if (mc.player.onGround) {
                        mc.player.jump();
                    }
                } else {
                    this.field1641 = 0;
                    this.field1637 = true;
                    NotificationModule.addNotification(new Notification(this.name, this.name, "Lowhop check has been disabled!", this.isEnabled(), false, false));
                }
            }
        }
        float f = mc.player.lastYaw;
        float f2 = mc.player.lastPitch;
        if (f == motionEvent.method1410() && f2 == motionEvent.method1414() || mc.currentScreen != null) {
            return;
        }
        if (field1636.method1703()) {
            if (Math.abs(Class231.method1516(f, motionEvent.method1410())) < 1.0E-4f) {
                motionEvent.method1400(Class231.method1516(f, motionEvent.method1410()) > 0.0f ? f + 1.0E-4f : f + -1.0E-4f);
            }
            if (Math.abs(f2 - motionEvent.method1414()) < 1.0E-4f) {
                motionEvent.method1404(f2 + 1.0E-4f < 90.0f ? f2 + 1.0E-4f : f2 + -1.0E-4f);
            }
        }
        if (field1642.method1703()) {
            int n = 360 * Random.create().nextBetween(1, 100);
            motionEvent.method1400(motionEvent.method1410() + (float)n);
        }
        this.field1638 = false;
    }

    @Override
    public void onTick() {
        if (Class224.nullCheck()) {
            this.field1647 = -1;
        }
        if (!field1635.method1703()) {
            this.field1637 = false;
            this.field1641 = 0;
        }
    }

    public DisablerModule() {
        super("Disabler", 0, Category.MISC);
    }

    static {
        field1639 = new BooleanSetting("DebugServerUsing", false);
        field1644 = new BooleanSetting("CancelServerUsing", false);
        field1640 = new BooleanSetting("cancelServerShield", false);
        field1646 = new BooleanSetting("SilentLagBack", false);
        field1635 = new BooleanSetting("hypLowHop", false);
        field1636 = new BooleanSetting("AcaAim", false);
        field1642 = new BooleanSetting("DuplicateRotationPlace", false);
    }

    public void method1214() {
        this.field1637 = false;
        this.field1641 = 0;
    }
}
