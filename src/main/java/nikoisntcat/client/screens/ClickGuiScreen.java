package nikoisntcat.client.screens;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import nikoisntcat.client.modules.Category;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends Screen {
    public List<CategoryComponent> components = new ArrayList<>();

    public boolean mouseReleased(double d, double d2, int n) {
        this.components.forEach(categoryComponent -> categoryComponent.mouseReleased(d, d2, n));
        return super.mouseReleased(d, d2, n);
    }

    public ClickGuiScreen() {
        super((Text)Text.empty());
    }

    protected void renderDarkening(DrawContext class_3322) {
    }

    public boolean shouldPause() {
        return false;
    }

    public void method2055() {
        this.components.clear();
        int n = 20;
        for (Category moduleCategory : Category.values()) {
            CategoryComponent categoryComponent = new CategoryComponent(moduleCategory, n, 20);
            this.components.add(categoryComponent);
            n += categoryComponent.method1920() + 40;
        }
    }

    protected void init() {
        super.init();
        this.components.forEach(CategoryComponent::init);
    }

    public boolean mouseDragged(double d, double d2, int n, double d3, double d4) {
        this.components.forEach(categoryComponent -> categoryComponent.mouseDragged(d, d2, n, d3, d4));
        return super.mouseDragged(d, d2, n, d3, d4);
    }

    public void render(DrawContext class_3322, int n, int n2, float f) {
        super.render(class_3322, n, n2, f);
        this.components.forEach(categoryComponent -> categoryComponent.render(class_3322, n, n2));
    }

    public boolean mouseClicked(double d, double d2, int n) {
        this.components.forEach(categoryComponent -> categoryComponent.mouseClicked(d, d2, n));
        return super.mouseClicked(d, d2, n);
    }

    public boolean mouseScrolled(double d, double d2, double d3, double d4) {
        this.components.forEach(categoryComponent -> categoryComponent.mouseScrolled(d, d2, d3, d4));
        return super.mouseScrolled(d, d2, d3, d4);
    }
}