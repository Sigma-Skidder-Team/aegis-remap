package nikoisntcat.client.modules.impl.move;

import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.utils.MovementUtil;

public class StuckModule extends Module {
   private Vec3d field1897;
   private int field1898 = 0;

   @Override
   public void onEnable() {
      MovementUtil.startBlink();
   }

   @Override
   public void onTick() {
   }

   public StuckModule() {
      super("Stuck", 0, Category.MOVE);
      this.field1897 = new Vec3d(0.0, 0.0, 0.0);
   }

   @Override
   public void onDisable() {
       MovementUtil.stopBlink();
   }
}
