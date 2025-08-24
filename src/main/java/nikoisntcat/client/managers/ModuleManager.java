package nikoisntcat.client.managers;

import nikoisntcat.AegisClient;
import nikoisntcat.client.events.Event;
import nikoisntcat.client.events.impl.*;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.modules.impl.*;
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
    public final List<Module> modules = new ArrayList<>();
    public final Map<Class<? extends Module>, Module> field2010 = new HashMap<>();

    public ModuleManager() {
        init();
    }

    public static List<Module> method1479(Category moduleCategory) {
        ArrayList<Module> arrayList = new ArrayList<>();
        for (Module module : AegisClient.moduleManager.modules) {
            if (module.getCategory() != moduleCategory) continue;
            arrayList.add(module);
        }
        return arrayList;
    }

    private <T extends Event> void call(BiConsumer<Module, T> biConsumer, T event) {
        for (Module module : this.modules) {
            try {
                if (!module.method1222()) continue;
                biConsumer.accept(module, event);
            } catch (Exception exception) {
                System.out.println("Exception while executing module " + module.name + ": ");
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    private void onTick(BiConsumer<Module, CallbackInfo> biConsumer, CallbackInfo callbackInfo) {
        for (Module module : this.modules) {
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

    private void call(Consumer<Module> consumer) {
        for (Module module : this.modules) {
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
        this.modules.clear();
        this.init();
    }

    public void init() {
        this.add(new SprintModule());
        //this.add(new Class174());
        //this.add(new Class202());
        //this.add(new Class171());
        //this.add(new Class187());
        //this.add(new Class175());
        //this.add(new Class194());
        //this.add(new Class169());
        //this.add(new Class165());
        //this.add(new Class197());
        this.add(new ClickGuiModule());
        //this.add(new Class182());
        //this.add(new Class199());
        //this.add(new Class164());
        this.add(ClientSettingsModule.instance);
        this.add(new Class166());
        this.add(new nrsModule());
        this.add(AntiBotModule.instance);
        //this.add(new Class179());
       // this.add(new Class172());
       // this.add(new Class180());
       // this.add(new Class196());
        //this.add(new Class200());
        //this.add(new Class188());
        //this.add(new Class203());
       // this.add(new Class189());
        //this.add(new Class184());
        this.add(new Class181());
        //this.add(new Class183());
        //this.add(Class178.field1737);
        //this.add(new Class167());
        //this.add(new Class170());
        //this.add(new Class173());
        //this.add(new Class192());
        //this.add(new Class177());
        //this.add(new Class191());
        //this.add(new Class198());
        //this.add(new Class193());
        //this.add(new Class168());
        this.add(new NoFovModule());
        this.add(FastPlaceModule.instance);
        System.out.println("ModuleManager loaded!");
    }

    public static Module method1481(String string) {
        Module module = null;
        for (Module module2 : AegisClient.moduleManager.modules) {
            if (!module2.name.equalsIgnoreCase(string)) continue;
            module = module2;
        }
        return module;
    }

    public static Module method1475(Class clazz) {
        Module module = null;
        for (Module module2 : AegisClient.moduleManager.modules) {
            if (!module2.getClass().getName().equalsIgnoreCase(clazz.getName())) continue;
            module = module2;
        }
        return module;
    }

    private void add(Module module) {
        this.field2010.put(module.getClass(), module);
        this.modules.add(module);
    }

    public void onMoveInput(MoveInputEvent event) {
        this.call(Module::onMoveInput, event);
    }

    public void onReceivePacket(PacketReceiveEvent event) {
        this.call(Module::onReceivePacket, event);
    }

    public void onTick(CallbackInfo ci) {
        this.onTick(Module::onTick, ci);
    }

    public void onSlow(SlowdownEvent event) {
        this.call(Module::onSlow, event);
    }

    public void onSendPacket(PacketSendEvent event) {
        this.call(Module::onSendPacket, event);
    }

    public void onRender3D(Render3DEvent event) {
        this.call(Module::onRender3D, event);
    }

    public void onRender2D(Render2DEvent event) {
        this.call(Module::onRender2D, event);
    }

    public void onTick() {
        this.call(Module::onTick);
    }

    public void onPreTick() {
        this.call(Module::onPreTick);
    }

    public void onStrafe(StrafeEvent event) {
        this.call(Module::onStrafe, event);
    }

    public void onJump(JumpEvent event) {
        this.call(Module::onJump, event);
    }

    public void onMotion(MotionEvent event) {
        this.call(Module::onMotion, event);
    }

    public void onSetWorld() {
        this.call(Module::onSetWorld);
    }

    public void onKey(KeyEvent event) {
        for (Module module : this.modules) {
            try {
                if (module.method1222()) {
                    module.onKey(event);
                }
                if (module.key != event.getKey() || Class224.nullCheck() || mc.currentScreen != null) continue;
                module.toggle();
            }
            catch (Exception exception) {
                System.out.println("Exception while executing module " + module.name + ": ");
                exception.printStackTrace();
            }
        }
    }
}