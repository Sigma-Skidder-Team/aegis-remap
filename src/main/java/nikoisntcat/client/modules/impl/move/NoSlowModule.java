package nikoisntcat.client.modules.impl.move;

import java.util.List;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.play.SetPlayerInventoryS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.events.impl.SlowdownEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.utils.Class207;
import nikoisntcat.client.utils.PlayerUtil;
import nikoisntcat.client.utils.MovementUtil;

public class NoSlowModule extends Module {

    // Settings
    public BooleanSetting swordNoSlow = new BooleanSetting("Sword", false);
    public BooleanSetting foodNoSlow = new BooleanSetting("Food", false);
    public BooleanSetting bowNoSlow = new BooleanSetting("Bow", false);
    public BooleanSetting server1_8Fix = new BooleanSetting("Server1_8", false);

    public ModeSetting foodPacketMode = new ModeSetting("FoodPacketMode", "None", List.of("None", "Blink", "Half", "GrimTest"));
    public ModeSetting bowPacketMode = new ModeSetting("BowPacketMode", "None", List.of("None"));
    public ModeSetting swordPacketMode = new ModeSetting("SwordPacketMode", "None", List.of("None"));

    // State flags
    private boolean halfTickFlag;
    private boolean isBlinkingFood;
    private boolean wasUsingItemLastTick;
    private boolean isUsingItemThisTick;
    private boolean grimReleaseStarted;

    // Timer
    private int tickCounter;

    static Object unused; // leftover artifact

    public NoSlowModule() {
        super("NoSlow", 0, Category.MOVE);

        this.isBlinkingFood = false;
        this.wasUsingItemLastTick = false;
        this.isUsingItemThisTick = false;
        this.halfTickFlag = false;
        this.tickCounter = 0;
        this.grimReleaseStarted = false;
    }

    @Override
    public void onSlow(SlowdownEvent event) {
        ItemStack mainHand = mc.player.getInventory().getMainHandStack();
        Item item = mainHand.getItem();

        // Eating/Drinking handling
        if (server1_8Fix.getValue() && mc.options.useKey.isPressed() &&
                (mainHand.getUseAction() == UseAction.EAT || mainHand.getUseAction() == UseAction.DRINK)) {
            event.setSlowdown(true);

            if (!mc.player.isUsingItem() || mc.player.getItemUseTime() < 2) {
                isUsingItemThisTick = false;
                wasUsingItemLastTick = false;
                mc.player.setSprinting(false);
                mc.options.sprintKey.setPressed(false);
                return;
            }
        }

        if (event.getSlowdown()) {
            isUsingItemThisTick = true;

            // Sword
            if (item instanceof SwordItem && swordNoSlow.getValue()) {
                event.setSlowdown(false);

                // Bow
            } else if (item instanceof BowItem && bowNoSlow.getValue()) {
                event.setSlowdown(false);

                // Food
            } else if (foodNoSlow.getValue() &&
                    (mainHand.getUseAction() == UseAction.EAT || mainHand.getUseAction() == UseAction.DRINK)) {
                switch (foodPacketMode.getValue()) {
                    case "Blink":
                        if (!isBlinkingFood) {
                            mc.player.networkHandler.sendPacket(
                                    new PlayerInteractItemC2SPacket(
                                            Hand.MAIN_HAND,
                                            mc.world.getPendingUpdateManager().incrementSequence().getSequence(),
                                            mc.player.getYaw(),
                                            mc.player.getPitch()
                                    )
                            );
                            tickCounter = 0;
                            isBlinkingFood = true;
                        }
                        event.setSlowdown(false);
                        break;

                    case "GrimTest":
                        if (!isBlinkingFood) {
                            MovementUtil.isBlinking = true;
                            mc.player.networkHandler.sendPacket(
                                    new PlayerInteractItemC2SPacket(
                                            Hand.MAIN_HAND,
                                            mc.world.getPendingUpdateManager().incrementSequence().getSequence(),
                                            mc.player.getYaw(),
                                            mc.player.getPitch()
                                    )
                            );
                            tickCounter = 0;
                            isBlinkingFood = true;
                        } else {
                            event.setSlowdown(false);
                        }
                        break;

                    case "Half":
                        if (!mc.player.onGround && new Class207().method1396(1, mc.player.getYaw(), true).field2178) {
                            halfTickFlag = true;
                        } else if ((mc.player.age % 2 == 0 || halfTickFlag) && wasUsingItemLastTick) {
                            event.setSlowdown(false);
                            if (halfTickFlag) halfTickFlag = false;
                        }
                        break;

                    default:
                        event.setSlowdown(false);
                        break;
                }
            }
        }
    }

    @Override
    public void onTick() {
        if (!PlayerUtil.nullCheck()) {
            tickCounter++;
            wasUsingItemLastTick = isUsingItemThisTick;
            isUsingItemThisTick = false;
        }
    }

    @Override
    public void onMotion(MotionEvent event) {
        if (event.getState() == MotionEvent.State.PRE) {
            if (isBlinkingFood && !AegisClient.blinkUtil.isBlinking()) {
                AegisClient.blinkUtil.startBlinking(this);
                mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
            }
        } else {
            if (foodPacketMode.getValue().equals("Blink") && tickCounter > 33) {
                AegisClient.blinkUtil.stopBlinking();
                tickCounter = 0;
                isBlinkingFood = false;
            }

            if (foodPacketMode.getValue().equals("GrimTest") && isBlinkingFood) {
                if (tickCounter > 6) {
                    MovementUtil.isBlinking = false;
                }

                if (tickCounter > 33) {
                    if (grimReleaseStarted) {
                        AegisClient.blinkUtil.sendHeldPackets();
                        if (tickCounter % 2 == 0) {
                            AegisClient.blinkUtil.sendHeldPackets();
                            PlayerUtil.setTickCounter(1);
                            PlayerUtil.sendChatMessage("Release More. Now Left: " + AegisClient.blinkUtil.previousPacketQueues.size());
                        }
                    } else {
                        for (int i = 0; i < 9; i++) {
                            AegisClient.blinkUtil.sendHeldPackets();
                        }
                        grimReleaseStarted = true;
                    }

                    if (AegisClient.blinkUtil.previousPacketQueues.isEmpty()) {
                        AegisClient.blinkUtil.stopBlinking();
                        tickCounter = 0;
                        isBlinkingFood = false;
                        grimReleaseStarted = false;
                    }

                    mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
                }
            }
        }
    }

    @Override
    public void onSendPacket(PacketSendEvent event) {
        if (isBlinkingFood) {

        }
    }

    @Override
    public void onReceivePacket(PacketReceiveEvent event) {
        if (isBlinkingFood) {
            Packet<?> packet = event.getPacket();
            if (packet instanceof SetPlayerInventoryS2CPacket) {

            }
        }
    }
}
