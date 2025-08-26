package nikoisntcat.client.modules.impl.misc;

import java.util.List;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.ModeSetting;

public class ProjectileTrailModule extends Module {

    public static ModeSetting particleEffect;

    public ProjectileTrailModule() {
        super("ProjectileTrail", 0, false, Category.MISC);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;

        Box detectionBox = new Box(mc.player.getPos().add(-150.0, -150.0, -150.0),
                mc.player.getPos().add(150.0, 150.0, 150.0));

        for (ProjectileEntity projectile : mc.world.getEntitiesByClass(ProjectileEntity.class, detectionBox, e -> true)) {
            if (projectile.getVelocity().lengthSquared() > 0.01
                    && !projectile.isOnGround()
                    && (
                    !mc.world.getBlockState(projectile.getBlockPos()).isSolidBlock(mc.world, projectile.getBlockPos())
                            || projectile.getOwner() == null
                            || projectile.getOwner().getId() == mc.player.getId()
            )) {

                ParticleEffect particle = getParticleEffect();
                if (particle != null) {
                    mc.world.addParticle(particle, projectile.getX(), projectile.getY(), projectile.getZ(), 0.0, 0.0, 0.0);
                }
            }
        }
    }

    private ParticleEffect getParticleEffect() {
        return switch (particleEffect.getValue()) {
            case "BlackSmoke" -> ParticleTypes.SMOKE;
            case "Fire" -> ParticleTypes.FLAME;
            case "GreenStar" -> ParticleTypes.HAPPY_VILLAGER;
            case "Hearts" -> ParticleTypes.HEART;
            case "Magic" -> ParticleTypes.WITCH;
            case "MusicNote" -> ParticleTypes.NOTE;
            case "Slime" -> ParticleTypes.ITEM_SLIME;
            case "Spark" -> ParticleTypes.FIREWORK;
            case "WhiteSmoke" -> ParticleTypes.WHITE_SMOKE;
            default -> null;
        };
    }

    static {
        particleEffect = new ModeSetting(
                "Projectile Effect",
                "Hearts",
                List.of("BlackSmoke", "Fire", "GreenStar", "Hearts", "Magic", "MusicNote", "Slime", "Spark", "Swirl", "WhiteSmoke")
        );
    }
}
