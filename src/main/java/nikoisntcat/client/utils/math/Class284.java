package nikoisntcat.client.utils.math;

import java.util.HashSet;
import java.util.Set;

public class Class284 {
   public int field2223;
   public Float field2224;
   public Class128 field2225;
   public int field2226;
   public Class284 field2227;
   public int field2228 = 0;
   public Set field2229;
   public Float field2230;
   public Class240 field2231;
   public int field2232;
   public Set field2233;
   private static String[] field2234;
   public Class130 field2235;
   public Set field2236;

   public Class284(Class240 var1, int var2, Class130 var3, Class128 var4) {
      this.field2224 = 0.0F;
      this.field2226 = 1;
      this.field2223 = 2500;
      this.field2235 = Class130.field1518;
      this.field2225 = Class128.field1505;
      this.field2236 = new HashSet();
      this.field2229 = new HashSet();
      this.field2233 = new HashSet();
      this.field2231 = var1;
      this.field2226 += var2;
      this.field2235 = var3;
      this.field2225 = var4;
   }

   public Class284 method1809(Class240 tracePoint, boolean isParent) {
      long var3 = tracePoint.method1616().asLong();
      if (isParent) {
         long var5 = tracePoint.method1616().up().asLong();
         this.field2229.add(var5);
         this.field2233.remove(var5);
      }

      this.field2229.add(var3);
      this.field2233.remove(var3);
      return this;
   }
}
