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
import nikoisntcat.client.modules.impl.misc.TargetsModule;
import nikoisntcat.client.modules.impl.misc.TeamsModule;

import java.util.function.Supplier;

/**
 * Utility class for Minecraft client-related helpers.
 */
public class PlayerUtil extends MinecraftUtil {

    private static Vec3d lastPlayerPosition = new Vec3d(0.0, 0.0, 0.0);
    public static boolean playerStateSaved = false;
    private static Supplier<Boolean> stateSupplier = null;
    private static int ticksCounter;

    /**
     * Returns whether the key is currently pressed.
     */
    public static boolean isKeyPressed(KeyBinding key) {
        return InputUtil.isKeyPressed(mc.getWindow().getHandle(), key.getDefaultKey().getCode());
    }

    /**
     * Save player position if not already saved.
     */
    public static void savePlayerPosition(Supplier<Boolean> supplier) {
        if (stateSupplier == null) {
            lastPlayerPosition = new Vec3d(mc.player.prevX, mc.player.prevY, mc.player.prevZ);
            stateSupplier = supplier;
        }
    }

    /**
     * Updates player render and previous positions.
     */
    public static void updatePlayerRenderState() {
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

        if (ticksCounter > 0) {
            --ticksCounter;
        }

        playerStateSaved = true;
    }

    /**
     * Checks if a LivingEntity should be targeted.
     */
    public static boolean isValidTarget(LivingEntity entity) {
        if (mc.player == null
                || entity.getId() == mc.player.getId()
                || entity instanceof PlayerEntity && !TargetsModule.targetPlayers.getValue()
                || entity instanceof AnimalEntity && !TargetsModule.targetAnimals.getValue()
                || (entity instanceof Npc || entity instanceof Monster) && !TargetsModule.targetMobs.getValue()
                || entity instanceof VillagerEntity && !TargetsModule.targetVillagers.getValue()) {
            return false;
        }

        if (TeamsModule.isTeammate((Entity) entity)) {
            return false;
        }

        // Placeholder for additional checks
        // return !Class201.field1921.method1380(entity);
        return false;
    }

    /**
     * Checks if the client or world is null.
     */
    public static boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    /**
     * Sets a tick counter and optionally saves the player position.
     */
    public static void setTickCounter(int ticks) {
        if (ticksCounter == 0) {
            lastPlayerPosition = new Vec3d(mc.player.prevX, mc.player.prevY, mc.player.prevZ);
        }
        ticksCounter = ticks;
    }

    /**
     * Restores the player's last saved position.
     */
    public static void restorePlayerPosition() {
        if (lastPlayerPosition != null) {
            mc.player.prevX = lastPlayerPosition.x;
            mc.player.prevY = lastPlayerPosition.y;
            mc.player.prevZ = lastPlayerPosition.z;
        }
        playerStateSaved = false;
        lastPlayerPosition = null;
    }

    /**
     * Sends a formatted chat message.
     */
    public static void sendChatMessage(String message) {
        mc.inGameHud.getChatHud().addMessage(Text.of("[AegisClient] " + message));
    }

    /**
     * Returns the current state from the supplier or the tick counter.
     */
    public static int getStateOrCounter() {
        if (stateSupplier != null) {
            return stateSupplier.get() ? 1 : 0;
        }
        return ticksCounter;
    }
}
