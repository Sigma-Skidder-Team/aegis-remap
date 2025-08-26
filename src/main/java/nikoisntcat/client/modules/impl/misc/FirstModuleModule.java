package nikoisntcat.client.modules.impl.misc;

import java.awt.Color;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.LivingEntity;
import nikoisntcat.client.events.impl.Render2DEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.utils.font.FontManager;
import nikoisntcat.client.utils.render.ShaderUtil;
import nikoisntcat.client.utils.render.RenderUtil;

public class FirstModuleModule extends Module {
    private float field1809;
    private LivingEntity target;
    private long field1811;
    private float field1812;
    static Object field1813;

    @Override
    public void onEnable() {
    }

    @Override
    public void onTick() {
        if (mc.player == null) {
            return;
        }
        if (mc.player.isOnGround() && FirstModuleModule.mc.options.forwardKey.isPressed()) {
            mc.player.jump();
        }
    }

    private void method1316(DrawContext context, String name, int x, int y, int panelWidth) {
        int n = mc.textRenderer.getWidth(name);
        int n2 = x + (panelWidth - n) / 2;
        int n3 = (int) (255.0f * Math.min(1.0f, (this.field1812 - 0.3f) * 3.0f)) << 24 | 0xFFFFFF;
        context.drawText(mc.textRenderer, name, n2, y + 5, n3, false);
    }

    private void method1317(float health) {
        float f = health - this.field1809;
        float f2 = Math.min(0.5f, Math.abs(f) * 0.3f);
        if (f > 0.0f) {
            this.field1809 = Math.min(health, this.field1809 + f2);
        } else if (f < 0.0f) {
            this.field1809 = Math.max(health, this.field1809 - f2);
        }
    }

    public FirstModuleModule() {
        super("FirstModule", 70, false, Category.MISC);
    }

    private void method1318() {
        this.field1812 = this.target != null ? Math.min(1.0f, this.field1812 + 0.05f) : Math.max(0.0f, this.field1812 - 0.07f);
    }

    private void method1320(DrawContext context, int x, int y, int width, int height) {
        int n = (int) (102.0f * this.field1812) << 24 | 0;
        context.fill(x, y, x + width, y + height, n);
        int n2 = (int) (85.0f * this.field1812) << 24 | 0x555555;
        context.drawBorder(x, y, width, height, n2);
    }

    private void method1321(DrawContext context, float health, float maxHealth, int x, int y, int panelWidth, float progress) {
        int n = (int) (80.0f * progress);
        int n2 = (int) (5.0f * progress);
        int n3 = x + (panelWidth - n) / 2;
        int n4 = y + (int) (20.0f + 5.0f * (1.0f - progress));
        int n5 = (int) (170.0f * progress);
        context.fill(n3, n4, n3 + n, n4 + n2, n5 << 24 | 0x3D0C0C);
        float f = Math.min(1.0f, health / maxHealth);
        int n6 = (int) ((float) n * f);
        int n7 = this.method1322(f);
        context.fill(n3, n4, n3 + n6, n4 + n2, n7);
        if (f > 0.05f) {
            int n8 = (int) (128.0f * progress) << 24 | 0xFFFFFF;
            context.fill(n3, n4, n3 + n6, n4 + 1, n8);
        }
    }

    private int method1322(float healthRatio) {
        if (healthRatio > 0.6f) {
            return -16711936;
        }
        if (healthRatio > 0.3f) {
            return -256;
        }
        return -65536;
    }

    private void method1323(DrawContext context, int level, int x, int y, float progress) {
        String string = "Lv." + level;
        int n = (int) ((float) (x + 5) + 10.0f * (1.0f - progress));
        int n2 = (int) (255.0f * Math.min(1.0f, (progress - 0.5f) * 2.0f)) << 24 | 0xFFE69900;
        context.drawText(mc.textRenderer, string, n, y + 5, n2, false);
    }

    private void method1324(DrawContext context, int healthPercent, float health, float maxHealth, int x, int y, int panelWidth) {
        if (health > 0.1f * maxHealth) {
            String string = healthPercent + "%";
            int n = mc.textRenderer.getWidth(string);
            int n2 = (int) ((float) (x + panelWidth - n - 5) + 5.0f * (1.0f - this.field1812));
            int n3 = (int) ((float) (y + 15) + 5.0f * (1.0f - this.field1812));
            int n4 = (int) (255.0f * Math.min(1.0f, (this.field1812 - 0.7f) * 3.0f));
            int n5 = this.method1322(health / maxHealth);
            int n6 = n4 << 24 | n5 & 0xFFFFFF;
            context.drawText(mc.textRenderer, string, n2, n3, n6, false);
        }
    }

    private void method1325(LivingEntity target) {
        if (target != this.target) {
            this.target = target;
            this.field1811 = System.currentTimeMillis();
            this.field1812 = target != null ? 0.1f : this.field1812;
        }
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        ShaderUtil.blur(event.getDrawContext().getMatrices(), matrixStack -> {
            FontManager.field2419.method1181(event.getDrawContext().getMatrices(), "Oh Geez", 1.0, 1.0, Color.WHITE);
            RenderUtil.method1578(event.getDrawContext().getMatrices(), 4.0f, 4.0f, 50.0f, 50.0f, 5.0f, new Color(200, 0, 0, 200).getRGB(), 0.0f, Color.WHITE.getRGB());
        }, 1.0f, Color.WHITE.getRGB(), 1.0f);
    }
}
