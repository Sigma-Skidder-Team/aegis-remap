package nikoisntcat.client.modules.impl.render;

import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import org.joml.Vector2f;

public class RotationsModule extends Module {
    public Vector2f field1600;
    public Vector2f field1601 = new Vector2f(0.0F, 0.0F);

    public void method1225(PlayerEntityRenderState livingEntity) {
        mc.player.prevPitch = this.field1601.y;
        mc.player.prevHeadYaw = this.field1601.x;
        mc.player.prevBodyYaw = this.field1601.x;
        mc.player.bodyYaw = this.field1600.x;
        mc.player.headYaw = this.field1600.x;
        mc.player.lastRenderPitch = this.field1601.y;
        mc.player.lastRenderYaw = this.field1601.x;
        mc.player.renderPitch = this.field1600.y;
        mc.player.renderYaw = this.field1600.x;
        livingEntity.pitch = this.field1600.y;
        livingEntity.bodyYaw = this.field1600.x;
    }

    public RotationsModule() {
        super("Rotations", 0, Category.RENDER);
        this.field1600 = new Vector2f(0.0F, 0.0F);
    }

    @Override
    public void onMotion(MotionEvent motionEvent) {
        if (motionEvent.method1399() == MotionEvent.Class123.PRE) {
            this.field1601 = this.field1600;
            this.field1600 = new Vector2f(motionEvent.method1410(), motionEvent.method1414());
        }
    }
}
