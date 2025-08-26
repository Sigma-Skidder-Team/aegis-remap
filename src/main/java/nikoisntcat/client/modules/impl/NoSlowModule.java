package nikoisntcat.client.modules.impl;

import java.util.List;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.play.SetPlayerInventoryS2CPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.PacketSendEvent;
import nikoisntcat.client.events.impl.SlowdownEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.utils.Class207;
import nikoisntcat.client.utils.Class224;
import nikoisntcat.client.utils.MovementUtil;

public class NoSlowModule extends Module {
   public BooleanSetting field1776 = new BooleanSetting("Sword", false);
   private boolean field1777;
   public ModeSetting field1778;
   private boolean field1779;
   private boolean field1780;
   public BooleanSetting field1781;
   public BooleanSetting field1782;
   private boolean field1783;
   public ModeSetting field1784;
   public ModeSetting field1785 = new ModeSetting("SwordPacketMode", "None", List.of("None"));
   public BooleanSetting field1786;
   private boolean field1787;
   private int field1788;
   static Object field1789;

   @Override
   public void onSlow(SlowdownEvent slowDownEvent) {
      ItemStack var2 = mc.player.getInventory().getMainHandStack();
      Item var3 = var2.getItem();
      if (this.field1781.getValue() && mc.options.useKey.isPressed() && (var2.getUseAction() == UseAction.EAT || var2.getUseAction() == UseAction.DRINK)) {
         slowDownEvent.setSlowdown(true);
         if (!mc.player.isUsingItem() || mc.player.getItemUseTime() < 2) {
            this.field1787 = false;
            this.field1780 = false;
            mc.player.setSprinting(false);
            mc.options.sprintKey.setPressed(false);
            return;
         }
      }

      if (slowDownEvent.getSlowdown()) {
         this.field1787 = true;
         if (var3 instanceof SwordItem && this.field1776.getValue()) {
            slowDownEvent.setSlowdown(false);
         } else if (var3 instanceof BowItem && this.field1786.getValue()) {
            slowDownEvent.setSlowdown(false);
         } else if (this.field1782.getValue() && (var2.getUseAction() == UseAction.EAT || var2.getUseAction() == UseAction.DRINK)) {
            String var4 = this.field1784.getValue();
            switch (var4) {
               case "Blink":
                  if (!this.field1783) {
                     mc.player
                        .networkHandler
                        .sendPacket(
                           new PlayerInteractItemC2SPacket(
                              Hand.MAIN_HAND, mc.world.getPendingUpdateManager().incrementSequence().getSequence(), mc.player.getYaw(), mc.player.getPitch()
                           )
                        );
                     this.field1788 = 0;
                     this.field1783 = true;
                  }

                  slowDownEvent.setSlowdown(false);
                  break;
               case "GrimTest":
                  if (!this.field1783) {
                     MovementUtil.isBlinking = true;
                     mc.player
                        .networkHandler
                        .sendPacket(
                           new PlayerInteractItemC2SPacket(
                              Hand.MAIN_HAND, mc.world.getPendingUpdateManager().incrementSequence().getSequence(), mc.player.getYaw(), mc.player.getPitch()
                           )
                        );
                     this.field1788 = 0;
                     this.field1783 = true;
                  } else {
                     slowDownEvent.setSlowdown(false);
                  }
                  break;
               case "Half":
                  if (!mc.player.onGround && new Class207().method1396(1, mc.player.getYaw(), true).field2178) {
                     this.field1777 = true;
                  } else if ((mc.player.age % 2 == 0 || this.field1777) && this.field1780) {
                     slowDownEvent.setSlowdown(false);
                     if (this.field1777) {
                        this.field1777 = false;
                     }
                  }
                  break;
               default:
                  slowDownEvent.setSlowdown(false);
            }
         }
      }
   }

   @Override
   public void onTick() {
      if (!Class224.nullCheck()) {
         this.field1788++;
         this.field1780 = this.field1787;
         this.field1787 = false;
      }
   }

   public NoSlowModule() {
      super("NoSlow", 0, Category.MOVE);
      this.field1782 = new BooleanSetting("Food", false);
      this.field1784 = new ModeSetting("FoodPacketMode", "None", List.of("None", "Blink", "Half", "GrimTest"));
      this.field1786 = new BooleanSetting("Bow", false);
      this.field1778 = new ModeSetting("BowPacketMode", "None", List.of("None"));
      this.field1781 = new BooleanSetting("Server1_8", false);
      this.field1783 = false;
      this.field1780 = false;
      this.field1787 = false;
      this.field1777 = false;
      this.field1788 = 0;
      this.field1779 = false;
   }

   @Override
   public void onMotion(MotionEvent motionEvent) {
      if (motionEvent.method1399() == MotionEvent.Class123.PRE) {
         if (this.field1783 && !AegisClient.field2315.method2044()) {
            AegisClient.field2315.method2051(this);
            mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
         }
      } else {
         if (this.field1784.getValue().equals("Blink") && this.field1788 > 33) {
            AegisClient.field2315.method2047();
            this.field1788 = 0;
            this.field1783 = false;
         }

         if (this.field1784.getValue().equals("GrimTest") && this.field1783) {
            if (this.field1788 > 6) {
                MovementUtil.isBlinking = false;
            }

            if (this.field1788 > 33) {
               if (this.field1779) {
                  AegisClient.field2315.method2046();
                  if (this.field1788 % 2 == 0) {
                     AegisClient.field2315.method2046();
                     Class224.method1450(1);
                     Class224.method1451("Release More. Now Left: " + AegisClient.field2315.previousHeldPackets.size());
                  }
               } else {
                  for (int var2 = 0; var2 < 9; var2++) {
                     AegisClient.field2315.method2046();
                  }

                  this.field1779 = true;
               }

               if (AegisClient.field2315.previousHeldPackets.isEmpty()) {
                  AegisClient.field2315.method2047();
                  this.field1788 = 0;
                  this.field1783 = false;
                  this.field1779 = false;
               }

               mc.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
            }
         }
      }
   }

   @Override
   public void onSendPacket(PacketSendEvent event) {
      if (this.field1783) {
      }
   }

   @Override
   public void onReceivePacket(PacketReceiveEvent event) {
      if (this.field1783) {
         Packet<?> var2 = event.getPacket();
         if (var2 instanceof SetPlayerInventoryS2CPacket) {
            ;
         }
      }
   }
}
