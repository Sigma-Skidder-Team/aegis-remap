package nikoisntcat.client.modules.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import nikoisntcat.client.events.impl.Render3DEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.utils.Class224;

public class ESPModule extends Module {

    // Settings
    public final BooleanSetting excludeTeammates;
    public final BooleanSetting highlightVillagers;
    public final BooleanSetting highlightMobs;
    public final BooleanSetting highlightPlayers;
    public final BooleanSetting highlightAnimals;
    public final BooleanSetting useTargets;
    public final BooleanSetting ignoreAntiBot;
    public final ModeSetting mode = new ModeSetting("Mode", "Glow", List.of("Glow"));

    private final Set<UUID> activeEntities;
    private final Set<UUID> glowingEntities;

    static Object unusedField;

    public ESPModule() {
        super("ESP", 0, false, Category.RENDER);

        this.useTargets = new BooleanSetting("UseTargets", true);
        this.highlightPlayers = new BooleanSetting("Players", true, v -> !this.useTargets.getValue());
        this.highlightMobs = new BooleanSetting("Mobs", true, v -> !this.useTargets.getValue());
        this.highlightAnimals = new BooleanSetting("Animals", false, v -> !this.useTargets.getValue());
        this.highlightVillagers = new BooleanSetting("Villagers", false, v -> !this.useTargets.getValue());
        this.excludeTeammates = new BooleanSetting("ExcludeTeammates", false);
        this.ignoreAntiBot = new BooleanSetting("IgnoreAntiBot", true);

        this.glowingEntities = new HashSet<>();
        this.activeEntities = new HashSet<>();
    }

    private void clearGlowingEntities() {
        if (mc.world != null) {
            for (Entity entity : mc.world.getEntities()) {
                if (this.glowingEntities.contains(entity.getUuid()) && entity.isGlowing()) {
                    entity.setGlowing(false);
                }
            }
        }
        this.glowingEntities.clear();
        this.activeEntities.clear();
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if ("Glow".equals(this.mode.getValue())) {
            if (mc.world != null && mc.player != null) {
                this.activeEntities.clear();

                for (Entity entity : mc.world.getEntities()) {
                    if (entity instanceof LivingEntity && shouldHighlight(this, (LivingEntity) entity)) {
                        if (!entity.isGlowing()) {
                            entity.setGlowing(true);
                            this.glowingEntities.add(entity.getUuid());
                        }
                        this.activeEntities.add(entity.getUuid());
                    }
                }

                for (Entity entity : mc.world.getEntities()) {
                    UUID uuid = entity.getUuid();
                    if (this.glowingEntities.contains(uuid) && !this.activeEntities.contains(uuid)) {
                        if (entity.isGlowing()) {
                            entity.setGlowing(false);
                        }
                        this.glowingEntities.remove(uuid);
                    }
                }
            }
        }
    }

    public static boolean shouldHighlight(ESPModule esp, LivingEntity entity) {
        if (esp == null || !esp.isEnabled() || !"Glow".equals(esp.mode.getValue())) {
            return false;
        } else if (Class224.nullCheck()) {
            return false;
        } else if (entity.getId() == mc.player.getId()) {
            return false;
        }

        boolean highlight;

        if (esp.useTargets.getValue()) {
            highlight = Class224.method1448(entity);
        } else {
            boolean isPlayer = entity instanceof PlayerEntity;
            boolean isVillager = entity instanceof VillagerEntity;
            boolean isAnimal = entity instanceof AnimalEntity;
            boolean isMonster = entity instanceof Monster;

            highlight =
                    (isPlayer && esp.highlightPlayers.getValue()) ||
                            (isMonster && esp.highlightMobs.getValue()) ||
                            (isAnimal && esp.highlightAnimals.getValue()) ||
                            (isVillager && esp.highlightVillagers.getValue());
        }

        if (!highlight) {
            return false;
        }

        if (esp.excludeTeammates.getValue() && TeamsModule.method1375(entity)) {
            return false;
        }

        return esp.ignoreAntiBot.getValue()
                || AntiBotModule.instance == null
                || !AntiBotModule.instance.method1380(entity);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.clearGlowingEntities();
    }
}
