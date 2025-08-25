package nikoisntcat.client.modules;

import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.*;
import nikoisntcat.client.modules.impl.NotificationModule;
import nikoisntcat.client.settings.Setting;
import nikoisntcat.client.settings.impl.ColorSetting;
import nikoisntcat.client.utils.MinecraftUtil;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.utils.render.Notification;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Module extends MinecraftUtil {
    public BooleanSetting hide = new BooleanSetting("Hide", false);
    public String name, describe = "";
    public int key;
    private Category category;

    public boolean field1594 = true;
    private boolean state = false;

    public void setKey(int n) {
        this.key = n;
    }

    public void setState(boolean bl) {
        if (this.state == bl) {
            return;
        }
        this.state = bl;
        if (bl) {
            NotificationModule.addNotification(new Notification(this.name, "Toggled", "Enabled " + this.name, true, true, false));
            this.onEnable();
        } else {
            NotificationModule.addNotification(new Notification(this.name, "Toggled", "Disabled " + this.name, false, true, false));
            this.onDisable();
        }
        AegisClient.moduleManager.modules.forEach(module -> module.unusedMethod(this));
    }

    public Category getCategory() {
        return category;
    }

    protected Module(String string, int n, boolean bl, Category moduleCategory) {
        this.name = string;
        this.key = n;
        this.state = bl;
        this.category = moduleCategory;
    }

    public Module(String string, String string2, int n, Category moduleCategory) {
        this.name = string;
        this.key = n;
        this.category = moduleCategory;
        this.describe = string2;
    }

    public Module(String string, int n, Category moduleCategory) {
        this.name = string;
        this.key = n;
        this.category = moduleCategory;
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public boolean method1222() {
        return this.state && this.field1594;
    }

    public void onSendPacket(PacketSendEvent event) {
    }

    public void onReceivePacket(PacketReceiveEvent event) {
    }

    public void onTick(CallbackInfo ci) {
    }

    public void onRender3D(Render3DEvent event) {
    }

    public void onMotion(MotionEvent event) {
    }

    public void onSlow(SlowdownEvent event) {
    }

    //nice
    public void unusedMethod(Module module) {
    }

    public void onSetWorld() {
    }

    public void onTick() {
    }

    public void onPreTick() {
    }

    public void onRender2D(Render2DEvent event) {
    }

    public void onJump(JumpEvent event) {
    }

    public void onStrafe(StrafeEvent event) {
    }

    public void onMoveInput(MoveInputEvent event) {
    }

    public void onKey(KeyEvent event) {
    }

    public void toggle() {
        AegisClient.configManager.save();
        this.setState(!this.state);
    }

    public boolean isEnabled() {
        return this.state;
    }

    public List<Setting> getSettings() {
        List<Setting> arrayList = new ArrayList<Setting>();
        try {
            for (Field field : this.getClass().getFields()) {
                Object object;
                field.setAccessible(true);
                Object object2 = field.get(this);
                if (object2 instanceof Setting) {
                    object = (Setting)object2;
                    arrayList.add((Setting)object);
                }
                if (!((object2 = field.get(this)) instanceof ColorSetting)) continue;
                object = (ColorSetting)object2;
                arrayList.addAll(List.of(((ColorSetting)object).field2327, ((ColorSetting)object).field2324, ((ColorSetting)object).field2328, ((ColorSetting)object).field2326));
            }
            arrayList.sort(Comparator.comparing(Setting::getName));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return arrayList;
    }
}
