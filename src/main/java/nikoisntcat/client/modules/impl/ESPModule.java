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
   public final BooleanSetting field1851;
   public final BooleanSetting field1852;
   public final BooleanSetting field1853;
   public final BooleanSetting field1854;
   private final Set field1855;
   private final Set field1856;
   public final BooleanSetting field1857;
   public final BooleanSetting field1858;
   public final ModeSetting field1859 = new ModeSetting("Mode", "Glow", List.of("Glow"));
   public final BooleanSetting field1860;
   static Object field1861;

   private void method1342() {
      if (mc.world != null) {
         for (Entity var2 : mc.world.getEntities()) {
            if (this.field1856.contains(var2.getUuid()) && var2.isGlowing()) {
               var2.setGlowing(false);
            }
         }
      }

      this.field1856.clear();
      this.field1855.clear();
   }

   @Override
   public void onRender3D(Render3DEvent event) {
      if ("Glow".equals(this.field1859.getValue())) {
         if (mc.world != null && mc.player != null) {
            this.field1855.clear();

            for (Entity var3 : mc.world.getEntities()) {
               if (var3 instanceof LivingEntity && method1344(this, (LivingEntity)var3)) {
                  if (!var3.isGlowing()) {
                     var3.setGlowing(true);
                     this.field1856.add(var3.getUuid());
                  }

                  this.field1855.add(var3.getUuid());
               }
            }

            for (Entity var6 : mc.world.getEntities()) {
               UUID var4 = var6.getUuid();
               if (this.field1856.contains(var4) && !this.field1855.contains(var4)) {
                  if (var6.isGlowing()) {
                     var6.setGlowing(false);
                  }

                  this.field1856.remove(var4);
               }
            }
         }
      }
   }

   public static boolean method1344(ESPModule esp, LivingEntity e) {
      if (esp == null || !esp.isEnabled() || !"Glow".equals(esp.field1859.getValue())) {
         return false;
      } else if (Class224.nullCheck()) {
         return false;
      } else if (e.getId() == mc.player.getId()) {
         return false;
      } else {
         boolean var2;
         if (esp.field1858.getValue()) {
            var2 = Class224.method1448(e);
         } else {
            boolean var3 = e instanceof PlayerEntity;
            boolean var4 = e instanceof VillagerEntity;
            boolean var5 = e instanceof AnimalEntity;
            boolean var6 = e instanceof Monster;
            var2 = var3 && esp.field1854.getValue()
               || var6 && esp.field1853.getValue()
               || var5 && esp.field1857.getValue()
               || var4 && esp.field1852.getValue();
         }

         if (!var2) {
            return false;
         } else {
            return esp.field1851.getValue() && TeamsModule.method1375(e)
               ? false
               : esp.field1860.getValue() || AntiBotModule.instance == null || !AntiBotModule.instance.method1380(e);
         }
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.method1342();
   }

   public ESPModule() {
      super("ESP", 0, false, Category.RENDER);
      this.field1858 = new BooleanSetting("UseTargets", true);
      this.field1854 = new BooleanSetting("Players", true, v -> !this.field1858.getValue());
      this.field1853 = new BooleanSetting("Mobs", true, v -> !this.field1858.getValue());
      this.field1857 = new BooleanSetting("Animals", false, v -> !this.field1858.getValue());
      this.field1852 = new BooleanSetting("Villagers", false, v -> !this.field1858.getValue());
      this.field1851 = new BooleanSetting("ExcludeTeammates", false);
      this.field1860 = new BooleanSetting("IgnoreAntiBot", true);
      this.field1856 = new HashSet();
      this.field1855 = new HashSet();
   }
}
