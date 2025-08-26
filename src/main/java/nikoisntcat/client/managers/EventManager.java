package nikoisntcat.client.managers;

import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.*;
import nikoisntcat.client.utils.MovementUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class EventManager {
    public void onSlow(SlowdownEvent slowdownEvent) {
        AegisClient.moduleManager.onSlow(slowdownEvent);
    }

    public void onTick(CallbackInfo ci) {
        AegisClient.packetUtil.onTick();
        AegisClient.blinkUtil.onTick();
        AegisClient.field2310.onTick();
        AegisClient.moduleManager.onTick(ci);
        AegisClient.moduleManager.onTick();
    }

    public void onReceivePacket(PacketReceiveEvent event) {
        MovementUtil.method1470(event);
        AegisClient.moduleManager.onReceivePacket(event);
    }

    public void onRender2D(Render2DEvent event) {
        AegisClient.moduleManager.onRender2D(event);
    }

    public void onChat(ChatMessageEvent event) {
        AegisClient.commandManager.onChat(event);
    }

    public void onMoveInput(MoveInputEvent event) {
        if (!MoveInputEvent.field1982) {
            AegisClient.moduleManager.onMoveInput(event);
        }
        AegisClient.rotationUtil.onMoveInput(event);
    }

    public void method2015(BlinkEvent event) {
        MovementUtil.shouldBlink(event);
        AegisClient.packetUtil.markMovementPacket();
    }

    public void onMotion(MotionEvent event) {
        AegisClient.moduleManager.onMotion(event);
    }

    public void onRender3D(Render3DEvent event) {
        AegisClient.moduleManager.onRender3D(event);
    }

    public void onSetWorld() {
        AegisClient.moduleManager.onSetWorld();
    }

    public void onSendPacket(PacketSendEvent event) {
        AegisClient.motionManager.onSendPacket(event);
        AegisClient.moduleManager.onSendPacket(event);
        AegisClient.field2310.onSendPacket(event);
        AegisClient.blinkUtil.onSendPacket(event);
        AegisClient.packetUtil.onSendPacket(event);
    }

    public void onJump(JumpEvent event) {
        AegisClient.rotationUtil.onJump(event);
        AegisClient.moduleManager.onJump(event);
    }

    public void onStrafe(StrafeEvent event) {
        AegisClient.rotationUtil.onStrafe(event);
        AegisClient.moduleManager.onStrafe(event);
    }

    public void onKey(KeyEvent event) {
        AegisClient.moduleManager.onKey(event);
    }

    public void onPreTick() {
        AegisClient.moduleManager.onPreTick();
    }
}