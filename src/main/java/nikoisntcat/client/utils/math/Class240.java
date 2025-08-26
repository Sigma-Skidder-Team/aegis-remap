package nikoisntcat.client.utils.math;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import nikoisntcat.client.utils.Class228;

public class Class240 {
   private double field2059;
   public static MinecraftClient field2060 = MinecraftClient.getInstance();
   public Class284 field2061;
   private double field2062;
   private static Class270[] field2063 = new Class270[]{
      new Class270(1.0, 0.0, 0.0),
      new Class270(-1.0, 0.0, 0.0),
      new Class270(0.0, 0.0, 1.0),
      new Class270(0.0, 0.0, -1.0),
      new Class270(0.0, -1.0, 0.0),
      new Class270(0.0, 1.0, 0.0)
   };
   private double field2064;

   public double method1615(Class240 var1) {
      double var2 = var1.method1621() - this.method1621();
      double var4 = var1.method1637() - this.method1637();
      double var6 = var1.method1636() - this.method1636();
      return var2 * var2 + var4 * var4 + var6 * var6;
   }

   public BlockPos method1616() {
      return Class228.method1489(this.method1621(), this.method1637(), this.method1636());
   }

   public boolean method1617() {
      return this.method1628() && this.method1638(this.method1616().up(3));
   }

   public Class240 method1618(float var1, float var2, float var3) {
      return new Class240(this.method1621() + (double)var1, this.method1637() + (double)var2, this.method1636() + (double)var3, this.field2061);
   }

   public boolean method1619() {
      for (Class270 var4 : field2063) {
         BlockPos var5 = this.method1616();
         BlockState var6 = field2060.world
            .getBlockState(
               Class228.method1489(var4.field2161 + (double)var5.getX(), var4.field2162 + (double)var5.getY(), var4.field2160 + (double)var5.getZ())
            );
         if (var6.getBlock() == Blocks.WATER || var6.getBlock() == Blocks.LAVA) {
            return true;
         }
      }

      for (Class270 var10 : field2063) {
         BlockPos var11 = this.method1616();
         BlockState var12 = field2060.world
            .getBlockState(
               Class228.method1489(var10.field2161 + (double)var11.getX(), var10.field2162 + (double)var11.getY(), var10.field2160 + (double)var11.getZ())
            );
         if (var12.getBlock() == Blocks.WATER || var12.getBlock() == Blocks.LAVA) {
            return true;
         }
      }

      return field2060.world.getBlockState(this.method1616().up(2)).getBlock() instanceof FallingBlock
         && !this.field2061.field2229.contains(this.method1616().up(2).asLong());
   }

   public void method1620(float z) {
      this.field2059 = (double)z;
   }

   public double method1621() {
      return this.field2062;
   }

   public boolean method1622() {
      return this.method1632() && this.method1624(this.method1616().down());
   }

   public float method1623(Entity e) {
      double var2 = (double)e.getBlockX() - this.method1621();
      double var4 = (double)e.getBlockY() - this.method1637();
      double var6 = (double)e.getBlockZ() - this.method1636();
      return MathHelper.sqrt((float)(var2 * var2 + var4 * var4 + var6 * var6));
   }

   public boolean method1624(BlockPos var1) {
      if (!this.field2061.field2229.contains(this.method1616().asLong())) {
         VoxelShape var2 = field2060.world.getBlockState(var1).getCollisionShape(field2060.world, var1);
         if (var2.isEmpty()) {
            return false;
         } else {
            Box var3 = var2.getBoundingBox();
            return var3.getLengthY() >= 0.9 && var3.getLengthY() <= 1.0;
         }
      } else {
         return false;
      }
   }

   public Vec3d method1625() {
      return new Vec3d(this.field2062, this.field2064, this.field2059);
   }

   public Vec3d method1626() {
      return new Vec3d(this.method1621(), this.method1637(), this.method1636()).add(0.5, 0.0, 0.5);
   }

   public boolean method1627() {
      return this.method1619()
         || field2060.world.getBlockState(this.method1616()).getBlock() == Blocks.BEDROCK
         || field2060.world.getBlockState(this.method1616().up()).getBlock() == Blocks.BEDROCK;
   }

   public boolean method1628() {
      return this.method1632() && this.method1638(this.method1616().up(2));
   }

   public void method1629(double x, double y, double z) {
      this.field2062 = x;
      this.field2064 = y;
      this.field2059 = z;
   }

   public static boolean method1630(BlockPos var0) {
      for (Class270 var4 : field2063) {
         if (field2060.world
            .getBlockState(
               Class228.method1489(var4.field2161 + (double)var0.getX(), var4.field2162 + (double)var0.getY(), var4.field2160 + (double)var0.getZ())
            )
            .isAir()) {
            return false;
         }
      }

      return true;
   }

   public Class240(double var1, double var3, double var5, Class284 var7) {
      this.method1629(var1, var3, var5);
      this.field2061 = var7;
   }

   public float method1631(Class240 var1) {
      double var2 = var1.method1621() - this.method1621();
      double var4 = var1.method1637() - this.method1637();
      double var6 = var1.method1636() - this.method1636();
      return MathHelper.sqrt((float)(var2 * var2 + var4 * var4 + var6 * var6));
   }

   public boolean method1632() {
      if (field2060.world.getBlockState(this.method1616()).getBlock() == Blocks.LAVA) {
         return false;
      } else if (!field2060.world.getBlockState(this.method1616()).getFluidState().isEmpty()) {
         return false;
      } else {
         return field2060.world.getBlockState(this.method1616().down()).getBlock() instanceof FenceBlock
            ? false
            : this.method1638(this.method1616()) && this.method1638(this.method1616().up());
      }
   }

   public void method1633(float x) {
      this.field2062 = (double)x;
   }

   public static boolean method1634(BlockPos var0) {
      for (Class270 var4 : field2063) {
         BlockState var5 = field2060.world
            .getBlockState(
               Class228.method1489(var4.field2161 + (double)var0.getX(), var4.field2162 + (double)var0.getY(), var4.field2160 + (double)var0.getZ())
            );
         if (var5.getBlock() == Blocks.WATER || var5.getBlock() == Blocks.LAVA) {
            return true;
         }
      }

      return false;
   }

   public Class240 method1635(double var1, double var3, double var5) {
      return new Class240(this.field2062 + var1, this.field2064 + var3, this.field2059 + var5);
   }

   public double method1636() {
      return this.field2059;
   }

   public double method1637() {
      return this.field2064;
   }

   public boolean method1638(BlockPos var1) {
      return !this.field2061.field2229.contains(var1.asLong()) ? field2060.world.getBlockState(var1).getCollisionShape(field2060.world, var1).isEmpty() : true;
   }

   public double method1639(Class240 var1) {
      double var2 = var1.method1621() - this.method1621();
      double var4 = var1.method1637() - this.method1637();
      double var6 = var1.method1636() - this.method1636();
      return Math.abs(var2) + Math.abs(var6) + Math.abs(var4);
   }

   public Class240(double var1, double var3, double var5) {
      this.method1629(var1, var3, var5);
   }

   public void method1640(float y) {
      this.field2064 = (double)y;
   }

   public Class240(BlockPos var1) {
      this.method1629((double)var1.getX(), (double)var1.getY(), (double)var1.getZ());
   }

   public boolean method1641() {
      return this.method1624(this.method1616());
   }

   public double method1642(Vec3d vec) {
      double var2 = vec.x - this.method1621() - 0.5;
      double var4 = vec.z - this.method1636() - 0.5;
      return (double)MathHelper.sqrt((float)(var2 * var2 + var4 * var4));
   }
}
