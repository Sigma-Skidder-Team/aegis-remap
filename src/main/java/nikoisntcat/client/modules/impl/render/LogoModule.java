package nikoisntcat.client.modules.impl.render;

import dev.sxmurxy.mre.builders.Builder;
import dev.sxmurxy.mre.builders.states.QuadColorState;
import dev.sxmurxy.mre.builders.states.QuadRadiusState;
import dev.sxmurxy.mre.builders.states.SizeState;
import dev.sxmurxy.mre.renderers.impl.BuiltRectangle;
import dev.sxmurxy.mre.renderers.impl.BuiltText;
import net.minecraft.util.Formatting;
import nikoisntcat.client.events.impl.Render2DEvent;
import nikoisntcat.client.managers.MsdfFontManager;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.utils.font.FontManager;
import nikoisntcat.client.utils.math.ColorUtil;
import nikoisntcat.client.utils.render.RenderUtil;
import nikoisntcat.client.utils.render.ShaderUtil;

import java.awt.Color;
import java.util.List;

public class LogoModule extends Module {
   float field1730;
   public ModeSetting field1731 = new ModeSetting("Mode", "A", List.of("A", "B"));

   @Override
   public void onRender2D(Render2DEvent event) {
      String var2 = this.field1731.getValue();
      switch (var2) {
         case "A":
            String var4 = mc.isInSingleplayer() ? "SinglePlayer" : mc.getNetworkHandler().getServerInfo().address + " ";
            String var5 = Formatting.DARK_GRAY
               + " | "
               + Formatting.WHITE
               + Formatting.DARK_GRAY
               + Formatting.WHITE
               + mc.getCurrentFps()
               + "fps"
               + Formatting.DARK_GRAY
               + " | "
               + Formatting.WHITE
               + var4;
            this.field1730 = this.method1281(FontManager.field2423.method1186(var5) + 10.0F, this.field1730, 0.1F);
            ShaderUtil.blur(
               event.getDrawContext().getMatrices(),
               matrixStack -> RenderUtil.method1578(
                     matrixStack,
                     4.0F,
                     5.0F,
                     4.0F + this.field1730 + FontManager.field2411.method1186("Aegis"),
                     17.0F,
                     1.0F,
                     new Color(10, 10, 10, 255).getRGB(),
                     20.0F,
                     new Color(100, 100, 100, 255).getRGB()
                  ),
               1.0F,
               new Color(10, 10, 10, 255).getRGB(),
               1.0F
            );
            RenderUtil.method1578(
               event.getDrawContext().getMatrices(),
               6.0F,
               6.0F,
               this.field1730 + FontManager.field2411.method1186("Aegis"),
               1.0F,
               1.0F,
               new Color(255, 0, 0, 150).getRGB(),
               0.0F,
               0
            );
            FontManager.field2423
               .method1192(event.getDrawContext().getMatrices(), var5, (double)(11.0F + FontManager.field2411.method1186("Aegis")), 10.0, Color.WHITE.getRGB());
            FontManager.field2411.method1192(event.getDrawContext().getMatrices(), "Aegis", 8.5, 8.0, new Color(31, 135, 204).getRGB());
            FontManager.field2411.method1192(event.getDrawContext().getMatrices(), "Aegis", 9.0, 8.5, Color.WHITE.getRGB());
            break;
         case "B":
            BuiltText var8 = Builder.text().font(MsdfFontManager.nourd).text("Aegis").color(Color.WHITE).size(30.0F).build();
            Color var9 = new Color(144, 241, 117, 255);
            Color var10 = new Color(0, 139, 253, 75);
            Color var11 = ColorUtil.method2007(var9, var10, (double)System.currentTimeMillis() / 2000.0);
            Color var12 = ColorUtil.method2007(var9, var10, (double)System.currentTimeMillis() / 2000.0 + 0.9);
            BuiltRectangle var13 = Builder.rectangle()
               .size(new SizeState(4.5F, 24.5F))
               .color(new QuadColorState(var12, var11, var11, var12))
               .radius(new QuadRadiusState(1.5))
               .build();
            BuiltRectangle var14 = Builder.rectangle()
               .size(new SizeState(4.5F, 12.5F))
               .color(new QuadColorState(var12, var11, var11, var12))
               .radius(new QuadRadiusState(1.5))
               .build();
            BuiltRectangle var15 = Builder.rectangle()
               .size(new SizeState(4.5F, 15.0F))
               .color(new QuadColorState(var11, var12, var12, var11))
               .radius(new QuadRadiusState(1.5, 0.0, 0.0, 1.5))
               .build();
            var13.render(event.getDrawContext().getMatrices().peek().getPositionMatrix(), 0.0F + var8.font().getWidth("Aegi", var8.size()), 5.0F);
            var14.render(event.getDrawContext().getMatrices().peek().getPositionMatrix(), 0.0F + var8.font().getWidth("Ae", var8.size()) + 5.0F, 3.5F);
            var15.render(event.getDrawContext().getMatrices().peek().getPositionMatrix(), 0.0F + var8.font().getWidth("Aeg", var8.size()) - 3.0F, 0.0F);
            var8.render(event.getDrawContext().getMatrices().peek().getPositionMatrix(), 0.0F, 0.0F);
      }
   }

   public LogoModule() {
      super("Logo", 0, Category.RENDER);
   }

   public float method1281(float target, float current, float speed) {
      if (current == target) {
         return current;
      } else {
         boolean var4 = target > current;
         if (speed < 0.0F) {
            speed = 0.0F;
         } else if (speed > 1.0F) {
            speed = 1.0F;
         }

         double var7 = (Math.max((double)target, (double)current) - Math.min((double)target, (double)current)) * (double)speed;
         if (var7 < 0.1) {
            var7 = 0.1;
         }

         if (var4) {
            current += (float)var7;
            if (current >= target) {
               current = target;
            }
         } else if (target < current) {
            current -= (float)var7;
            if (current <= target) {
               current = target;
            }
         }

         return current;
      }
   }
}
