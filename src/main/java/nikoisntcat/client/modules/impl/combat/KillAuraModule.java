package nikoisntcat.client.modules.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.modules.impl.misc.TargetsModule;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.PlayerUtil;
import nikoisntcat.client.utils.Priority;
import nikoisntcat.client.utils.RotationUtil;
import nikoisntcat.client.utils.math.TimerUtil;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KillAuraModule extends Module {
    public static LivingEntity target, secondaryTarget;
    public final NumberSetting range = new NumberSetting("Range", 3.0, 6, 1, 0.1);
    public final NumberSetting cps = new NumberSetting("Cps", 15.0, 20, 1, 0.1);
    public final BooleanSetting keepSprint = new BooleanSetting("KeepSprint", false);
    public final ModeSetting targetMode = new ModeSetting("TargetMode", "Single", List.of("Single", "Switch"));
    public final NumberSetting switchDelay = new NumberSetting("SwitchDelay", 0.0, 10, 1, 0.1, e -> targetMode.getValue().equals("Switch"));
    public final NumberSetting rotationKeepTicks = new NumberSetting("RotationKeepTicks", 0.0, 10, 0.0, 0.1, e -> targetMode.getValue().equals("Switch"));
    public final ModeSetting autoBlock = new ModeSetting("AutoBlock", "Swap", List.of("Swap", "None"));
    public final ModeSetting blockHand = new ModeSetting("BlockHand", "MainHand", List.of("MainHand", "OffHand"));
    private final TimerUtil timerUtil = new TimerUtil();

    private long lastSwitch = 0;
    private int switchIndex = 0;

    public KillAuraModule() {
        super("KillAura", 0, Category.COMBAT);
    }

    @Override
    public void onPreTick() {
        if (PlayerUtil.nullCheck()) return;

        List<LivingEntity> targets = new ArrayList<>();
        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof LivingEntity entity)) continue;
            if (!isValidTarget(entity)) continue;
            if (mc.player.distanceTo(entity) > range.getValue()) continue;

            targets.add(entity);
        }

        targets.sort(Comparator.comparingDouble(mc.player::distanceTo));

        if (targets.isEmpty()) {
            target = null;
            return;
        }

        if (targetMode.getValue().equals("Single")) {
            target = targets.get(0);
        } else {
            if (System.currentTimeMillis() - lastSwitch >= (long) (switchDelay.getValue() * 1000)) {
                switchIndex++;
                if (switchIndex >= targets.size()) switchIndex = 0;
                target = targets.get(switchIndex);
                lastSwitch = System.currentTimeMillis();
            }
        }

        // CPS-limited attack
        double delay = 1000.0 / cps.getValue();
        if (target != null && timerUtil.passed((long) delay)) {
            attack(target);
            timerUtil.update();
        }
    }

    // Tech is god tier
    @Override
    public void onMotion(MotionEvent event) {
        if (target == null) return;
        Vector2f rotation = new Vector2f(target.getYaw(), target.getPitch());

        RotationUtil.rotate(rotation, (int) rotationKeepTicks.getValue(), Priority.field1513);

        event.setEntityYaw(rotation.x);
        event.setEntityPitch(rotation.y);
    }

    private void attack(LivingEntity entity) {
        mc.player.swingHand(Hand.MAIN_HAND);
        mc.interactionManager.attackEntity(mc.player, entity);

        if (!autoBlock.getValue().equals("None")) {
            Hand hand = blockHand.getValue().equals("MainHand") ? Hand.MAIN_HAND : Hand.OFF_HAND;
            mc.player.swingHand(hand);
        }
    }

    private boolean isValidTarget(LivingEntity entity) {
        if (entity == mc.player || entity.isDead() || entity.getHealth() <= 0) return false;
        if (entity instanceof PlayerEntity) return TargetsModule.targetPlayers.getValue();
        if (entity instanceof VillagerEntity) return TargetsModule.targetVillagers.getValue();
        if (entity instanceof AnimalEntity) return TargetsModule.targetAnimals.getValue();
        return TargetsModule.targetMobs.getValue();
    }

    @Override
    public void onDisable() {
        target = secondaryTarget = null;
        timerUtil.update();
    }

    @Override
    public void onEnable() {
        target = secondaryTarget = null;
        timerUtil.update();
    }
}
