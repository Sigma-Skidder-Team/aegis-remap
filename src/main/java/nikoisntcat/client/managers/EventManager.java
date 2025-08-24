package nikoisntcat.client.managers;

import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class EventManager {
    public void onSlow(SlowdownEvent slowdownEvent) {
        AegisClient.moduleManager.onSlow(slowdownEvent);
    }

    public void method2010(CallbackInfo callbackInfo) {
        AegisClient.field2321.method1196();
        AegisClient.field2315.method2049();
        AegisClient.field2310.method1443();
        AegisClient.moduleManager.method1199(callbackInfo);
        AegisClient.moduleManager.method1196();
    }

    public void method2011(Class212 class212) {
        //Class226.method1470(class212);
        AegisClient.moduleManager.method1212(class212);
    }

    public void onRender2D(Render2DEvent render2DEvent) {
        AegisClient.moduleManager.onRender2D(render2DEvent);
    }

    public void method2013(Class214 class214) {
        //AegisClient.commandManager.method1880(class214);
    }

    public void method2014(Class217 class217) {
        if (!Class217.field1982) {
            AegisClient.moduleManager.method1205(class217);
        }
        //AegisClient.field2316.method1205(class217);
    }

    public void method2015(Class213 class213) {
        //Class226.method1462(class213);
        AegisClient.field2321.method1503();
    }

    public void onMotion(MotionEvent motionEvent) {
        AegisClient.moduleManager.onMotion(motionEvent);
    }

    public void onRender3D(Render3DEvent render3DEvent) {
        AegisClient.moduleManager.onRender3D(render3DEvent);
    }

    public void onSetWorld() {
        AegisClient.moduleManager.onSetWorld();
    }

    public void onPacketSend(PacketSendEvent packetSendEvent) {
        AegisClient.motionManager.onPacketSend(packetSendEvent);
        AegisClient.moduleManager.onPacketSend(packetSendEvent);
        AegisClient.field2310.onPacketSend(packetSendEvent);
        AegisClient.field2315.onPacketSend(packetSendEvent);
        AegisClient.field2321.onPacketSend(packetSendEvent);
    }

    public void method2020(Class216 event) {
        //AegisClient.field2316.method1208(event);
        AegisClient.moduleManager.method1208(event);
    }

    public void method2021(StrafeEvent event) {
        //AegisClient.field2316.method1221(event);
        AegisClient.moduleManager.method1221(event);
    }

    public void method2022(Class221 event) {
        AegisClient.moduleManager.method1210(event);
    }

    public void method2023() {
        AegisClient.moduleManager.method1197();
    }
}