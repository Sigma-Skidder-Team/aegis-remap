package nikoisntcat.client.modules.impl;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.utils.Class224;

public class InventoryMoveModule extends Module {
   private boolean field1823;
   private final KeyBinding[] field1824;
   private boolean field1825;
   public BooleanSetting field1826 = new BooleanSetting("Hypixel", true);
   private boolean field1827;
   static Object field1828;

   @Override
   public void onSendPacket(PacketSendEvent event) {
      if (this.field1826.getValue() && mc.currentScreen == null) {
         Packet var2 = event.getPacket();
         if (var2 instanceof ClickSlotC2SPacket) {
            this.field1825 = true;
         }

         var2 = event.getPacket();
         if (var2 instanceof CloseHandledScreenC2SPacket) {
            this.field1825 = false;
         }
      }
   }

   @Override
   public void onMotion(MotionEvent motionEvent) {
      if (this.field1825 && motionEvent.method1399() == MotionEvent.Class123.PRE && mc.currentScreen == null) {
         mc.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(0));
      }
   }

   public InventoryMoveModule() {
      super("InventoryMove", 0, Category.MOVE);
      this.field1823 = false;
      this.field1827 = false;
      this.field1825 = false;
      this.field1824 = new KeyBinding[]{mc.options.forwardKey, mc.options.rightKey, mc.options.leftKey, mc.options.jumpKey, mc.options.backKey};
   }

   @Override
   public void onTick() {
      if (!this.field1826.getValue()) {
         if (mc.currentScreen != null) {
            for (KeyBinding var4 : this.field1824) {
               var4.setPressed(Class224.method1445(var4));
            }
         }
      }
   }
}
