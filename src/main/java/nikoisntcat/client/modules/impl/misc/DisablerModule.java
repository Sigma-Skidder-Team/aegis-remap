package nikoisntcat.client.modules.impl.misc;

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
import nikoisntcat.client.modules.impl.render.NotificationModule;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.utils.PlayerUtil;
import nikoisntcat.client.utils.RotationUtil;
import nikoisntcat.client.utils.render.Notification;

public class DisablerModule extends Module {

    private float lastYaw;
    private float lastPitch;
    public static BooleanSetting hypLowHop;
    public static BooleanSetting acaAim;
    public static BooleanSetting debugServerUsing;
    public static BooleanSetting cancelServerUsing;
    public static BooleanSetting cancelServerShield;
    public static BooleanSetting silentLagBack;
    public static BooleanSetting duplicateRotationPlace;

    private boolean lowHopActive = false;
    private int lowHopTicks = 0;
    private int airTicks = 0;

    private int lastSelectedSlot = -1;

    public DisablerModule() {
        super("Disabler", 0, Category.MISC);
    }

    @Override
    public void onSendPacket(PacketSendEvent event) {
        Packet packet = event.getPacket();

        if (packet instanceof UpdateSelectedSlotC2SPacket updatePacket) {
            // Prevent sending packet if slot hasn't changed
            if (updatePacket.getSelectedSlot() == lastSelectedSlot) {
                event.cancel();
            }
            lastSelectedSlot = updatePacket.getSelectedSlot();
        }
    }

    @Override
    public void onPreTick() {
        // Count air ticks
        airTicks = mc.player.onGround ? 0 : ++airTicks;
    }

    @Override
    public void onReceivePacket(PacketReceiveEvent event) {
        Packet packet = event.getPacket();

        if (packet instanceof PlayerPositionLookS2CPacket) {
            if (silentLagBack.getValue()) {
                event.cancel();
            }
            if (hypLowHop.getValue() && !lowHopActive) {
                ++lowHopTicks;
            }
        }
    }

    @Override
    public void onMotion(MotionEvent event) {
        // Hypixel low-hop logic
        if (hypLowHop.getValue()) {
            if (mc.player.getInventory().getStack(8).getItem() == Items.NETHER_STAR) {
                lowHopActive = false;
                lowHopTicks = 0;
                return;
            }

            if (!lowHopActive && event.getTiming() == MotionEvent.Timing.PRE) {
                if (lowHopTicks < 24) {
                    if (airTicks >= 9 && airTicks % 2 == 0) {
                        event.setEntityZ(event.getEntityZ() -
                                (double) MathHelper.nextBetween((Random) Random.create(), 0.09f, 0.12f));
                    }
                    if (airTicks >= 9) {
                        mc.player.setVelocity(0.0, 0.0, 0.0);
                    }
                    if (mc.player.onGround) {
                        mc.player.jump();
                    }
                } else {
                    lowHopTicks = 0;
                    lowHopActive = true;
                    NotificationModule.addNotification(new Notification(
                            this.name, this.name, "Lowhop check has been disabled!", this.isEnabled(), false, false));
                }
            }
        }

        // Rotation handling
        float currentYaw = mc.player.lastYaw;
        float currentPitch = mc.player.lastPitch;

        if (currentYaw == event.getEntityYaw() && currentPitch == event.getEntityPitch() || mc.currentScreen != null) {
            return;
        }

        if (acaAim.getValue()) {
            if (Math.abs(RotationUtil.wrap(currentYaw, event.getEntityYaw())) < 1.0E-4f) {
                event.setEntityYaw(RotationUtil.wrap(currentYaw, event.getEntityYaw()) > 0.0f
                        ? currentYaw + 1.0E-4f
                        : currentYaw - 1.0E-4f);
            }
            if (Math.abs(currentPitch - event.getEntityPitch()) < 1.0E-4f) {
                event.setEntityPitch(currentPitch + 1.0E-4f < 90.0f
                        ? currentPitch + 1.0E-4f
                        : currentPitch - 1.0E-4f);
            }
        }

        if (duplicateRotationPlace.getValue()) {
            int randomYaw = 360 * Random.create().nextBetween(1, 100);
            event.setEntityYaw(event.getEntityYaw() + (float) randomYaw);
        }
    }

    @Override
    public void onTick() {
        if (PlayerUtil.nullCheck()) {
            lastSelectedSlot = -1;
        }

        if (!hypLowHop.getValue()) {
            lowHopActive = false;
            lowHopTicks = 0;
        }
    }

    public void resetLowHop() {
        lowHopActive = false;
        lowHopTicks = 0;
    }

    // Initialize settings
    static {
        debugServerUsing = new BooleanSetting("DebugServerUsing", false);
        cancelServerUsing = new BooleanSetting("CancelServerUsing", false);
        cancelServerShield = new BooleanSetting("CancelServerShield", false);
        hypLowHop = new BooleanSetting("hypLowHop", false);
        acaAim = new BooleanSetting("AcaAim", false);
        silentLagBack = new BooleanSetting("SilentLagBack", false);
        duplicateRotationPlace = new BooleanSetting("DuplicateRotationPlace", false);
    }
}
