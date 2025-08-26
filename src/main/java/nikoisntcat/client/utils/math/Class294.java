package nikoisntcat.client.utils.math;

public class Class294 {
   public float field2272;
   public float field2273;
   public float field2274;
   public float field2275;

   public float method1866(float by) {
      if (by != this.field2274) {
         this.field2274 = this.field2274 + (by - this.field2274) * this.field2272;
      }

      return this.field2274;
   }

   public float method1867(float by) {
      if (by != this.field2275) {
         this.field2275 = this.field2275 + (by - this.field2275) * this.field2272;
      }

      return this.field2275;
   }

   public float method1868(float by) {
      if (by != this.field2273) {
         this.field2273 = this.field2273 + (by - this.field2273) * this.field2272;
      }

      return this.field2273;
   }

   public Class294(float x, float y, float z) {
      this.field2274 = x;
      this.field2275 = y;
      this.field2273 = z;
      this.field2272 = 0.1F;
   }

   public Class294(float x, float y, float z, float weight) {
      this(x, y, z);
      this.field2272 = weight;
   }
}
