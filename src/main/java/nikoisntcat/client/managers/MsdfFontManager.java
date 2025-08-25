package nikoisntcat.client.managers;

import dev.sxmurxy.mre.msdf.MsdfFont;

public class MsdfFontManager {
   public static final MsdfFont nourd;
   public static final MsdfFont biko;
   public static final MsdfFont medium;

   static {
      biko = MsdfFont.builder().atlas("biko").data("biko").build();
      medium = MsdfFont.builder().atlas("medium").data("medium").build();
      nourd = MsdfFont.builder().atlas("nourd").data("nourd").build();
   }
}
