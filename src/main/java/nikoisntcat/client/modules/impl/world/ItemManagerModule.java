package nikoisntcat.client.modules.impl.world;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.LinkedBlockingQueue;

import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BedItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PotionItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.Class264;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.Class228;
import nikoisntcat.client.utils.math.TimerUtil;
import nikoisntcat.client.utils.player.Class205;

public class ItemManagerModule extends Module {
    public NumberSetting field1687;
    private boolean field1688;
    private int field1689;
    public Class264 field1690;
    private boolean field1691;
    public BooleanSetting field1692;
    public Class264 field1693;
    private final TimerUtil field1694;
    private final Random field1695;
    public NumberSetting field1696;
    public NumberSetting field1697;
    public BooleanSetting field1698;
    public Class264 field1699;
    public Class264 field1700;
    private final TimerUtil field1701;
    private final TimerUtil field1702;
    public NumberSetting field1703;
    public Class264 field1704;
    public NumberSetting field1705;
    public Class264 field1706;
    public NumberSetting field1707;
    public Class264 field1708;
    private Map<Integer, ItemStack> field1709;
    public NumberSetting field1710;
    public Class264 field1711;
    public Class264 field1712;
    public BooleanSetting field1713 = new BooleanSetting("NoInvOpen", false);
    private final LinkedBlockingQueue<Packet<?>> packets;

    private boolean method1265(int slot, ItemStack stack) {
        for (int var3 = 36; var3 < 45; var3++) {
            ItemStack var4 = this.field1709.get(var3);
            int var5 = var3 - 36;
            String var6 = this.method1270(var5);
            if (slot >= 36 && slot <= 44 && var6.equals(this.method1270(slot - 36))) {
                return false;
            }

            switch (var6) {
                case "Block":
                    if (stack.getItem() instanceof BlockItem && (var4.isEmpty() || !(var4.getItem() instanceof BlockItem))) {
                        return this.method1266(0, slot, var5, SlotActionType.SWAP);
                    }
                    break;
                case "Sword":
                    if (stack.getItem() instanceof SwordItem && (var4.isEmpty() || !(var4.getItem() instanceof SwordItem))) {
                        return this.method1266(0, slot, var5, SlotActionType.SWAP);
                    }
                    break;
                case "AppleGold":
                    if (stack.getItem() == Items.GOLDEN_APPLE && (var4.isEmpty() || var4.getItem() != Items.GOLDEN_APPLE)) {
                        return this.method1266(0, slot, var5, SlotActionType.SWAP);
                    }
                case "Bow":
                case "Projectiles":
                case "Potion":
                case "Axe":
                case "Pickaxe":
                default:
                    break;
                case "Peal":
                    if (stack.getItem() instanceof EnderPearlItem && !var4.isEmpty() && var4.getItem() != Items.GOLDEN_APPLE) {
                    }
            }
        }

        return false;
    }

    private boolean method1266(int syncId, int slotId, int button, SlotActionType action) {
        if (syncId == 0) {
            this.field1688 = true;
        }

        int var5;
        if (this.field1702.passed((long) (var5 = this.method1268(syncId, false)))) {
            mc.interactionManager.clickSlot(syncId, slotId, button, action, mc.player);
            this.field1702.update();
            this.field1689 = -1;
            if (var5 > 0 || this.field1698.getValue()) {
                return true;
            }
        }

        return false;
    }

    private boolean method1267() {
        return mc.currentScreen instanceof GenericContainerScreen;
    }

    @Override
    public void onReceivePacket(PacketReceiveEvent event) {
        if (event.getPacket() instanceof ScreenHandlerSlotUpdateS2CPacket
                || event.getPacket() instanceof EntityEquipmentUpdateS2CPacket
                || event.getPacket() instanceof InventoryS2CPacket) {
            if (!this.field1688 && this.field1694.method1902(this.field1687.getValue())) {
                while (!this.packets.isEmpty()) {
                    ((Packet) this.packets.poll()).apply(mc.getNetworkHandler());
                }
            }

            if (!this.field1694.method1902(this.field1687.getValue()) && this.field1692.getValue()) {
                event.cancel();
                this.packets.add(event.getPacket());
                this.field1694.update();
            }
        }

        if (event.getPacket() instanceof OpenScreenS2CPacket) {
            while (!this.packets.isEmpty()) {
                ((Packet) this.packets.poll()).apply(mc.getNetworkHandler());
            }

            this.field1694.setCurrentMs(0L);
        }
    }

    private int method1268(int syncId, boolean isClose) {
        if (this.field1689 != -1) {
            return this.field1689;
        } else if (isClose) {
            return this.field1689 = MathHelper.nextBetween(this.field1695, (int) this.field1710.getValue(), (int) this.field1705.getValue());
        } else {
            return syncId == 0
                    ? (this.field1689 = MathHelper.nextBetween(this.field1695, (int) this.field1697.getValue(), (int) this.field1696.getValue()))
                    : (this.field1689 = MathHelper.nextBetween(this.field1695, (int) this.field1707.getValue(), (int) this.field1703.getValue()));
        }
    }

    private boolean method1269() {
        return mc.currentScreen instanceof InventoryScreen || this.field1713.getValue();
    }

