package nikoisntcat.client.utils;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Npc;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.modules.impl.TargetsModule;
import nikoisntcat.client.modules.impl.TeamsModule;

import java.util.function.Supplier;

public class Class224 extends MinecraftUtil {
    public static Vec3d field2000;
    public static boolean field2001;
    private static Supplier field2002;
    private static int field2003;

    public static boolean method1445(KeyBinding class_3042) {
        return InputUtil.isKeyPressed((long) mc.getWindow().getHandle(), (int) class_3042.getDefaultKey().getCode());
    }

    public static void method1446(Supplier supplier) {
        if (field2002 == null) {
            field2000 = new Vec3d(mc.player.prevX, mc.player.prevY, mc.player.prevZ);
            field2002 = supplier;
        }
    }

    static {
        field2002 = null;
        field2001 = false;
        field2000 = new Vec3d(0.0, 0.0, 0.0);
    }

    public static void method1447() {
        mc.player.prevBodyYaw = mc.player.bodyYaw;
        mc.player.prevHeadYaw = mc.player.headYaw;
        mc.player.lastRenderYaw = mc.player.renderYaw;
        mc.player.lastRenderPitch = mc.player.renderPitch;
        mc.player.lastHandSwingProgress = mc.player.handSwingProgress;
        mc.player.prevX = mc.player.getX();
        mc.player.prevY = mc.player.getY();
        mc.player.prevZ = mc.player.getZ();
        mc.player.lastRenderX = mc.player.getX();
        mc.player.lastRenderY = mc.player.getY();
        mc.player.lastRenderZ = mc.player.getZ();
        if (mc.world != null) {
            mc.gameRenderer.tick();
            mc.worldRenderer.tick();
        }
        if (field2003 > 0) {
            --field2003;
        }
        field2001 = true;
    }

    public static boolean method1448(LivingEntity class_13092) {
        if (mc.player == null || class_13092.getId() == mc.player.getId() || class_13092 instanceof PlayerEntity && !TargetsModule.field1773.getValue() || class_13092 instanceof AnimalEntity && !TargetsModule.field1774.getValue() || (class_13092 instanceof Npc || class_13092 instanceof Monster) && !TargetsModule.field1772.getValue() || class_13092 instanceof VillagerEntity && !TargetsModule.field1771.getValue()) {
          return false;
        }
        if (TeamsModule.method1375((Entity) class_13092)) {
            return false;
        }
        //return !Class201.field1921.method1380(class_13092);
        return false;
    }

    public static boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public static void method1450(int n) {
        if (field2003 == 0) {
            field2000 = new Vec3d(mc.player.prevX, mc.player.prevY, mc.player.prevZ);
        }
        field2003 = n;
    }

    public static void method1277() {
        if (field2000 != null) {
            mc.player.prevX = field2000.x;
            mc.player.prevY = field2000.y;
            mc.player.prevZ = field2000.z;
        }
        field2001 = false;
        field2000 = null;
    }

    public static void method1451(String string) {
        mc.inGameHud.getChatHud().addMessage(Text.of((String) ("[AegisClient] " + string)));
    }

    public static int method1452() {
        if (field2002 != null) {
            return (Boolean) field2002.get() != false ? 1 : 0;
        }
        return field2003;
    }
}