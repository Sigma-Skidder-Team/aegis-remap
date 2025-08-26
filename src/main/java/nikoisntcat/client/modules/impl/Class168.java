package nikoisntcat.client.modules.impl;

import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.NumberSetting;

public class Class168 extends Module {
   public NumberSetting field1657;
   private boolean field1658 = false;
   public static boolean $skidonion$316698668;

   @Override
   public native void onMotion(MotionEvent motionEvent);

   public Class168() {
      super((String)method1246('\u0000'), 0, false, Category.MOVE);
      this.field1657 = new NumberSetting((String)method1246('\u0001'), 10.0, 10.0, 1.0);
   }

   @Override
   public native void onSendPacket(PacketSendEvent event);

   private static native Object method1246(char var0);
}