    @Override
    public void onPreTick() {
        this.field1709 = Class205.method1389();
        if (mc.player.getInventory().getStack(8).getItem() != Items.NETHER_STAR && !(mc.player.getInventory().getStack(8).getItem() instanceof BedItem)) {
            if (mc.currentScreen == null && !this.field1713.getValue() && this.field1698.getValue()) {
                this.field1702.update();
                this.field1701.update();
                this.field1694.update();
            }

            if (mc.currentScreen != null || Class165.field1607 == null) {
                if (this.field1688) {
                    this.field1694.update();
                } else if (this.field1694.method1902(this.field1687.getValue())) {
                    while (!this.packets.isEmpty()) {
                        ((Packet) this.packets.poll()).apply(mc.getNetworkHandler());
                    }
                }

                this.field1688 = false;
                if (this.method1267()) {
                    if (mc.currentScreen instanceof GenericContainerScreen var2) {
                        boolean var7 = false;

                        for (int var3 = 0; var3 < ((GenericContainerScreenHandler) var2.getScreenHandler()).getRows() * 9; var3++) {
                            if (!((GenericContainerScreenHandler) var2.getScreenHandler()).getSlot(var3).getStack().isEmpty()) {
                                var7 = true;
                                this.field1701.update();
                                if (this.method1266(((GenericContainerScreenHandler) var2.getScreenHandler()).syncId, var3, 0, SlotActionType.QUICK_MOVE)) {
                                    break;
                                }
                            }
                        }

                        if (!var7 && this.field1701.passed((long) this.method1268(-1, true))) {
                            mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(((GenericContainerScreenHandler) var2.getScreenHandler()).syncId));
                            mc.player.closeScreen();
                            this.field1689 = -1;
                        }
                    }

                    this.field1688 = true;
                }

                if (this.method1269()) {
                    HashMap var9 = Class205.method1386();
                    if (this.field1691) {
                        if (this.method1266(0, -999, 0, SlotActionType.PICKUP)) {
                            this.field1691 = false;
                        }

                        return;
                    }

                    for (Entry var10 : this.field1709.entrySet()) {
                        ItemStack var4 = (ItemStack) var10.getValue();
                        if (!var4.isEmpty()) {
                            int var5 = (Integer) var10.getKey();
                            Item var6 = var4.getItem();
                            if ((!(var6 instanceof SwordItem) || !var9.containsKey(var5))
                                    && (
                                    var6 instanceof ArmorItem && var9.containsKey(var5)
                                            ? var5 > 8 && this.method1266(0, var5, 0, SlotActionType.QUICK_MOVE)
                                            : (!(var6 instanceof BlockItem) || Class228.method1492(((BlockItem) var6).getBlock()))
                                            && (!(var6 instanceof MiningToolItem) || !var9.containsKey(var5))
                                            && (var4.getUseAction() == UseAction.NONE || !var9.containsKey(var5))
                                            && (!(var6 instanceof BowItem) || !var9.containsKey(var5))
                                            && !(var6 instanceof ArrowItem)
                                            && !(var6 instanceof EnderPearlItem)
                                            && !(var6 instanceof PotionItem)
                                            && this.method1266(0, var5, 1, SlotActionType.THROW)
                            )
                                    || this.method1265(var5, var4)) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public ItemManagerModule() {
        super("ItemManager", 0, Category.WORLD);
        this.field1703 = new NumberSetting("MaxChestClickDelay", 0.0, 2000.0, 0.0, 1.0);
        this.field1707 = new NumberSetting("MinChestClickDelay", 0.0, 2000.0, 0.0, 1.0);
        this.field1696 = new NumberSetting("MaxInventoryClickDelay", 0.0, 2000.0, 0.0, 1.0);
        this.field1697 = new NumberSetting("MinInventoryClickDelay", 0.0, 2000.0, 0.0, 1.0);
        this.field1705 = new NumberSetting("MaxCloseDelay", 0.0, 2000.0, 0.0, 1.0);
        this.field1710 = new NumberSetting("MinCloseDelay", 0.0, 2000.0, 0.0, 1.0);
        this.field1692 = new BooleanSetting("ItemLagFix", false);
        this.field1687 = new NumberSetting("ItemLagFixDelay", 0.0, 1000.0, 0.0, 1.0);
        this.field1708 = new Class264("Slot_1");
        this.field1712 = new Class264("Slot_2");
        this.field1711 = new Class264("Slot_3");
        this.field1700 = new Class264("Slot_4");
        this.field1706 = new Class264("Slot_5");
        this.field1704 = new Class264("Slot_6");
        this.field1693 = new Class264("Slot_7");
        this.field1690 = new Class264("Slot_8");
        this.field1699 = new Class264("Slot_9");
        this.field1698 = new BooleanSetting("Legit", true);
        this.field1701 = new TimerUtil();
        this.field1702 = new TimerUtil();
        this.field1691 = false;
        this.field1688 = false;
        this.field1694 = new TimerUtil();
        this.packets = new LinkedBlockingQueue();
        this.field1695 = Random.create();
        this.field1689 = -1;
    }

    private String method1270(int index) {
        return new String[]{
                this.field1708.getValue(),
                this.field1712.getValue(),
                this.field1711.getValue(),
                this.field1700.getValue(),
                this.field1706.getValue(),
                this.field1704.getValue(),
                this.field1693.getValue(),
                this.field1690.getValue(),
                this.field1699.getValue()
        }[index];
    }
}
