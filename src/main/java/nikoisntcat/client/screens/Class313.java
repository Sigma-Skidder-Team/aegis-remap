package nikoisntcat.client.screens;

import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import nikoisntcat.client.utils.math.Class250;

public class Class313 {
    private int field2393;
    public boolean field2394 = false;
    private int field2395;
    public final String field2396;
    private int field2397;

    public void method1971(DrawContext context, int x, int y, int width, int mouseX, int mouseY) {
        this.field2393 = x;
        this.field2397 = y;
        this.field2395 = width;
        if (Class250.method1684(mouseX, mouseY, x, y + 1, x + width, y + 9)) {
            context.fill(x, y - 1, x + width + 2, y + 11, new Color(255, 255, 255, 50).getRGB());
        }
        context.drawText(MinecraftClient.getInstance().textRenderer, this.field2396, x + 5, y + 2, -1, false);
    }

    public boolean method1972(double mouseX, double mouseY, int button) {
        if (Class250.method1684(mouseX, mouseY, this.field2393, this.field2397 + 1, this.field2393 + this.field2395, this.field2397 + 9)) {
            return true;
        }
        this.field2394 = false;
        return false;
    }

    public Class313(String name) {
        this.field2396 = name;
    }

    public void method1973(double mouseX, double mouseY, int button) {
        if (Class250.method1684(mouseX, mouseY, this.field2393, this.field2397 + 1, this.field2393 + this.field2395, this.field2397 + 9)) {
            this.field2394 = true;
        }
    }
}
