package nikoisntcat.client.utils.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FireChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.managers.Class223;
import nikoisntcat.client.utils.Class228;
import nikoisntcat.client.utils.MinecraftUtil;
import nikoisntcat.client.utils.interfaces.IArmorItem;
import nikoisntcat.client.utils.interfaces.ISwordItem;
import org.joml.Vector2d;

public class Class205 extends MinecraftUtil {
    private static final double field1929;
    private static final double field1930;
    private static final double field1931;
    private static final double field1932;
    private static final double field1933;

    public static HashMap method1386() {
        HashMap<Integer, ItemStack> hashMap = method1389();
        Vector2d vector2d = new Vector2d(0.0, 0.0);
        Vector2d vector2d2 = new Vector2d(0.0, 0.0);
        Vector2d vector2d3 = new Vector2d(0.0, 0.0);
        Vector2d vector2d4 = new Vector2d(0.0, 0.0);
        Vector2d vector2d5 = new Vector2d(0.0, 0.0);
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (Map.Entry<Integer, ItemStack> entry : hashMap.entrySet()) {
            double d;
            ItemStack itemStack = (ItemStack)entry.getValue();
            int n = (Integer)entry.getKey();
            if (itemStack.isEmpty()) continue;
            Item item = itemStack.getItem();
            if (item instanceof SwordItem) {
                d = ((ISwordItem)((SwordItem)item)).getAttackDamage() + 1.25 * (double)itemStack.getEnchantments().getLevel(Class223.method1441(itemStack, Enchantments.SHARPNESS));
                if (d > vector2d5.x) {
                    if (vector2d5.x != 0.0) {
                        arrayList.add((int)vector2d5.y);
                    }
                    vector2d5.x = d;
                    vector2d5.y = n;
                } else {
                    arrayList.add(n);
                }
            }
            if (!(item instanceof ArmorItem)) continue;
            IArmorItem iArmorItem = (IArmorItem)((ArmorItem)item);
            d = (double)((Integer)iArmorItem.getArmorMaterial().defense().get(iArmorItem.getEquipmentType())).intValue() + 0.01 * (double)itemStack.getEnchantments().getLevel(Class223.method1441(itemStack, Enchantments.PROTECTION));
            switch (iArmorItem.getEquipmentType()) {
                case BODY: {
                    break;
                }
                case BOOTS: {
                    if (d > vector2d.x) {
                        if (vector2d.x != 0.0) {
                            arrayList.add((int)vector2d.y);
                        }
                        vector2d.y = n;
                        vector2d.x = d;
                        break;
                    }
                    arrayList.add(n);
                    break;
                }
                case HELMET: {
                    if (d > vector2d2.x) {
                        if (vector2d2.x != 0.0) {
                            arrayList.add((int)vector2d2.y);
                        }
                        vector2d2.y = n;
                        vector2d2.x = d;
                        break;
                    }
                    arrayList.add(n);
                    break;
                }
                case LEGGINGS: {
                    if (d > vector2d3.x) {
                        if (vector2d3.x != 0.0) {
                            arrayList.add((int)vector2d3.y);
                        }
                        vector2d3.y = n;
                        vector2d3.x = d;
                        break;
                    }
                    arrayList.add(n);
                    break;
                }
                case CHESTPLATE: {
                    if (d > vector2d4.x) {
                        if (vector2d4.x != 0.0) {
                            arrayList.add((int)vector2d4.y);
                        }
                        vector2d4.y = n;
                        vector2d4.x = d;
                        break;
                    }
                    arrayList.add(n);
                }
            }
        }
        arrayList.forEach(hashMap::remove);
        return hashMap;
    }

    public static double method1387() {
        if (mc.player == null) {
            return -1.0;
        }
        Vec3d vec3d = mc.player.getPos();
        double d = vec3d.x;
        double d2 = vec3d.y;
        double d3 = vec3d.z;
        if (mc.player.isOnGround()) {
            return 0.0;
        }
        for (int i = (int)Math.floor(d2); i >= 0; --i) {
            BlockPos blockPos = Class228.method1489(d, i, d3);
            if (mc.world.getBlockState(blockPos).isAir()) continue;
            return d2 - (double)(i + 1);
        }
        return d2;
    }

    public static int method1388(BlockState state) {
        int n = -1;
        float f = 1.0f;
        for (int i = 0; i < 9; ++i) {
            Item item;
            float f2;
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (itemStack == null || !((f2 = state == null && (item = itemStack.getItem()) instanceof SwordItem ? (float)(((ISwordItem)((SwordItem)item)).getAttackDamage() + 1.25 * (double)itemStack.getEnchantments().getLevel(Class223.method1441(itemStack, Enchantments.SHARPNESS))) : (state != null ? itemStack.getMiningSpeedMultiplier(state) : 1.0f)) > f)) continue;
            n = i;
            f = f2;
        }
        return n;
    }

    public static HashMap<Integer, ItemStack> method1389() {
        HashMap<Integer, ItemStack> hashMap = new HashMap<Integer, ItemStack>();
        for (int i = 5; i < 45; ++i) {
            ItemStack itemStack;
            int n = i <= 8 ? i - 5 : i - 9;
            ItemStack itemStack2 = itemStack = i <= 8 ? mc.player.getInventory().getArmorStack(n) : (ItemStack)mc.player.getInventory().main.get(n);
            int n2 = i <= 8 ? 8 - n : (n <= 8 ? 36 + n : n);
            hashMap.put(n2, itemStack);
        }
        hashMap.put(45, mc.player.getOffHandStack());
        return hashMap;
    }

    static {
        field1930 = 1.3;
        field1931 = 0.3;
        field1933 = 0.5203619909502263;
        field1929 = 0.4751131221719457;
        field1932 = 0.221;
    }

    public static int method1390() {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (itemStack == null || !(itemStack.getItem() instanceof FireChargeItem)) continue;
            n = i;
        }
        return n;
    }

    public static int method1391(int minStack) {
        int n = -1;
        for (int i = 0; i < 9; ++i) {
            Item item;
            ItemStack itemStack = mc.player.getInventory().getStack(i);
            if (itemStack.isEmpty() || !((item = itemStack.getItem()) instanceof BlockItem) || Class228.method1492(((BlockItem)item).getBlock())) continue;
            n = i;
            if (itemStack.getCount() >= minStack) break;
        }
        return n;
    }

    public static double method1392() {
        double d;
        ClientPlayerEntity clientPlayerEntity = mc.player;
        if (clientPlayerEntity == null) {
            return 0.0;
        }
        boolean bl = false;
        if (clientPlayerEntity.isInFluid()) {
            d = 0.105;
        } else if (clientPlayerEntity.isInsideWaterOrBubbleColumn() || clientPlayerEntity.isInLava()) {
            d = 0.115;
        } else if (clientPlayerEntity.isSneaking()) {
            d = 0.0663;
        } else {
            d = 0.221;
            bl = true;
        }
        if (bl && !clientPlayerEntity.isSneaking() && (clientPlayerEntity.forwardSpeed != 0.0f || clientPlayerEntity.sidewaysSpeed != 0.0f)) {
            d *= 1.3;
        }
        return d;
    }
}
