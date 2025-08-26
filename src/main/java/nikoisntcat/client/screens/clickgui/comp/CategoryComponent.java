package nikoisntcat.client.screens.clickgui.comp;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import nikoisntcat.client.managers.ModuleManager;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.utils.render.RenderUtil;
import nikoisntcat.client.utils.math.BoundaryUtils;
import nikoisntcat.client.utils.math.Class288;

import java.util.ArrayList;
import java.util.List;

public class CategoryComponent {
    public int y;
    public List<ModuleComponent> moduleComponents = new ArrayList<>();
    private int lastLeftClickY = 0;
    private boolean pressedRightClick = false;
    public int x;
    public int width;
    private final Class288 field2335 = new Class288(4.0f, 0.0f);
    private int lastLeftClickX = 0;
    public Category category;
    public boolean expanded = true;
    private boolean dragging = false;

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (BoundaryUtils.inBoundary(mouseX, mouseY, this.x, this.y, this.x + this.width, this.y + 20)) {
            if (button == 1) {
                this.pressedRightClick = true;
            }
            if (button == 0) {
                this.lastLeftClickX = (int)mouseX - this.x;
                this.lastLeftClickY = (int)mouseY - this.y;
                this.dragging = true;
            }
        }
        this.moduleComponents.forEach(moduleComponent -> moduleComponent.mouseClicked(mouseX, mouseY, button));
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        final var inBox = BoundaryUtils.inBoundary(
                mouseX, mouseY,
                this.x, this.y,
                this.x + this.width, this.y + 20
        );
        if (inBox && button == 1 && this.pressedRightClick) {
            this.expanded = !this.expanded;
        }
        this.pressedRightClick = false;
        this.dragging = false;
        this.moduleComponents.forEach(moduleComponent -> moduleComponent.mouseReleased(mouseX, mouseY, button));
    }

    public int method1919() {
        int n = this.width;
        for (ModuleComponent moduleComponent : this.moduleComponents) {
            n = Math.max(n, moduleComponent.method1855());
        }
        return n;
    }

    public CategoryComponent(Category category, int n, int n2) {
        this.category = category;
        this.x = n;
        this.y = n2;
    }

    public int method1920() {
        int n = 120;
        for (Module m : ModuleManager.findInCategory(this.category)) {
            ModuleComponent moduleComponent = new ModuleComponent(m);
            this.moduleComponents.add(moduleComponent);
            moduleComponent.method1859();
            n = Math.max(n, MinecraftClient.getInstance().textRenderer.getWidth(m.name) + 20);
        }
        this.width = n;
        return n;
    }

    private static void fill(DrawContext ctx, int x, int y, int width, int height, int color) {
        if (width <= 0 || height <= 0) {
            return;
        }
        ctx.fill(x, y, x + width, y + height, color);
    }

    public void mouseScrolled(double d, double d2, double d3, double d4) {
        this.moduleComponents.forEach(moduleComponent -> moduleComponent.mouseScrolled(d, d2, d3, d4));
    }

    public void render(DrawContext ctx, int n, int n2) {
        this.width = this.method1919();
        TextRenderer fr = MinecraftClient.getInstance().textRenderer;
        int n3 = (int)Math.ceil(this.field2335.method1845());
        int n4 = 22 + n3;
        RenderUtil.method1578(ctx.getMatrices(), this.x, this.y, this.width, n4, 12.0f, 0, 1.0f, 0x55FFFFFF);
        CategoryComponent.fill(ctx, this.x + 1, this.y + 1, this.width - 2, 21, -1726737127);
        CategoryComponent.fill(ctx, this.x + 1, this.y + 22, this.width - 2, n4 - 22 - 1, 0x66FFFFFF);
        String string = this.category != null ? this.category.name() : "CATEGORY";
        ctx.drawText(fr, string, this.x + 8, this.y + 7, -1, false);
        ctx.enableScissor(this.x + 1, this.y + 22, this.x + this.width - 1, this.y + n4 - 1);
        int n5 = 0;
        if (this.expanded) {
            for (ModuleComponent moduleComponent : this.moduleComponents) {
                n5 += moduleComponent.render(ctx, this.x + 1, this.y + 22 + n5, this.width - 2, n, n2);
            }
            this.field2335.method1847((float)n5 + 1.0f);
        } else {
            this.field2335.method1847(0.0f);
        }
        ctx.disableScissor();
    }

    public void init() {
        this.moduleComponents.forEach(ModuleComponent::init);
        this.field2335.method1844(0.0f);
    }

    // double mouseX, double mouseY, int button, double deltaX, double deltaY
    public void mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (button == 0 && this.dragging) {
            this.x = (int)(mouseX - (double)this.lastLeftClickX);
            this.y = (int)(mouseY - (double)this.lastLeftClickY);
        }
        this.moduleComponents.forEach(moduleComponent -> moduleComponent.mouseDragged(mouseX, mouseY, button, deltaX, deltaY));
    }
}