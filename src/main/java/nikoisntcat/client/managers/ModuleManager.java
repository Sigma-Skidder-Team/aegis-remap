package nikoisntcat.client.managers;

import io.github.markgg.JniCaller;
import nikoisntcat.AegisClient;
import nikoisntcat.client.events.Event;
import nikoisntcat.client.events.impl.*;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.modules.impl.combat.KillAuraModule;
import nikoisntcat.client.modules.impl.misc.*;
import nikoisntcat.client.modules.impl.move.*;
import nikoisntcat.client.modules.impl.render.*;
import nikoisntcat.client.modules.impl.world.*;
import nikoisntcat.client.utils.PlayerUtil;
import nikoisntcat.client.utils.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ModuleManager extends MinecraftUtil {
    public final List<Module> modules;
    public final Map<Class<? extends Module>, Module> moduleMap = new HashMap<>();

    private static JniCaller jniCaller;

    public ModuleManager() {
        this.modules = new ArrayList<>();
        try {
            jniCaller = new JniCaller(new File("Aegis-obf.jar"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }

    public static List<Module> findInCategory(Category moduleCategory) {
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
        // literally the same as call(biConsumer, callbackInfo);
        // :broken_heart:
        for (Module module : this.modules) {
            try {
                if (!module.method1222()) continue;
                biConsumer.accept(module, callbackInfo);
            } catch (Exception exception) {
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
            } catch (Exception exception) {
                System.out.println("Exception while executing module " + module.name + ": ");
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }
    }

    public void method1476() {
        this.moduleMap.clear();
        this.modules.clear();
        this.init();
    }

    public void init() {
        this.add(new SprintModule());
        this.add(new GetLagModule());
        this.add(new WeatherChangerModule());
        this.add(new ProjectileTrailModule());
        this.add(new FirstModuleModule());
        this.add(new AutoToolModule());
        this.add(new ArrayListModule());
        this.add(new KillAuraModule());
        //Velo
        //Scaffold
        this.add(new ClickGuiModule());
        this.add(new TargetsModule());
        this.add(new TeamsModule());
        this.add(new RotationsModule());
        this.add(ClientSettingsModule.instance);
        this.add(new DisablerModule());
        this.add(AntiBotModule.instance);
        this.add(new FuckerModule());
        this.add(new ItemManagerModule());
        this.add(new NoRotationSetModule());
        this.add(new ESPModule());
        this.add(new SpeedModule());
        this.add(new NoFallModule());
        this.add(new HighJumpModule());
        this.add(new InventoryMoveModule());
        //TODO: Another native module
        //this.add(new Class184());
        this.add(new NotificationModule());
        this.add(new NoSlowModule());
        this.add(AnimationModule.instance);
        this.add(new BlockESPModule());
        this.add(new NameTagsModule());
        this.add(new LongJumpModule());
        this.add(new NoJumpDelayModule());
        this.add(new LogoModule());
        this.add(new BlinkModule());
        this.add(new StuckModule());
        this.add(new ProjectilesModule());
        //TODO: Another native module
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
        this.moduleMap.put(module.getClass(), module);
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
                if (module.key != event.getKey() || PlayerUtil.nullCheck() || mc.currentScreen != null) continue;
                module.toggle();
            } catch (Exception exception) {
                System.out.println("Exception while executing module " + module.name + ": ");
                exception.printStackTrace();
            }
        }
    }
}