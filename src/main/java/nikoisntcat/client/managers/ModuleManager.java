package nikoisntcat.client.managers;

import nikoisntcat.AegisClient;
import nikoisntcat.client.events.Event;
import nikoisntcat.client.events.impl.*;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.modules.impl.FastPlaceModule;
import nikoisntcat.client.utils.Class224;
import nikoisntcat.client.utils.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModuleManager extends MinecraftUtil {
    public final List<Module> field2009 = new ArrayList<>();
    public final Map<Class<? extends Module>, Module> field2010 = new HashMap<>();

    public ModuleManager() {
        this.init();
    }

    private void method1480(BiConsumer biConsumer, Event event) {
        for (Module module : this.field2009) {
            try {
                if (!module.method1222()) continue;
                biConsumer.accept(module, event);
            }
            catch (Exception exception) {
                System.out.println("Exception while executing module " + module.name + ": ");
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    private void method1482(BiConsumer biConsumer, CallbackInfo callbackInfo) {
        for (Module module : this.field2009) {
            try {
                if (!module.method1222()) continue;
                biConsumer.accept(module, callbackInfo);
            }
            catch (Exception exception) {
                System.out.println("Exception while executing module " + module.name + ": ");
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    private void method1474(Consumer consumer) {
        for (Module module : this.field2009) {
            try {
                if (!module.method1222()) continue;
                consumer.accept(module);
            }
            catch (Exception exception) {
                System.out.println("Exception while executing module " + module.name + ": ");
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    public void method1476() {
        this.field2010.clear();
        this.field2009.clear();
        this.init();
    }

    private void init() {
        this.addModule(FastPlaceModule.instance);
        System.out.println("ModuleManager loaded!");
    }

    public static Module method1481(String string) {
        Module module = null;
        for (Module module2 : AegisClient.moduleManager.field2009) {
            if (!module2.name.equalsIgnoreCase(string)) continue;
            module = module2;
        }
        return module;
    }

    public static Module method1475(Class clazz) {
        Module module = null;
        for (Module module2 : AegisClient.moduleManager.field2009) {
            if (!module2.getClass().getName().equalsIgnoreCase(clazz.getName())) continue;
            module = module2;
        }
        return module;
    }

    private void addModule(Module module) {
        this.field2010.put(module.getClass(), module);
        this.field2009.add(module);
    }

    public void method1205(Class217 class217) {
        this.method1480(Module::method1205, class217);
    }

    public void method1212(Class212 class212) {
        this.method1480(Module::method1212, class212);
    }

    public void method1199(CallbackInfo callbackInfo) {
        this.method1482(Module::method1199, callbackInfo);
    }

    public void onSlow(SlowdownEvent slowdownEvent) {
        this.method1480(Module::onSlow, slowdownEvent);
    }

    public void onPacketSend(PacketSendEvent packetSendEvent) {
        this.method1480(Module::onPacketSend, packetSendEvent);
    }

    public void onRender3D(Render3DEvent render3DEvent) {
        this.method1480(Module::onRender3D, render3DEvent);
    }

    public void onRender2D(Render2DEvent render2DEvent) {
        this.method1480(Module::method1209, render2DEvent);
    }

    public void method1196() {
        this.method1474(Module::method1196);
    }

    public void method1197() {
        this.method1474(Module::method1197);
    }

    public void method1221(Class211 class211) {
        this.method1480(Module::method1221, class211);
    }

    public void method1208(Class216 class216) {
        this.method1480(Module::method1208, class216);
    }

    public void onMotion(MotionEvent motionEvent) {
        this.method1480(Module::onMotion, motionEvent);
    }

    public void onSetWorld() {
        this.method1474(Module::method1214);
    }

    public void method1210(Class221 class221) {
        for (Module module : this.field2009) {
            try {
                if (module.method1222()) {
                    module.method1210(class221);
                }
                if (module.key != class221.method1430() || Class224.nullCheck() || mc.currentScreen != null) continue;
                module.method1216();
            }
            catch (Exception exception) {
                System.out.println("Exception while executing module " + module.name + ": ");
                exception.printStackTrace();
            }
        }
    }
}