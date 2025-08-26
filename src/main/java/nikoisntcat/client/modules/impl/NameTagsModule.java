package nikoisntcat.client.modules.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.events.impl.Render3DEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.screens.Class276;
import nikoisntcat.client.utils.Class224;
import nikoisntcat.client.utils.Class231;
import nikoisntcat.client.utils.math.Tween;
import nikoisntcat.client.utils.math.TweenType;
import nikoisntcat.client.utils.render.RenderUtil;
import org.joml.Vector2f;

public class NameTagsModule extends Module {
   private final List<Entity> field1681;
   private final Map field1682 = new HashMap<>();
   private final Map field1683 = new HashMap<>();
   static Object field1684;

   @Override
   public void onRender3D(Render3DEvent render3DEvent) {
      if (!Class224.nullCheck()) {
         float var2 = render3DEvent.method1423();
         Vec3d var3 = RenderUtil.method1570(mc.player, (double)var2);

         for (Entity var6 : mc.world.getEntities()) {
            if (var6 instanceof LivingEntity && Class224.method1448((LivingEntity)var6)) {
               try {
                  LivingEntity var7 = (LivingEntity)var6;
                  float var9 = -1.0F;
                  LivingEntity var10 = Class165.field1611;
                  LivingEntity var11 = Class165.field1606;
                  Vec3d var19;
                  if (var6 == var10) {
                     Tween var12 = (Tween)this.field1683.computeIfAbsent(var6, e -> new Class276(1500, 1.0));
                     if (var12.method1760(TweenType.field1465)) {
                        var12.method1769(TweenType.field1464);
                     }

                     var9 = var12.method1770().floatValue();
                     if (var11 != null && var11 != var6) {
                        var12.timer.setCurrentMs(0L);
                        this.field1682.remove(var6);
                        var9 = 1.0F;
                     }

                     Vec3d var13 = RenderUtil.method1569(
                        new Vec3d(mc.player.prevX, mc.player.prevY, mc.player.prevZ), mc.player.getPos().add(0.0, 0.0, 0.0), (double)var2
                     );
                     float var14 = mc.player.getFovMultiplier(true, ((Double)mc.options.getFovEffectScale().getValue()).floatValue());
                     Vec3d var16 = Class231.method1526(new Vector2f(mc.player.getYaw(var2), mc.player.getPitch(var2) - 25.0F));
                     Vec3d var17 = var13.add(var16.multiply((double)(3.0F / var14)));
                     Vec3d var18 = (Vec3d)this.field1682.getOrDefault(var6, var17);
                     var19 = RenderUtil.method1569(var18, var17, var12.method1770());
                     this.field1682.put(var6, var19);
                     if (var6 instanceof AbstractClientPlayerEntity) {
                        this.field1681.add((AbstractClientPlayerEntity)var6);
                     }
                  } else {
                     float var23 = 1.0F;
                     Tween var24 = (Tween)this.field1683.get(var6);
                     if (var24 != null) {
                        if (var24.method1767() == TweenType.field1464) {
                           var24.method1769(TweenType.field1465);
                           var24.update();
                        }

                        var23 = 1.0F - var24.method1770().floatValue();
                     } else if (var10 == null) {
                        Class276 var25 = new Class276(1000, 1.0);
                        var25.method1769(TweenType.field1465);
                        this.field1683.put(var6, var25);
                        var23 = 1.0F - var25.method1770().floatValue();
                     }

                     Vec3d var26 = RenderUtil.method1569(
                        new Vec3d(var6.prevX, var6.prevY + (double)var7.getStandingEyeHeight(), var6.prevZ),
                        var6.getPos().add(0.0, (double)var7.getStandingEyeHeight(), 0.0),
                        (double)var2
                     );
                     Vec3d var15 = (Vec3d)this.field1682.getOrDefault(var6, var26);
                     var19 = RenderUtil.method1569(var15, var26, (double)var23);
                     this.field1682.put(var6, var19);
                     if (var6 instanceof AbstractClientPlayerEntity && this.field1681.contains(var6)) {
                        var9 = 1.0F - var23;
                        if (var9 <= 0.001F) {
                           this.field1681.remove(var6);
                        }
                     }
                  }

                  double var20 = var3.squaredDistanceTo(var19);
                  float var8 = (float)Math.max(2.0, Math.sqrt(var20 / 7.5));
                  RenderUtil.method1575(render3DEvent.method1427(), var19.x, var19.y, var19.z, var7, var8, null, var9);
               } catch (Exception var22) {
                  var22.printStackTrace();
               }
            }
         }
      }
   }

   public NameTagsModule() {
      super("NameTags", 0, Category.RENDER);
      this.field1681 = new ArrayList<>();
   }

   @Override
   public void onTick() {
   }
}
