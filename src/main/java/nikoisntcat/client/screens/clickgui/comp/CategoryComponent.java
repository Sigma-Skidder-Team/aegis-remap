package nikoisntcat.client.screens.clickgui.comp;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import nikoisntcat.client.managers.ModuleManager;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.utils.render.RenderUtil;
import nikoisntcat.client.utils.math.Class250;
import nikoisntcat.client.utils.math.Class288;

import java.util.ArrayList;
import java.util.List;

public class CategoryComponent {
    public int field2329;
    public List<ModuleComponent> field2330 = new ArrayList<>();
    private int field2331 = 0;
    private boolean field2332 = false;
    public int field2333;
    public int field2334;
    private final Class288 field2335 = new Class288(4.0f, 0.0f);
    private int field2336 = 0;
    public Category category;
    public boolean field2338 = true;
    private boolean field2339 = false;

    public void mouseClicked(double d, double d2, int n) {
        if (Class250.method1684(d, d2, this.field2333, this.field2329, this.field2333 + this.field2334, this.field2329 + 20)) {
            if (n == 1) {
                this.field2332 = true;
            }
            if (n == 0) {
                this.field2336 = (int)d - this.field2333;
                this.field2331 = (int)d2 - this.field2329;
                this.field2339 = true;
            }
        }
        this.field2330.forEach(moduleComponent -> moduleComponent.mouseClicked(d, d2, n));
    }

    public void mouseReleased(double d, double d2, int n) {
        if (Class250.method1684(d, d2, this.field2333, this.field2329, this.field2333 + this.field2334, this.field2329 + 20) && n == 1 && this.field2332) {
            this.field2338 = !this.field2338;
        }
        this.field2332 = false;
        this.field2339 = false;
        this.field2330.forEach(moduleComponent -> moduleComponent.mouseReleased(d, d2, n));
    }

    public int method1919() {
        int n = this.field2334;
        for (ModuleComponent moduleComponent : this.field2330) {
            n = Math.max(n, moduleComponent.method1855());
        }
        return n;
    }

    public CategoryComponent(Category category, int n, int n2) {
        this.category = category;
        this.field2333 = n;
        this.field2329 = n2;
    }

    public int method1920() {
        int n = 120;
        for (Module module : ModuleManager.method1479(this.category)) {
            System.out.println(module.name);
            ModuleComponent moduleComponent = new ModuleComponent(module);
            this.field2330.add(moduleComponent);
            moduleComponent.method1859();
            n = Math.max(n, MinecraftClient.getInstance().textRenderer.getWidth(module.name) + 20);
        }
        this.field2334 = n;
        return n;
    }

    private static void method1921(DrawContext class_3322, int n, int n2, int n3, int n4, int n5) {
        if (n3 <= 0 || n4 <= 0) {
            return;
        }
        class_3322.fill(n, n2, n + n3, n2 + n4, n5);
    }

    public void mouseScrolled(double d, double d2, double d3, double d4) {
        this.field2330.forEach(moduleComponent -> moduleComponent.mouseScrolled(d, d2, d3, d4));
    }

    public void render(DrawContext ctx, int n, int n2) {
        this.field2334 = this.method1919();
        TextRenderer fr = MinecraftClient.getInstance().textRenderer;
        int n3 = (int)Math.ceil(this.field2335.method1845());
        int n4 = 22 + n3;
        RenderUtil.method1578(ctx.getMatrices(), this.field2333, this.field2329, this.field2334, n4, 12.0f, 0, 1.0f, 0x55FFFFFF);
        CategoryComponent.method1921(ctx, this.field2333 + 1, this.field2329 + 1, this.field2334 - 2, 21, -1726737127);
        CategoryComponent.method1921(ctx, this.field2333 + 1, this.field2329 + 22, this.field2334 - 2, n4 - 22 - 1, 0x66FFFFFF);
        String string = this.category != null ? this.category.name() : "CATEGORY";
        ctx.drawText(fr, string, this.field2333 + 8, this.field2329 + 7, -1, false);
        ctx.enableScissor(this.field2333 + 1, this.field2329 + 22, this.field2333 + this.field2334 - 1, this.field2329 + n4 - 1);
        int n5 = 0;
        if (this.field2338) {
            for (ModuleComponent moduleComponent : this.field2330) {
                n5 += moduleComponent.render(ctx, this.field2333 + 1, this.field2329 + 22 + n5, this.field2334 - 2, n, n2);
            }
            this.field2335.method1847((float)n5 + 1.0f);
        } else {
            this.field2335.method1847(0.0f);
        }
        ctx.disableScissor();
    }

    public void init() {
        this.field2330.forEach(ModuleComponent::init);
        this.field2335.method1844(0.0f);
    }

    public void mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (n == 0 && this.field2339) {
            this.field2333 = (int)(d - (double)this.field2336);
            this.field2329 = (int)(d2 - (double)this.field2331);
        }
        this.field2330.forEach(moduleComponent -> moduleComponent.mouseDragged(d, d2, n, d3, d4));
    }
}