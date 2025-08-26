package nikoisntcat.client.modules.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.List;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import nikoisntcat.client.events.impl.Render3DEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.utils.math.Class240;
import nikoisntcat.client.utils.math.Class294;
import nikoisntcat.client.utils.player.Class127;
import nikoisntcat.client.utils.render.RenderUtil;

public class ProjectilesModule extends Module {
   public Class294 field1833;
   public Class294 field1834 = new Class294(0.0F, 0.0F, 0.0F);

   private void method1332(Box box, MatrixStack matrixStack, Color color, boolean fix) {
      BufferBuilder var6 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
      RenderSystem.disableDepthTest();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      RenderUtil.method1583(var6, box, matrixStack.peek().getPositionMatrix(), color, fix);
      BuiltBuffer var7 = var6.endNullable();
      if (var7 != null) {
         BufferRenderer.drawWithGlobalProgram(var7);
      }

      RenderSystem.disableBlend();
      RenderSystem.enableDepthTest();
   }

   public ProjectilesModule() {
      super("Projectiles", 0, Category.RENDER);
      this.field1833 = new Class294(0.0F, 0.0F, 0.0F);
   }

   @Override
   public void onRender3D(Render3DEvent render3DEvent) {
      if (!mc.player.getMainHandStack().isEmpty()) {
         Class127 var2 = Class127.method1012(mc.player.getMainHandStack().getItem());
         if (var2 != null) {
            float var3 = (float)Math.toRadians((double)(mc.player.getYaw() - 25.0F));
            double var6 = mc.player.getBoundingBox().getAverageSideLength() / 2.0;
            double var8 = Math.cos((double)var3) * var6;
            double var10 = Math.sin((double)var3) * var6;
            MatrixStack var12 = render3DEvent.method1427();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableDepthTest();
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            var12.push();
            Vec3d var14 = mc.getEntityRenderDispatcher().camera.getPos();
            Tessellator var15 = Tessellator.getInstance();
            BufferBuilder var16 = var15.begin(DrawMode.LINE_STRIP, VertexFormats.POSITION_COLOR);
            RenderSystem.lineWidth(10.0F);
            List var17 = var2.method1016(render3DEvent.method1423());

            for (int var18 = 0; var18 < var17.size(); var18++) {
               Class240 var19 = (Class240)var17.get(var18);
               double var20 = (double)((float)(var18 + 1) / (float)var17.size());
               double var22 = var8 * (1.0 - var20);
               double var24 = var10 * (1.0 - var20);
               double var26 = 0.2F * (1.0 - var20);
               Vec3d var28 = var19.method1625().subtract(var14).subtract(var22, var26, var24);
               float var29 = (float)Math.min(1, var18) * 0.05F;
               var16.vertex(var12.peek().getPositionMatrix(), (float)var28.x, (float)var28.y, (float)var28.z).color(0.0F, 0.0F, 0.0F, var29);
            }

            BufferRenderer.drawWithGlobalProgram(var16.endNullable());
            var16 = var15.begin(DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
            RenderSystem.lineWidth(2.0F * (float)mc.getWindow().getScaleFactor());
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);

            for (int var36 = 0; var36 < var17.size(); var36++) {
               Class240 var37 = (Class240)var17.get(var36);
               double var38 = (double)((float)(var36 + 1) / (float)var17.size());
               double var40 = var8 * (1.0 - var38);
               double var42 = var10 * (1.0 - var38);
               double var43 = 0.2F * (1.0 - var38);
               Vec3d var44 = var37.method1625().subtract(var14).subtract(var40, var43, var42);
               float var45 = (float)Math.min(1, var36) * 0.75F;
               var16.vertex(var12.peek().getPositionMatrix(), (float)var44.x, (float)var44.y, (float)var44.z).color(1.0F, 1.0F, 1.0F, var45);
            }

            BufferRenderer.drawWithGlobalProgram(var16.endNullable());
            if (var2.field1476 == null) {
               if (var2.field1489 != null) {
                  this.method1332(var2.field1489.getBoundingBox(), render3DEvent.method1427(), new Color(-16723258), true);
               }
            } else {
               double var30 = var2.field1488 - mc.gameRenderer.getCamera().getPos().x;
               double var39 = var2.field1493 - mc.gameRenderer.getCamera().getPos().y;
               double var41 = var2.field1482 - mc.gameRenderer.getCamera().getPos().z;
               var12.push();
               var12.translate(var30, var39, var41);
               BlockPos var32 = new BlockPos(0, 0, 0).offset(((BlockHitResult)var2.field1476).getSide());
               RenderUtil.method1580(
                  var12,
                  45.0,
                  this.field1834.method1866((float)var32.getX()),
                  this.field1834.method1867((float)(-var32.getY())),
                  this.field1834.method1868((float)var32.getZ())
               );
               RenderUtil.method1580(
                  var12,
                  90.0,
                  this.field1833.method1866((float)var32.getZ()),
                  this.field1833.method1867((float)var32.getY()),
                  this.field1833.method1868((float)(-var32.getX()))
               );
               var12.translate(-0.5F, 0.0F, -0.5F);
               Box var33 = new Box(0.0, 0.0, 0.0, 1.0, 0.0, 1.0);
               Color var34 = new Color(-21931);
               var34 = new Color(var34.getRed(), var34.getGreen(), var34.getBlue(), 125);
               this.method1332(var33, var12, var34, false);
               var12.pop();
            }

            var12.pop();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
         }
      }
   }
}
