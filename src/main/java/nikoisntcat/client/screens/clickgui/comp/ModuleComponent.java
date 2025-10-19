package nikoisntcat.client.screens.clickgui.comp;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import nikoisntcat.client.settings.Setting;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.math.Class249;
import nikoisntcat.client.utils.math.BoundaryUtils;
import nikoisntcat.client.utils.math.Class288;
import nikoisntcat.client.modules.Module;

import java.util.ArrayList;
import java.util.List;

public class ModuleComponent {
    int y;
    private final Class288 field2259;
    int x;
    private final Class288 field2261;
    private boolean field2262 = false;
    private boolean field2263 = true;
    public Module mod;
    private final Class288 field2265;
    int width;
    public List<SettingComponent> settingComponents = new ArrayList();
    private final Class288 field2268;
    public boolean field2269 = false;

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (BoundaryUtils.inBoundary(mouseX, mouseY, this.x + 1, this.y + 1, this.x + this.width - 1, this.y + 16) && (button == 1 || button == 0)) {
            this.field2262 = true;
        }
        if (this.field2269) {
            this.settingComponents.forEach(settingComponent -> settingComponent.method1668(mouseX, mouseY, button));
        }
    }

    public ModuleComponent(Module module) {
        this.field2265 = new Class288(3.2f, 0.0f);
        this.field2261 = new Class288(18.0f, 0.0f);
        this.field2259 = new Class288(20.0f, 0.0f);
        this.field2268 = new Class288(10.0f, 0.0f);
        this.mod = module;
    }

    public int getHeight() {
        int n = MinecraftClient.getInstance().textRenderer.getWidth(this.mod.name) + 20;
        for (SettingComponent settingComponent : this.settingComponents) {
            n = Math.max(n, settingComponent.method1673());
        }
        return n;
    }

    public void mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (this.field2269) {
            this.settingComponents.forEach(settingComponent -> settingComponent.mouseDragged(d, d2, n, d3, d4));
        }
    }

    public void method1857(boolean bl) {
        this.field2263 = bl;
    }

    public void method1859() {
        for (Setting setting : this.mod.getSettings()) {
            SettingComponent settingComponent;
            if (setting instanceof BooleanSetting) {
                settingComponent = new BooleanSettingComponent(setting);
                this.settingComponents.add(settingComponent);
                settingComponent.method1674();
                continue;
            }
            if (setting instanceof ModeSetting) {
                settingComponent = new ModeSettingComponent(setting);
                this.settingComponents.add(settingComponent);
                settingComponent.method1674();
                continue;
            }
            if (!(setting instanceof NumberSetting)) continue;
            settingComponent = new NumberSettingComponent(setting);
            this.settingComponents.add(settingComponent);
            settingComponent.method1674();
        }
    }

    public void mouseScrolled(double d, double d2, double d3, double d4) {
        if (this.field2269) {
            this.settingComponents.forEach(settingComponent -> settingComponent.mouseScrolled(d, d2, d3, d4));
        }
    }

    private static int method1861(int n, int n2) {
        int n3 = n & 0xFFFFFF;
        return n2 << 24 | n3;
    }

    public void init() {
        this.field2265.method1844(0.0f);
        this.field2268.method1844(0.0f);
        this.settingComponents.forEach(SettingComponent::init);
    }

    public int render(DrawContext ctx, int x, int y, int width, int mX, int mY) {
        this.x = x;
        this.y = y;
        this.width = width;
        TextRenderer fr = MinecraftClient.getInstance().textRenderer;
        boolean bl = BoundaryUtils.inBoundary(mX, mY, x, y, x + width, y + 16);
        this.field2261.method1847(bl ? 1.0f : 0.0f);
        this.field2259.method1847(this.field2262 ? 1.0f : 0.0f);
        this.field2268.method1847(this.field2263 ? 1.0f : 0.0f);
        float f = Class249.method1681(this.field2261.method1845());
        float f2 = Class249.method1681(this.field2259.method1845());
        float f3 = Class249.method1682(this.field2268.method1845());
        MatrixStack matrix = ctx.getMatrices();
        matrix.push();
        float f4 = 1.0f - 0.03f * f2;
        float f5 = 1.0f - 0.06f * f2;
        matrix.translate((float) x + (float) width / 2.0f, (float) y + 8.0f, 0.0f);
        matrix.scale(f4, f5, 1.0f);
        matrix.translate(-((float) x + (float) width / 2.0f), -((float) y + 8.0f), 0.0f);
        int n6 = this.mod.isEnabled() ? -865163265 : 0x6644464A;
        int n7 = ModuleComponent.method1861(0xFFFFFF, (int) (34.0f * f));
        ctx.fill(x + 1, y + 1, x + width - 1, y + 1 + 16 - 2, n6);
        if (f > 0.001f) {
            ctx.fill(x + 1, y + 1, x + width - 1, y + 1 + 16 - 2, n7);
        }
        ctx.drawCenteredTextWithShadow(fr, this.mod.name, x + width / 2, y + 5, -1);
        matrix.pop();
        int n8 = 16;
        if (this.field2269) {
            int n9 = 0;
            for (SettingComponent settingComponent : this.settingComponents) {
                if (!settingComponent.method1670()) continue;
                n9 += settingComponent.method1671(ctx, x + 2, y + 16 + n9 + 1, width - 4, mX, mY);
            }
            this.field2265.method1847(n9 + 5);
            int n10 = (int) this.field2265.method1845();
            if (n10 > 0) {
                int n11 = (int) (51.0f * f3);
                int n12 = (int) (17.0f * f3);
                int n13 = (int) ((1.0f - f3) * 6.0f);
                ctx.fill(x + 1, y + 16 + n13, x + width - 1, y + 16 + n13 + n10, ModuleComponent.method1861(0x222222, n11));
                if (n12 > 0) {
                    ctx.fill(x + 1, y + 16 + n13, x + width - 1, y + 16 + n13 + 1, ModuleComponent.method1861(0xFFFFFF, n12));
                }
            }
            n8 = 16 + n10;
        } else {
            this.field2265.method1847(0.0f);
        }
        return n8;
    }

    public void mouseReleased(double d, double d2, int n) {
        if (BoundaryUtils.inBoundary(d, d2, this.x + 1, this.y + 1, this.x + this.width - 1, this.y + 16) && this.field2262) {
            if (n == 1) {
                this.field2269 = !this.field2269;
            }
            if (n == 0) {
                this.mod.setState(!this.mod.isEnabled());
            }
        }
        this.field2262 = false;
        if (this.field2269) {
            this.settingComponents.forEach(settingComponent -> settingComponent.method1672(d, d2, n));
        }
    }

}
