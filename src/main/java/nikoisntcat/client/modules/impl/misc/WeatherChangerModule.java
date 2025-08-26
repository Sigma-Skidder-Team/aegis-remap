package nikoisntcat.client.modules.impl.misc;

import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.ModeSetting;

import java.util.List;

public class WeatherChangerModule extends Module {
   public static ModeSetting weather;
   public static WeatherChangerModule instance;

   static {
      instance = new WeatherChangerModule();
      weather = new ModeSetting("Weather", "Clear", List.of("Clear", "Rain", "Storm", "Snow"));
   }

   public void method1382() {
      if (mc != null && mc.world != null) {
         if ("Clear".equals(weather.getValue())) {
            mc.world.setRainGradient(0.0F);
            mc.world.setThunderGradient(0.0F);
         } else if ("Rain".equals(weather.getValue())) {
            mc.world.setRainGradient(1.0F);
            mc.world.setThunderGradient(0.0F);
         } else if ("Storm".equals(weather.getValue())) {
            mc.world.setRainGradient(1.0F);
            mc.world.setThunderGradient(1.0F);
         } else if ("Snow".equals(weather.getValue())) {
            mc.world.setRainGradient(1.0F);
            mc.world.setThunderGradient(0.0F);
         }
      }
   }

   public WeatherChangerModule() {
      super("WeatherChanger", 0, false, Category.MISC);
   }

   @Override
   public void onTick() {
      this.method1382();
   }
}
