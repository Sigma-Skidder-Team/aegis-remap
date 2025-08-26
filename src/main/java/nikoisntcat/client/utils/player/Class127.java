package nikoisntcat.client.utils.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import nikoisntcat.client.utils.math.Class240;

public enum Class127 {
   field1475(Items.SNOWBALL, 0.0F, 1.875F, 0.0F),
   field1480(Items.ENDER_PEARL, 0.0F, 1.875F, 0.0F),
   field1481(Items.TRIDENT, 0.0F, 2.5F, 0.0F),
   field1483(Items.EXPERIENCE_BOTTLE, 0.0F, 0.6F, 0.0F),
   field1484(Items.SPLASH_POTION, 0.0F, 0.5F, 0.0F),
   field1486(Items.BOW, 0.0F, 3.0F, 0.0F),
   field1494(Items.EGG, 0.0F, 1.875F, 0.0F);

   public float field1474;
   public HitResult field1476;
   private final float field1477;
   public float field1479;
   public double field1482;
   private final float field1485;
   public float field1487;
   public double field1488;
   public Entity field1489;
   private final Item field1490;
   static final boolean field1491;
   private final float field1492;
   public double field1493;
   private MinecraftClient field1495 = MinecraftClient.getInstance();
   static Object field1496;

   public float method1010() {
      if (!this.field1490.equals(Items.BOW)) {
         return this.field1492;
      } else {
         return !(this.field1492 * BowItem.getPullProgress(this.field1495.player.getItemUseTime()) > 0.0F)
            ? BowItem.getPullProgress(20)
            : BowItem.getPullProgress(this.field1495.player.getItemUseTime());
      }
   }

   public float method1011() {
      return this.field1477;
   }

   static {
      field1491 = !Class127.class.desiredAssertionStatus();
   }

   public static Class127 method1012(Item item) {
      for (Class127 var4 : values()) {
         if (var4.method1014().equals(item)) {
            return var4;
         }
      }

      return null;
   }

   private Class127(Item item, float posX, float posY, float posZ) {
      this.field1490 = item;
      this.field1485 = posX;
      this.field1492 = posY;
      this.field1477 = posZ;
   }

   public float method1013() {
      return this.field1485;
   }

   public Item method1014() {
      return this.field1490;
   }

   public List method1016(float renderPartialTicks) {
      ArrayList var2 = new ArrayList();
      if (!field1491 && this.field1495.player == null) {
         throw new AssertionError();
      } else {
         float var3 = (float)Math.toRadians((double)this.field1495.player.getYaw());
         float var4 = (float)Math.toRadians((double)this.field1495.player.getPitch());
         double var5 = this.field1495.player.prevX + (this.field1495.player.getX() - this.field1495.player.prevX) * (double)renderPartialTicks;
         double var7 = this.field1495.player.prevY + (this.field1495.player.getY() - this.field1495.player.prevY) * (double)renderPartialTicks;
         double var9 = this.field1495.player.prevZ + (this.field1495.player.getZ() - this.field1495.player.prevZ) * (double)renderPartialTicks;
         this.field1488 = var5;
         this.field1493 = var7 + (double)this.field1495.player.getEyeHeight(this.field1495.player.getPose()) - 0.1F;
         this.field1482 = var9;
         float var11 = Math.min(20.0F, (float)(72000 - this.field1495.player.getItemUseTime()) + renderPartialTicks) / 20.0F;
         this.field1474 = -MathHelper.sin(var3) * MathHelper.cos(var4) * this.field1492 * var11;
         this.field1479 = -MathHelper.sin(var4) * this.field1492 * var11;
         this.field1487 = MathHelper.cos(var3) * MathHelper.cos(var4) * this.field1492 * var11;
         this.field1476 = null;
         this.field1489 = null;
         var2.add(new Class240(this.field1488, this.field1493, this.field1482));

         while (this.field1476 == null && this.field1489 == null && this.field1493 > 0.0) {
            Vec3d var12 = new Vec3d(this.field1488, this.field1493, this.field1482);
            Vec3d var13 = new Vec3d(this.field1488 + (double)this.field1474, this.field1493 + (double)this.field1479, this.field1482 + (double)this.field1487);
            float var14 = (float)(this.field1490 instanceof BowItem ? 0.3 : 0.3);
            double var15 = (double)var14;
            Box var17 = new Box(
               Math.min(var12.x, var13.x) - var15,
               Math.min(var12.y, var13.y) - var15,
               Math.min(var12.z, var13.z) - var15,
               Math.max(var12.x, var13.x) + var15,
               Math.max(var12.y, var13.y) + var15,
               Math.max(var12.z, var13.z) + var15
            );
            if (!field1491 && this.field1495.world == null) {
               throw new AssertionError();
            }

            List<Entity> var18 = this.field1495
               .world
               .getOtherEntities(this.field1495.player, var17, EntityPredicates.EXCEPT_SPECTATOR.and(new Class297(this, var14, var12, var13)));
            Entity var19 = null;
            double var20 = Double.MAX_VALUE;
            Vec3d var22 = null;

            for (Entity var24 : var18) {
               Box var25 = var24.getBoundingBox().expand((double)var14);
               Optional var26 = var25.raycast(var12, var13);
               if (var26.isPresent()) {
                  double var27 = var12.squaredDistanceTo((Vec3d)var26.get());
                  if (var27 < var20) {
                     var20 = var27;
                     var19 = var24;
                     var22 = (Vec3d)var26.get();
                  }
               }
            }

            if (var19 != null) {
               this.field1489 = var19;
               this.field1488 = var22.x;
               this.field1493 = var22.y;
               this.field1482 = var22.z;
               var2.add(new Class240(this.field1488, this.field1493, this.field1482));
               break;
            }

            BlockHitResult var29 = this.field1495
               .world
               .raycast(new RaycastContext(var12, var13, ShapeType.COLLIDER, FluidHandling.NONE, this.field1495.player));
            if (var29 != null && var29.getType() != Type.MISS) {
               this.field1476 = var29;
               this.field1488 = this.field1476.getPos().x;
               this.field1493 = this.field1476.getPos().y;
               this.field1482 = this.field1476.getPos().z;
               var2.add(new Class240(this.field1488, this.field1493, this.field1482));
               break;
            }

            this.field1488 = this.field1488 + (double)this.field1474;
            this.field1493 = this.field1493 + (double)this.field1479;
            this.field1482 = this.field1482 + (double)this.field1487;
            var2.add(new Class240(this.field1488, this.field1493, this.field1482));
            this.field1474 *= 0.99F;
            this.field1479 *= 0.99F;
            this.field1487 *= 0.99F;
            this.field1479 -= 0.05F;
         }

         return var2;
      }
   }
}
