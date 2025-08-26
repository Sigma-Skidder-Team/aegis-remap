package nikoisntcat.client.modules.impl;

import java.util.List;
import net.minecraft.network.packet.c2s.play.ClientTickEndC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.Class224;

public class NoFallModule extends Module {
   private boolean field1814;
   private double field1815;
   private boolean field1816;
   private boolean field1817;
   public NumberSetting field1818;
   public ModeSetting field1819 = new ModeSetting("Mode", "GroundSpoof", List.of("Grim", "GroundSpoof", "NoGround", "SemiGround"));
   private boolean field1820;
   private double field1821;
   static Object field1822;

   @Override
   public void onMotion(MotionEvent motionEvent) {
      if (motionEvent.method1399() == MotionEvent.Class123.PRE) {
         String var2 = this.field1819.getValue();
         switch (var2) {
            case "GroundSpoof":
               motionEvent.method1406(true);
               break;
            case "NoGround":
               motionEvent.method1406(false);
               break;
            case "SemiGround":
               this.field1815 = this.field1815 + ((double)mc.player.fallDistance - this.field1821);
               if (this.field1815 > this.field1818.getValue() && !motionEvent.method1412()) {
                  this.field1815 = (double)mc.player.fallDistance - this.field1821;
                  mc.getNetworkHandler().sendPacket(new OnGroundOnly(true, mc.player.horizontalCollision));
               }
               break;
            case "Grim":
               if (!this.field1814 && (double)mc.player.fallDistance > this.field1818.getValue() && !motionEvent.method1412()) {
                  this.field1814 = true;
                  this.field1817 = false;
                  this.field1816 = false;
               } else if (this.field1820 && mc.player.fallDistance > 0.0F) {
                  mc.options.jumpKey.setPressed(Class224.method1445(mc.options.jumpKey));
                  this.field1820 = false;
               }

               if (this.field1814 && motionEvent.method1412()) {
                  mc.options.jumpKey.setPressed(false);
                  motionEvent.method1406(false);
                  if (!this.field1816) {
                     mc.player
                        .networkHandler
                        .sendPacket(
                           new PositionAndOnGround(motionEvent.method1401() + 1000.0, motionEvent.method1403(), motionEvent.method1407(), false, false)
                        );
                     this.field1816 = true;
                  }
               }
         }
      }
   }

   @Override
   public void onReceivePacket(PacketReceiveEvent event) {
      if (this.field1814 && event.getPacket() instanceof PlayerPositionLookS2CPacket) {
         this.field1817 = true;
         mc.options.jumpKey.setPressed(false);
      }
   }

   @Override
   public void onSendPacket(PacketSendEvent event) {
      if (this.field1814 && this.field1816 && !this.field1817 && event.getPacket() instanceof PlayerMoveC2SPacket) {
         event.cancel();
      }
   }

   public NoFallModule() {
      super("NoFall", 0, Category.MOVE);
      this.field1818 = new NumberSetting("WorkDistance", 3.3, 5.0, 0.0);
      this.field1817 = false;
      this.field1814 = false;
      this.field1816 = false;
      this.field1820 = false;
   }

   @Override
   public void onTick() {
      if (Class224.nullCheck()) {
         this.field1817 = false;
         this.field1814 = false;
         this.field1816 = false;
      } else {
         if (mc.player.onGround) {
            this.field1815 = 0.0;
            this.field1821 = 0.0;
         } else {
            this.field1821 = (double)mc.player.fallDistance;
         }

         if (this.field1814) {
            mc.options.jumpKey.setPressed(false);
         }

         if (this.field1817 && this.field1814) {
            mc.options.jumpKey.setPressed(true);
            this.field1814 = false;
            this.field1820 = true;
            this.field1817 = false;
            mc.player.networkHandler.sendPacket(new OnGroundOnly(false, false));
            mc.player.networkHandler.sendPacket(new ClientTickEndC2SPacket());
            Class224.method1450(1);
         }
      }
   }
}
