package nikoisntcat.client.events.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.listener.ServerHandshakePacketListener;
import net.minecraft.network.packet.c2s.play.UpdateCommandBlockC2SPacket;
import net.minecraft.network.packet.c2s.query.QueryPingC2SPacket;
import net.minecraft.util.math.Vec3i;
import nikoisntcat.client.events.Event;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Render3DEvent extends Event {
    private final MatrixStack field1984;
    private final List field1985;
    private final Map field1986 = new HashMap();
    private final Camera field1987;
    private final float field1988;


    public void method1422() {
        if (!this.field1986.isEmpty()) {
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            /*
            this.field1985.forEach(class_2382 -> Class234.method1583(class_2872, class_2382, this.field1984.method_23760().method_23761(), (Color)this.field1986.get(class_2382), true));
            BuiltBuffer class_98012 = class_2872.method_60794();
            if (class_98012 != null) {
                class_286.method_43433((class_9801)class_98012);
            }

             */
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
