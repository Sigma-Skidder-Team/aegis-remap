package nikoisntcat.client.modules.impl.world;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.utils.PlayerUtil;

public class InventoryMoveModule extends Module {

    public BooleanSetting hypixelBypass = new BooleanSetting("Hypixel", true);
    private boolean clickedSlot = false;
    private final KeyBinding[] movementKeys;

    public InventoryMoveModule() {
        super("InventoryMove", 0, Category.WORLD);

        this.movementKeys = new KeyBinding[]{
                mc.options.forwardKey,
                mc.options.rightKey,
                mc.options.leftKey,
                mc.options.jumpKey,
                mc.options.backKey
        };
    }

    @Override
    public void onSendPacket(PacketSendEvent event) {
        if (hypixelBypass.getValue() && mc.currentScreen == null) {
            Packet packet = event.getPacket();

            if (packet instanceof ClickSlotC2SPacket) {
                clickedSlot = true;
            }
            if (packet instanceof CloseHandledScreenC2SPacket) {
                clickedSlot = false;
            }
        }
    }

    @Override
    public void onMotion(MotionEvent event) {
        if (clickedSlot && event.method1399() == MotionEvent.Class123.PRE && mc.currentScreen == null) {
            mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
        }
    }

    @Override
    public void onTick() {
        if (!hypixelBypass.getValue() && mc.currentScreen != null) {
            for (KeyBinding key : movementKeys) {
                key.setPressed(PlayerUtil.isKeyPressed(key));
            }
        }
    }
}
