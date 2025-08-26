package nikoisntcat.client.modules.impl;

import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;

public class NoJumpDelayModule extends Module {

   public NoJumpDelayModule() {
      super("NoJumpDelay", 0, Category.MOVE);
   }

   @Override
   public void onPreTick() {
      mc.player.jumpingCooldown = 0;
   }
}
