package nikoisntcat.client.modules.impl;

import java.util.List;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.render.RenderUtil;
import org.jetbrains.annotations.NotNull;

public class AnimationModule extends Module {
   public static NumberSetting field1733;
   public static NumberSetting field1734;
   public static ModeSetting field1735;
   public static NumberSetting field1736;
   public static AnimationModule instance;
   public static NumberSetting field1738;
   static Object field1739;

   private static void method1283(MatrixStack matrices, float equipProgress, float swingProgress) {
      matrices.translate(0.56F, -0.52F, -0.71999997F);
      matrices.translate(0.0F, equipProgress * -0.6F, 0.0F);
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(45.0F));
      float var3 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
      float var4 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(var3 * -20.0F));
      matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(var4 * -20.0F));
      matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var4 * -80.0F));
   }

   private static void method1284(MatrixStack matrices, float p_178096_1_, float p_178096_2_) {
      matrices.translate(0.56F, -0.52F, -0.71999997F);
      matrices.translate(0.0F, p_178096_1_ * -0.6F, 0.0F);
      RenderUtil.method1580(matrices, 45.0, 0.0F, 1.0F, 0.0F);
      float var3 = MathHelper.sin(p_178096_2_ * p_178096_2_ * (float) Math.PI);
      float var4 = MathHelper.sin(MathHelper.sqrt(p_178096_2_) * (float) Math.PI);
      RenderUtil.method1580(matrices, (double)(var3 * -20.0F), 0.0F, 1.0F, 0.0F);
      RenderUtil.method1580(matrices, (double)(var4 * -20.0F), 0.0F, 0.0F, 1.0F);
      RenderUtil.method1580(matrices, (double)(var4 * -80.0F), 1.0F, 0.0F, 0.0F);
   }

   private static void method1285(@NotNull MatrixStack matrices, Arm arm, float equipProgress) {
      int var3 = arm == Arm.RIGHT ? 1 : -1;
      matrices.translate((float)var3 * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);
   }

   private static void method1286(MatrixStack matrices) {
      matrices.translate(-0.5F, -0.05F, 0.0F);
      RenderUtil.method1580(matrices, 30.0, 0.0F, 1.0F, 0.0F);
      RenderUtil.method1580(matrices, -80.0, 1.0F, 0.0F, 0.0F);
      RenderUtil.method1580(matrices, 60.0, 0.0F, 1.0F, 0.0F);
   }

   static {
      field1736 = new NumberSetting("SwordX", 0.0, 1.0, -1.0, 0.01);
      field1738 = new NumberSetting("SwordY", 0.0, 1.0, -1.0, 0.01);
      field1733 = new NumberSetting("SwordZ", 0.0, 1.0, -1.0, 0.01);
      field1734 = new NumberSetting("SwordScale", 1.0, 2.0, 0.0, 0.01);
      field1735 = new ModeSetting("SwordMode", "Vanilla", List.of("Vanilla", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"));
      instance = new AnimationModule();
   }

   private static void method1287(MatrixStack matrices) {
      matrices.translate(field1736.getValue(), field1738.getValue(), field1733.getValue());
   }

   private static void method1288(MatrixStack matrices) {
      matrices.translate(-0.5F, 0.2F, 0.0F);
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(30.0F));
      matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-80.0F));
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(60.0F));
   }

   public static void method1289(MatrixStack matrices, float swingProgress, float equipProgress, Arm arm, boolean blocking) {
      method1287(matrices);
      float var5 = -0.4F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
      float var6 = 0.2F * MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) (Math.PI * 2));
      float var7 = -0.2F * MathHelper.sin(swingProgress * (float) Math.PI);
      int var8 = arm == Arm.RIGHT ? 1 : -1;
      matrices.translate((float)var8 * var5, var6, var7);
      method1285(matrices, arm, equipProgress);
      method1290(matrices, arm, swingProgress);
      matrices.scale((float)field1734.getValue(), (float)field1734.getValue(), (float)field1734.getValue());
      if (blocking) {
         matrices.pop();
         matrices.push();
         method1287(matrices);
         String var9 = field1735.getValue();
         switch (var9) {
            case "Vanilla":
               matrices.translate(0.4F, -0.3F, -0.5F);
               method1283(matrices, equipProgress, swingProgress);
               method1288(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "A":
               float var11 = (double)swingProgress > 0.5 ? 1.0F - swingProgress : swingProgress;
               matrices.translate(0.5F, -0.5F, -0.7F);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-20.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-60.0F));
               matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(25.0F * var11));
               RenderUtil.method1580(matrices, (double)(-110.0F * var11), 2.0F, 0.0F, 0.0F);
               matrices.translate(0.0F, 0.0F, 0.1F);
               break;
            case "B":
               matrices.translate(0.4F, -0.3F, -0.5F);
               method1283(matrices, equipProgress / 2.0F, -0.2F);
               equipProgress = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
               RenderUtil.method1580(matrices, (double)(-equipProgress / 19.0F), equipProgress / 20.0F, -0.0F, 9.0F);
               RenderUtil.method1580(matrices, (double)(-equipProgress * 5.0F), 10.0F, equipProgress / 50.0F, 0.0F);
               matrices.translate(0.0F, 0.3F, 0.0F);
               method1288(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "C":
               matrices.translate(0.4F, -0.3F, -0.5F);
               method1283(matrices, equipProgress / 2.0F, 0.0F);
               RenderUtil.method1580(
                  matrices,
                  (double)(-MathHelper.sin(swingProgress * swingProgress * (float) Math.PI) * 40.0F / 2.0F),
                  MathHelper.sin(swingProgress * swingProgress * (float) Math.PI) / 2.0F,
                  -0.0F,
                  9.0F
               );
               RenderUtil.method1580(
                  matrices,
                  (double)(-MathHelper.sin(swingProgress * swingProgress * (float) Math.PI) * 30.0F),
                  1.0F,
                  MathHelper.sin(swingProgress * swingProgress * (float) Math.PI) / 2.0F,
                  -0.0F
               );
               method1288(matrices);
               matrices.translate(-0.05F, mc.player.isSneaking() ? -0.2F : 0.0F, 0.1F);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "D":
               matrices.translate(0.4F, -0.3F, -0.5F);
               matrices.translate(0.56F, -0.52F, -0.71999997F);
               matrices.translate(0.0F, equipProgress * -0.6F, 0.0F);
               RenderUtil.method1580(matrices, 45.0, 0.0F, 1.0F, 0.0F);
               float var12 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
               float var13 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
               RenderUtil.method1580(matrices, (double)(var12 * -20.0F), 0.0F, 1.0F, 0.0F);
               RenderUtil.method1580(matrices, (double)(var13 * -20.0F), 0.0F, 0.0F, 1.0F);
               method1288(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "E":
               matrices.translate(0.4F, -0.3F, -0.5F);
               matrices.translate(-0.09, 0.25, 0.0);
               method1283(matrices, equipProgress / 2.0F, swingProgress);
               equipProgress = MathHelper.sin((float)((double)MathHelper.sqrt(swingProgress) * Math.PI));
               matrices.translate(mc.player.isSneaking() ? -0.03 : 0.1, mc.player.isSneaking() ? 0.0 : -0.0, 0.1);
               RenderUtil.method1580(matrices, (double)(equipProgress * 30.0F / 2.0F), -equipProgress, -0.0F, 9.0F);
               RenderUtil.method1580(matrices, (double)(equipProgress * 40.0F), 1.0F, -equipProgress / 2.0F, -0.0F);
               method1286(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "F":
               matrices.translate(0.4F, -0.5F, -0.5F);
               method1283(matrices, equipProgress / 2.0F, 0.0F);
               matrices.translate(0.1, 0.55, 0.0);
               equipProgress = MathHelper.sin((float)((double)MathHelper.sqrt(swingProgress) * Math.PI));
               RenderUtil.method1580(matrices, (double)(-equipProgress * 65.0F / 2.0F), -equipProgress / 2.0F, -0.0F, 9.0F);
               RenderUtil.method1580(matrices, (double)(-equipProgress * 53.0F), 1.0F, equipProgress / 2.0F, -0.0F);
               method1286(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "G":
               matrices.translate(0.4F, 0.4F, 0.4F);
               method1284(matrices, 0.0F, 0.0F);
               matrices.translate(-0.5F, 0.2F, 0.0F);
               RenderUtil.method1580(matrices, 30.0, 0.0F, 1.0F, 0.0F);
               RenderUtil.method1580(matrices, -80.0, 1.0F, 0.0F, 0.0F);
               RenderUtil.method1580(matrices, 60.0, 0.0F, 1.0F, 0.0F);
               int var14 = (int)Math.min(
                  255L,
                  (System.currentTimeMillis() % 255L > 127L ? Math.abs(Math.abs(System.currentTimeMillis()) % 255L - 255L) : System.currentTimeMillis() % 255L)
                     * 2L
               );
               float var15 = (double)swingProgress > 0.5 ? 1.0F - swingProgress : swingProgress;
               matrices.translate(0.3F, -0.0F, 0.4F);
               RenderUtil.method1580(matrices, 0.0, 0.0F, 0.0F, 1.0F);
               matrices.translate(0.0F, 0.5F, 0.0F);
               RenderUtil.method1580(matrices, 90.0, 1.0F, 0.0F, -1.0F);
               matrices.translate(0.6F, 0.5F, 0.0F);
               RenderUtil.method1580(matrices, -90.0, 1.0F, 0.0F, -1.0F);
               RenderUtil.method1580(matrices, -10.0, 1.0F, 0.0F, -1.0F);
               RenderUtil.method1580(matrices, (double)(-var15 * 10.0F), 10.0F, 10.0F, -9.0F);
               RenderUtil.method1580(matrices, 10.0, -1.0F, 0.0F, 0.0F);
               matrices.translate(0.0, 0.0, -0.5);
               RenderUtil.method1580(matrices, mc.player.handSwinging ? (double)((float)(-var14) / 5.0F) : 1.0, 1.0F, -0.0F, 1.0F);
               matrices.translate(0.0, 0.0, 0.5);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "H":
               matrices.translate(0.4F, -0.2F, -0.5F);
               matrices.translate(0.08, 0.02, 0.0);
               float var25 = MathHelper.sin((float)((double)MathHelper.sqrt(swingProgress) * Math.PI));
               method1283(matrices, equipProgress / 1.4F, 0.0F);
               method1288(matrices);
               RenderUtil.method1580(matrices, (double)(-var25 * 41.0F), 1.1F, 0.8F, -0.3F);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "I":
               matrices.translate(0.3, -0.1, -0.4);
               matrices.translate(0.0F, 0.0F, 0.0F);
               float var24 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
               method1283(matrices, equipProgress / 2.0F, swingProgress);
               RenderUtil.method1580(matrices, (double)(var24 * 30.0F / 2.0F), -var24, -0.0F, 9.0F);
               RenderUtil.method1580(matrices, (double)(var24 * 40.0F), 1.0F, -var24 / 2.0F, -0.0F);
               method1288(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "J":
               matrices.translate(0.35F, -0.1F, -0.4F);
               matrices.translate(0.0, 0.03, 0.0);
               float var23 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
               method1283(matrices, equipProgress / 1.8F, 0.0F);
               RenderUtil.method1580(matrices, (double)(-var23 * -30.0F / 2.0F), var23 / 2.0F, 1.0F, 4.0F);
               RenderUtil.method1580(matrices, (double)(-var23 * 7.5F), 1.0F, var23 / 3.0F, -0.0F);
               method1288(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "K":
               matrices.translate(0.3, 0.1, -0.1);
               matrices.translate(0.02, 0.02, 0.0);
               method1283(matrices, equipProgress / 2.0F, swingProgress);
               matrices.translate(0.4, -0.06, -0.46);
               float var22 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
               RenderUtil.method1580(matrices, (double)(var22 * 25.0F / 2.0F), -var22, -0.0F, 9.0F);
               RenderUtil.method1580(matrices, (double)(var22 * 15.0F), 1.0F, -var22 / 2.0F, -0.0F);
               method1288(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "L":
               matrices.translate(0.3, -0.1, -0.4);
               matrices.translate(0.0F, 0.0F, 0.0F);
               float var21 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
               matrices.translate(0.0, 0.0, 0.0);
               method1283(matrices, equipProgress / 2.5F, 0.0F);
               RenderUtil.method1580(matrices, (double)(-var21 * 40.0F / 2.0F), var21 / 2.0F, 1.0F, 4.0F);
               RenderUtil.method1580(matrices, (double)(-var21 * 30.0F), 1.0F, var21 / 3.0F, -0.0F);
               method1288(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "M":
               matrices.translate(0.3, -0.1, -0.4);
               matrices.translate(0.0, 0.03, 0.0);
               float var20 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
               matrices.translate(0.0, 0.0, 0.0);
               method1283(matrices, equipProgress / 2.5F, 0.0F);
               RenderUtil.method1580(matrices, (double)(-var20 * 74.0F / 2.0F), var20 / 2.0F, 1.0F, 4.0F);
               RenderUtil.method1580(matrices, (double)(-var20 * 52.0F), 1.0F, var20 / 3.0F, -0.0F);
               method1288(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "N":
               matrices.translate(0.3, -0.2, -0.4);
               matrices.translate(-0.08, 0.12, 0.0);
               float var16 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
               matrices.translate(0.0, 0.0, 0.0);
               method1283(matrices, equipProgress / 1.4F, 0.0F);
               RenderUtil.method1580(matrices, (double)(-var16 * 65.0F / 2.0F), var16 / 2.0F, 1.0F, 4.0F);
               RenderUtil.method1580(matrices, (double)(-var16 * 60.0F), 1.0F, var16 / 3.0F, -0.0F);
               method1288(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
               break;
            case "O":
               matrices.translate(0.3, -0.1, -0.4);
               method1283(matrices, equipProgress, swingProgress);
               method1288(matrices);
               matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(135.0F));
               matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40.0F));
         }

         matrices.scale((float)field1734.getValue(), (float)field1734.getValue(), (float)field1734.getValue());
      }
   }

   public AnimationModule() {
      super("Animation", 0, Category.RENDER);
   }

   private static void method1290(@NotNull MatrixStack matrices, Arm arm, float swingProgress) {
      int var3 = arm == Arm.RIGHT ? 1 : -1;
      float var4 = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var3 * (45.0F + var4 * -20.0F)));
      float var5 = MathHelper.sin(MathHelper.sqrt(swingProgress) * (float) Math.PI);
      matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)var3 * var5 * -20.0F));
      matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(var5 * -80.0F));
      matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)var3 * -45.0F));
   }
}
