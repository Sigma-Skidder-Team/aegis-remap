package nikoisntcat.client.utils.math;

import java.awt.Color;

public class ColorUtil {
   public static Color method2007(Color color1, Color color2, double offset) {
      if (offset > 1.0) {
         double var4 = offset % 1.0;
         offset = (int)offset % 2 == 0 ? var4 : 1.0 - var4;
      }

      double var10 = 1.0 - offset;
      int var6 = (int)((double)color1.getRed() * var10 + (double)color2.getRed() * offset);
      int var7 = (int)((double)color1.getGreen() * var10 + (double)color2.getGreen() * offset);
      int var8 = (int)((double)color1.getBlue() * var10 + (double)color2.getBlue() * offset);
      int var9 = (int)((double)color1.getAlpha() * var10 + (double)color2.getAlpha() * offset);
      return new Color(var6, var7, var8, var9);
   }

   public static Color method2008(Color color1, Color color2, double offset, int alpha) {
      if (offset > 1.0) {
         double var5 = offset % 1.0;
         offset = (int)offset % 2 == 0 ? var5 : 1.0 - var5;
      }

      double var10 = 1.0 - offset;
      int var7 = (int)((double)color1.getRed() * var10 + (double)color2.getRed() * offset);
      int var8 = (int)((double)color1.getGreen() * var10 + (double)color2.getGreen() * offset);
      int var9 = (int)((double)color1.getBlue() * var10 + (double)color2.getBlue() * offset);
      return new Color(var7, var8, var9, alpha);
   }
}
