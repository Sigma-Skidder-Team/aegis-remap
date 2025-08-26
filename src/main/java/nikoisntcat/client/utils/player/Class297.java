package nikoisntcat.client.utils.player;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Class297 implements Predicate {
   public final Class127 field2289;
   public final Vec3d field2290;
   public final float field2291;
   public final Vec3d field2292;

   public Class297(Class127 var1, float var2, Vec3d var3, Vec3d var4) {
      this.field2289 = var1;
      this.field2291 = var2;
      this.field2292 = var3;
      this.field2290 = var4;
   }

   public boolean method1882(Entity entity) {
      boolean var3 = entity.getBoundingBox()
         .expand((double)this.field2291, (double)this.field2291, (double)this.field2291)
         .intersects(this.field2292, this.field2290);
      return entity != null && entity.canBeHitByProjectile() && var3;
   }

    @Override
    public boolean apply(Object input) {
        return false;
    }
}
