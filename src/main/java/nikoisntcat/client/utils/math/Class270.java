package nikoisntcat.client.utils.math;

import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;

public class Class270 {
   public double field2160;
   public double field2161;
   public double field2162;

   public Class270 method1742(Class270 dir) {
      return new Class270(
         this.field2162 * dir.field2160 - this.field2160 * dir.field2162,
         this.field2160 * dir.field2161 - this.field2161 * dir.field2160,
         this.field2161 * dir.field2162 - this.field2162 * dir.field2161
      );
   }

   public Class270 method1743(double x, double y, double z) {
      return new Class270(this.field2161 + x, this.field2162 + y, this.field2160 + z);
   }

   public String toString() {
      return "[" + this.field2161 + ";" + this.field2162 + ";" + this.field2160 + "]";
   }

   public Class270(double x, double y, double z) {
      this.field2161 = x;
      this.field2162 = y;
      this.field2160 = z;
   }

   public Class270 method1744(Class270 var1) {
      return this.method1743(var1.method1747(), var1.method1751(), var1.method1752());
   }

   public double method1745(Class270 var1) {
      return Math.pow(var1.field2161 - this.field2161, 2.0) + Math.pow(var1.field2162 - this.field2162, 2.0) + Math.pow(var1.field2160 - this.field2160, 2.0);
   }

   public Class270(BlockPos blockPos) {
      this.field2161 = (double)blockPos.getX();
      this.field2162 = (double)blockPos.getY();
      this.field2160 = (double)blockPos.getZ();
   }

   public Class270 method1746(Class270 dir) {
      return this.method1743(-dir.method1747(), -dir.method1751(), -dir.method1752());
   }

   public double method1747() {
      return this.field2161;
   }

   public BlockPos method1748() {
      return new BlockPos((int)Math.floor(this.field2161), (int)Math.floor(this.field2162), (int)Math.floor(this.field2160));
   }

   public Vector3i method1749() {
      return new Vector3i((int)Math.floor(this.field2161), (int)Math.floor(this.field2162), (int)Math.floor(this.field2160));
   }

   public Class270 method1750() {
      double var1 = Math.sqrt(this.field2161 * this.field2161 + this.field2162 * this.field2162 + this.field2160 * this.field2160);
      return !(var1 < 1.0E-4) ? new Class270(this.field2161 / var1, this.field2162 / var1, this.field2160 / var1) : new Class270(0.0, 0.0, 0.0);
   }

   public double method1751() {
      return this.field2162;
   }

   public double method1752() {
      return this.field2160;
   }

   public Class270 method1753() {
      return new Class270(Math.floor(this.field2161), Math.floor(this.field2162), Math.floor(this.field2160));
   }
}
