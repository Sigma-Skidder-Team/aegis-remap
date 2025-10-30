package nikoisntcat.client.modules.impl.render;

import dev.sxmurxy.mre.builders.Builder;
import dev.sxmurxy.mre.builders.states.QuadColorState;
import dev.sxmurxy.mre.builders.states.SizeState;
import dev.sxmurxy.mre.msdf.MsdfFont;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.Render2DEvent;
import nikoisntcat.client.managers.MsdfFontManager;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ColorSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.math.ColorUtil;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ArrayListModule extends Module {
   public ColorSetting field1836;
   public ColorSetting field1837;
   private final List<Module> field1838 = new ArrayList<>();
   public ModeSetting field1839;
   public NumberSetting field1840;
   public BooleanSetting field1841;
   public NumberSetting field1842;
   public NumberSetting field1843 = new NumberSetting("Offset", 0.0, 1.0, 0.01, 0.01);
   public ColorSetting field1844;
   static Object field1845;

   @Override
   public void onToggle(Module module) {
      boolean var2 = this.field1839.getValue().equals("Vanilla");
      MsdfFont var3 = this.getFont();
      this.field1838.clear();
      this.field1838
         .addAll(
            AegisClient.moduleManager
               .modules
               .stream()
               .filter(Module::isEnabled)
               .sorted(
                  Comparator.comparingDouble(
                        e -> var2
                              ? (double)mc.textRenderer.getWidth(((Module)e).getTag() + ((Module)e).name)
                              : (double)var3.getWidth(((Module)e).getTag() + ((Module)e).name, (float)((int)this.field1842.getValue()))
                     )
                     .reversed()
               )
               .toList()
         );
   }

   private MsdfFont getFont() {
      String var1 = this.field1839.getValue();

      return switch (var1) {
         case "B" -> MsdfFontManager.biko;
         case "C" -> MsdfFontManager.nourd;
         case "D" -> MsdfFontManager.medium;
         default -> null;
      };
   }

   @Override
   public void onRender2D(Render2DEvent event) {
      int var2 = 0;
      double var3 = (double)System.currentTimeMillis() / 2000.0 * this.field1840.getValue();
      boolean var5 = this.field1839.getValue().equals("Vanilla");
      MsdfFont font = null;
      if (!var5) {
         font = this.getFont();
      }

      int var7 = var5 ? 9 : this.method1337();

      for (Module var9 : this.field1838) {
         var3 %= 2.0;
         Color var10 = ColorUtil.method2008(this.field1836.getColor(), this.field1837.getColor(), var3, this.field1837.getColor().getAlpha());
         String var11 = var9.name;
         if (var5) {
            if (this.field1841.getValue()) {
               Builder.blur()
                  .color(new QuadColorState(this.field1844.getRGB()))
                  .size(new SizeState((float)(mc.textRenderer.getWidth(var11) + 2), 12.0F))
                  .blurRadius(24.0F)
                  .smoothness(1.0F)
                  .build()
                  .render(
                     event.getDrawContext().getMatrices().peek().getPositionMatrix(),
                     (float)(mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(var11) - 2),
                     (float)var2
                  );
            } else {
               Builder.rectangle()
                  .color(new QuadColorState(this.field1844.getRGB()))
                  .size(new SizeState((float)(mc.textRenderer.getWidth(var11) + 2), 12.0F))
                  .build()
                  .render(
                     event.getDrawContext().getMatrices().peek().getPositionMatrix(),
                     (float)(mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(var11) - 2),
                     (float)var2
                  );
            }

            event.getDrawContext()
               .drawText(mc.textRenderer, var11, mc.getWindow().getScaledWidth() - mc.textRenderer.getWidth(var11), var2 + 1, var10.getRGB(), false);
         } else {
            if (this.field1841.getValue()) {
               Builder.blur()
                  .color(new QuadColorState(this.field1844.getRGB()))
                  .size(new SizeState(font.getWidth(var11, (float)((int)this.field1842.getValue())) + 6.0F, (float)(this.method1337() + 3)))
                  .blurRadius(24.0F)
                  .smoothness(1.0F)
                  .build()
                  .render(
                     event.getDrawContext().getMatrices().peek().getPositionMatrix(),
                     (float)mc.getWindow().getScaledWidth() - font.getWidth(var11, (float)((int)this.field1842.getValue())) - 6.0F,
                     (float)var2
                  );
            } else {
               Builder.rectangle()
                  .color(new QuadColorState(this.field1844.getRGB()))
                  .size(new SizeState(font.getWidth(var11, (float)((int)this.field1842.getValue())) + 6.0F, (float)(this.method1337() + 3)))
                  .build()
                  .render(
                     event.getDrawContext().getMatrices().peek().getPositionMatrix(),
                     (float)mc.getWindow().getScaledWidth() - font.getWidth(var11, (float)((int)this.field1842.getValue())) - 6.0F,
                     (float)var2
                  );
            }

            Builder.text()
               .font(font)
               .text(var11)
               .size((float)((int)this.field1842.getValue()))
               .color(var10)
               .build()
               .render(
                  event.getDrawContext().getMatrices().peek().getPositionMatrix(),
                  (float)mc.getWindow().getScaledWidth() - font.getWidth(var11, (float)((int)this.field1842.getValue())) - 4.0F,
                  (float)(var2 + 1)
               );
         }

         var2 += var7 + 2;
         var3 += this.field1843.getValue();
      }
   }

   public ArrayListModule() {
      super("ArrayList", 0, Category.RENDER);
      this.field1840 = new NumberSetting("ColorSpeed", 0.0, 10.0, 1.0, 1.0);
      this.field1839 = new ModeSetting("Font", "Vanilla", List.of("Vanilla", "B", "C", "D"));
      this.field1842 = new NumberSetting("Size", 0.0, 100.0, 1.0, 1.0, value -> !this.field1839.getValue().equalsIgnoreCase("Vanilla"));
      this.field1836 = new ColorSetting("ColorA", false);
      this.field1837 = new ColorSetting("ColorB", true);
      this.field1844 = new ColorSetting("BackGroundColor", true);
      this.field1841 = new BooleanSetting("BackGroundBlur", false);
   }

   private int method1337() {
      return (int)this.field1842.getValue();
   }
}
