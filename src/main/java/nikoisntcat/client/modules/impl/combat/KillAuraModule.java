package nikoisntcat.client.modules.impl.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
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
    private final NumberSetting range = new NumberSetting("Range", 3.0, 6, 1, 0.1);
    private final NumberSetting blockRange = new NumberSetting("BlockRange", 9.2, 10, 1, 0.1);
    private final NumberSetting cps = new NumberSetting("Cps", 15.0, 20, 1, 0);
    private final BooleanSetting keepSprint = new BooleanSetting("KeepSprint", false);
    private final BooleanSetting followTarget = new BooleanSetting("FollowTarget", false);
    private final ModeSetting targetMode = new ModeSetting("TargetMode", "Single", List.of("Single", "Switch"));
    private final NumberSetting switchDelay = new NumberSetting("SwitchDelay", 0.0, 10, 1, 0.1, e -> targetMode.getValue().equals("Switch"));
    private final NumberSetting rotationKeepTicks = new NumberSetting("rotationKeepTicks", 0.0, 10, 0.0, 0.1, e -> targetMode.getValue().equals("Switch"));

    private final ModeSetting autoBlock = new ModeSetting("AutoBlock", "Swap", List.of("Swap", "None"));
    private final ModeSetting blockHand = new ModeSetting("BlockHand", "MainHand", List.of("MainHand", "OffHand"));
    private final ModeSetting targetMark = new ModeSetting("TargetMark", "Sigma", List.of("Sigma"));

    public static LivingEntity target, secondaryTarget;
    private final TimerUtil timerUtil = new TimerUtil();

    private long lastSwitch = 0;
    private int switchIndex = 0;

    public KillAuraModule() {
        super("KillAura", 0, Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (PlayerUtil.nullCheck()) return;

        List<LivingEntity> targets = new ArrayList<>();
        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof LivingEntity entity)) continue;
            if (!PlayerUtil.isValidTarget(entity)) continue;
            if (mc.player.distanceTo(entity) > range.getValue()) continue;

            targets.add(entity);
        }

        targets.sort(Comparator.comparingDouble(mc.player::distanceTo));

        if (targets.isEmpty()) {
            target = null;
            return;
        }

        if (targetMode.getValue().equals("Single")) {
            target = targets.getFirst();
        } else { // Switch
            if (System.currentTimeMillis() - lastSwitch >= (long) (switchDelay.getValue() * 1000)) {
                switchIndex++;
                if (switchIndex >= targets.size()) switchIndex = 0;
                target = targets.get(switchIndex);
                lastSwitch = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void onMotion(MotionEvent event) {
        if (event.getState() != MotionEvent.State.PRE || target == null) return;

        RotationUtil.rotate(new Vector2f(target.getYaw(), target.getPitch()), (int) rotationKeepTicks.getValue(), Priority.field1513);

        // CPS limiter
        double delay = 1000.0 / cps.getValue();
        if (timerUtil.passed((long) delay)) {
            attack(target);
            timerUtil.update();
        }
    }

    private void attack(LivingEntity entity) {
        if (!keepSprint.getValue()) {
            mc.player.swingHand(Hand.MAIN_HAND);
        }
        mc.interactionManager.attackEntity(mc.player, entity);

        // AutoBlock logic (very basic)
        if (!autoBlock.getValue().equals("None")) {
            Hand hand = blockHand.getValue().equals("MainHand") ? Hand.MAIN_HAND : Hand.OFF_HAND;
            mc.player.swingHand(Hand.MAIN_HAND);
        }
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
