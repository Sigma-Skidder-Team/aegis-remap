package nikoisntcat.client.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import nikoisntcat.client.settings.Setting;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.math.Class249;
import nikoisntcat.client.utils.math.Class250;
import nikoisntcat.client.utils.math.Class288;
import nikoisntcat.client.modules.Module;

import java.util.ArrayList;
import java.util.List;

public class Class291 {
    int field2258;
    private final Class288 field2259;
    int field2260;
    private final Class288 field2261;
    private boolean field2262 = false;
    private boolean field2263 = true;
    public Module field2264;
    private final Class288 field2265;
    int field2266;
    public List<Class245> field2267 = new ArrayList();
    private final Class288 field2268;
    public boolean field2269 = false;

    public void mouseClicked(double d, double d2, int n) {
        if (Class250.method1684(d, d2, this.field2260 + 1, this.field2258 + 1, this.field2260 + this.field2266 - 1, this.field2258 + 16) && (n == 1 || n == 0)) {
            this.field2262 = true;
        }
        if (this.field2269) {
            this.field2267.forEach(class245 -> class245.method1668(d, d2, n));
        }
    }

    public Class291(Module module) {
        this.field2265 = new Class288(3.2f, 0.0f);
        this.field2261 = new Class288(18.0f, 0.0f);
        this.field2259 = new Class288(20.0f, 0.0f);
        this.field2268 = new Class288(10.0f, 0.0f);
        this.field2264 = module;
    }

    public int method1855() {
        int n = MinecraftClient.getInstance().textRenderer.getWidth(this.field2264.name) + 20;
        for (Class245 class245 : this.field2267) {
            n = Math.max(n, class245.method1673());
        }
        return n;
    }

    public void mouseDragged(double d, double d2, int n, double d3, double d4) {
        if (this.field2269) {
            this.field2267.forEach(class245 -> class245.mouseDragged(d, d2, n, d3, d4));
        }
    }

    public void method1857(boolean bl) {
        this.field2263 = bl;
    }

    public void method1859() {
        for (Setting setting : this.field2264.getSettings()) {
            Class245 class245;
            if (setting instanceof BooleanSetting) {
                class245 = new Class248(setting);
                this.field2267.add(class245);
                class245.method1674();
                continue;
            }
            if (setting instanceof ModeSetting) {
                class245 = new Class247(setting);
                this.field2267.add(class245);
                class245.method1674();
                continue;
            }
            if (!(setting instanceof NumberSetting)) continue;
            class245 = new Class246(setting);
            this.field2267.add(class245);
            class245.method1674();
        }
    }

    public void mouseScrolled(double d, double d2, double d3, double d4) {
        if (this.field2269) {
            this.field2267.forEach(class245 -> class245.mouseScrolled(d, d2, d3, d4));
        }
    }

    private static int method1861(int n, int n2) {
        int n3 = n & 0xFFFFFF;
        return n2 << 24 | n3;
    }

    public void init() {
        this.field2265.method1844(0.0f);
        this.field2268.method1844(0.0f);
        this.field2267.forEach(Class245::init);
    }

    public int render(DrawContext ctx, int n, int n2, int n3, int n4, int n5) {
        this.field2260 = n;
        this.field2258 = n2;
        this.field2266 = n3;
        TextRenderer fr = MinecraftClient.getInstance().textRenderer;
        boolean bl = Class250.method1684(n4, n5, n, n2, n + n3, n2 + 16);
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
        matrix.translate((float) n + (float) n3 / 2.0f, (float) n2 + 8.0f, 0.0f);
        matrix.scale(f4, f5, 1.0f);
        matrix.translate(-((float) n + (float) n3 / 2.0f), -((float) n2 + 8.0f), 0.0f);
        int n6 = this.field2264.isEnabled() ? -865163265 : 0x6644464A;
        int n7 = Class291.method1861(0xFFFFFF, (int) (34.0f * f));
        ctx.fill(n + 1, n2 + 1, n + n3 - 1, n2 + 1 + 16 - 2, n6);
        if (f > 0.001f) {
            ctx.fill(n + 1, n2 + 1, n + n3 - 1, n2 + 1 + 16 - 2, n7);
        }
        ctx.drawCenteredTextWithShadow(fr, this.field2264.name, n + n3 / 2, n2 + 5, -1);
        matrix.pop();
        int n8 = 16;
        if (this.field2269) {
            int n9 = 0;
            for (Class245 class245 : this.field2267) {
                if (!class245.method1670()) continue;
                n9 += class245.method1671(ctx, n + 2, n2 + 16 + n9 + 1, n3 - 4, n4, n5);
            }
            this.field2265.method1847(n9 += 5);
            int n10 = (int) this.field2265.method1845();
            if (n10 > 0) {
                int n11 = (int) (51.0f * f3);
                int n12 = (int) (17.0f * f3);
                int n13 = (int) ((1.0f - f3) * 6.0f);
                ctx.fill(n + 1, n2 + 16 + n13, n + n3 - 1, n2 + 16 + n13 + n10, Class291.method1861(0x222222, n11));
                if (n12 > 0) {
                    ctx.fill(n + 1, n2 + 16 + n13, n + n3 - 1, n2 + 16 + n13 + 1, Class291.method1861(0xFFFFFF, n12));
                }
            }
            n8 = 16 + n10;
        } else {
            this.field2265.method1847(0.0f);
        }
        return n8;
    }

    public void mouseReleased(double d, double d2, int n) {
        if (Class250.method1684(d, d2, this.field2260 + 1, this.field2258 + 1, this.field2260 + this.field2266 - 1, this.field2258 + 16) && this.field2262) {
            if (n == 1) {
                boolean bl = this.field2269 = !this.field2269;
            }
            if (n == 0) {
                this.field2264.setState(!this.field2264.isEnabled());
            }
        }
        this.field2262 = false;
        if (this.field2269) {
            this.field2267.forEach(class245 -> class245.method1672(d, d2, n));
        }
    }

}
