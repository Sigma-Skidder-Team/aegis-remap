package nikoisntcat.client.modules.impl;

import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.NumberSetting;

public class HighJumpModule extends Module {
   public NumberSetting field1927 = new NumberSetting("MotionY", 0.0, 10.0, 0.0, 0.1);
   static Object field1928;

   public HighJumpModule() {
      super("HighJump", 0, Category.MOVE);
   }

   @Override
   public void onPreTick() {
      if (mc.player.onGround) {
         mc.player.jump();
         mc.player.addVelocity(new Vec3d(mc.player.getVelocity().x, mc.player.getVelocity().y + this.field1927.getValue(), mc.player.getVelocity().z));
         this.setState(false);
      } else {
         mc.player.jump();
         mc.player
            .addVelocity(
               new Vec3d(
                  mc.player.getVelocity().x,
                  mc.player.getVelocity().y < 0.0
                     ? this.field1927.getValue() - mc.player.getVelocity().y
                     : mc.player.getVelocity().y + this.field1927.getValue(),
                  mc.player.getVelocity().z
               )
            );
         this.setState(false);
      }
   }
}
