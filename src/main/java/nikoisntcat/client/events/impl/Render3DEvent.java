package nikoisntcat.client.events.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3i;
import nikoisntcat.client.events.Event;
import nikoisntcat.client.utils.render.RenderUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Render3DEvent extends Event {
    private final MatrixStack field1984;
    private final List<Vec3i> field1985;
    private final Map<Vec3i, Color> field1986 = new HashMap<>();
    private final Camera field1987;
    private final float field1988;


    public void method1422() {
        if (!this.field1986.isEmpty()) {
            BufferBuilder var2 = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            //this.field1985.forEach(box -> RenderUtil.method1583(var2, box, this.field1984.peek().getPositionMatrix(), this.field1986.get(box), true));
            BuiltBuffer var3 = var2.endNullable();
            if (var3 != null) {
                BufferRenderer.drawWithGlobalProgram(var3);
            }

            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
        }
    }

    public Render3DEvent(Camera class_41842, MatrixStack class_45872, float f) {
        this.field1985 = new ArrayList();
        this.field1988 = f;
        this.field1987 = class_41842;
        this.field1984 = class_45872;
    }

    public float method1423() {
        return this.field1988;
    }

    public void method1425(Vec3i class_2382, Color color) {
        this.field1985.add(class_2382);
        this.field1986.put(class_2382, color);
    }

    public Camera method1426() {
        return this.field1987;
    }

    public MatrixStack method1427() {
        return this.field1984;
    }
}
