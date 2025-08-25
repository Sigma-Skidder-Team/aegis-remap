package nikoisntcat.client.modules.impl;

import java.util.List;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.ModeSetting;

public class ProjectileTrailModule extends Module {
   public static ModeSetting effect;

   @Override
   public void onTick() {
      if (mc.player != null && mc.world != null) {
         Box var1 = new Box(mc.player.getPos().add(-150.0, -150.0, -150.0), mc.player.getPos().add(150.0, 150.0, 150.0));

         for (ProjectileEntity var3 : mc.world.getEntitiesByClass(ProjectileEntity.class, var1, entity -> true)) {
            if (var3.getVelocity().lengthSquared() > 0.01
               && !var3.isOnGround()
               && (
                  !mc.world.getBlockState(var3.getBlockPos()).isSolidBlock(mc.world, var3.getBlockPos())
                     || var3.getOwner() == null
                     || var3.getOwner().getId() == mc.player.getId()
               )) {
               ParticleEffect var4 = this.method1263();
               if (var4 != null) {
                  mc.world.addParticle(var4, var3.getX(), var3.getY(), var3.getZ(), 0.0, 0.0, 0.0);
               }
            }
         }
      }
   }

    static {
      effect = new ModeSetting(
         "Projectile Effect", "Hearts", List.of("BlackSmoke", "Fire", "GreenStar", "Hearts", "Magic", "MusicNote", "Slime", "Spark", "Swirl", "WhiteSmoke")
      );
   }

   public ProjectileTrailModule() {
      super("ProjectileTrail", 0, false, Category.RENDER);
   }

   private ParticleEffect method1263() {
      String mode = effect.getValue();
       return switch (mode) {
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
}
