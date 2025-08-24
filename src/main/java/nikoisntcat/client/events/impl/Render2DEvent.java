package nikoisntcat.client.events.impl;


import net.minecraft.client.gui.DrawContext;
import nikoisntcat.client.events.Event;

public class Render2DEvent
extends Event {
    private float field1989;
    private DrawContext field1990;

    public DrawContext getDrawContext() {
        return this.field1990;
    }

    public Render2DEvent(float f, DrawContext class_3322) {
        this.field1989 = f;
        this.field1990 = class_3322;
    }

    public float method1429() {
        return this.field1989;
    }
}
