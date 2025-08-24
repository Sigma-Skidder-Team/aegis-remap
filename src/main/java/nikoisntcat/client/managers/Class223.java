package nikoisntcat.client.managers;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.utils.Class224;
import nikoisntcat.client.utils.MinecraftUtil;
import nikoisntcat.client.utils.interfaces.Class303;

public class Class223 extends MinecraftUtil {
    private static int field1994;
    private static boolean field1995;
    private static boolean field1996;
    public static int field1997;
    private static boolean field1998;
    public static int field1999;

    public void method1434() {
        if (Class224.nullCheck()) {
            method1277();
            return;
        }
        if (field1998) {
            field1995 = true;
            mc.player.getInventory().selectedSlot = field1999;
        }
    }

    public void onPacketSend(PacketSendEvent packetSendEvent) {
        if (packetSendEvent.isCancelled()) {
            return;
        }
        Packet<?> class_25962 = packetSendEvent.getPacket();
        if (class_25962 instanceof UpdateSelectedSlotC2SPacket slotC2SPacket) {
            if (slotC2SPacket.getSelectedSlot() == field1997) {
                packetSendEvent.cancel();
            }
            field1997 = slotC2SPacket.getSelectedSlot();
        }
    }

    public void method1436() {
        if (Class224.nullCheck()) {
            method1277();
            return;
        }
        if (field1998) {
            field1995 = false;
            mc.player.getInventory().selectedSlot = field1994;
        }
    }

    public void method1437() {
        if (Class224.nullCheck()) {
            method1277();
            return;
        }
        if (field1995) {
            field1995 = false;
            mc.player.getInventory().selectedSlot = field1994;
            if (field1996) {
                mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(field1994));
            }
        }
    }

    public void method1438() {
        this.method1437();
    }

    public static void method1439(int n, boolean bl) {
        field1995 = true;
        field1996 = bl;
        field1999 = n;
        if (!field1998) {
            field1994 = mc.player.getInventory().selectedSlot;
        }
        field1998 = true;
        mc.player.getInventory().selectedSlot = n;
    }

    public static void method1277() {
        if (!field1998) {
            return;
        }
        field1998 = false;
        field1995 = false;
        field1996 = false;
        if (!Class224.nullCheck()) {
            mc.player.getInventory().selectedSlot = field1994;
        }
        field1999 = -1;
        field1994 = -1;
    }

    public static RegistryEntry method1441(ItemStack stack, RegistryKey regKey) {
        return stack.getEnchantments().getEnchantments().stream().filter(class_68802 -> regKey.isOf(regKey.getRegistryRef())).findFirst().orElse(null);
    }

    private static void method1442(ItemStack stack, Class303 class303) {
        for (Object2IntMap.Entry entry : ((ItemEnchantmentsComponent) stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, (Object) ItemEnchantmentsComponent.DEFAULT)).getEnchantmentEntries()) {
            class303.method1914((RegistryEntry) entry.getKey(), entry.getIntValue());
        }
    }

    static {
        field1997 = 0;
        field1999 = 1;
        field1994 = 0;
        field1995 = false;
        field1996 = false;
        field1998 = false;
    }

    public void method1443() {
        this.method1434();
    }

    public void method1444() {
        if (Class224.nullCheck()) {
            method1277();
            return;
        }
        if (field1998) {
            field1994 = mc.player.getInventory().selectedSlot;
            this.method1434();
        }
    }
}