package nikoisntcat.client.modules;

import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.*;
import nikoisntcat.client.modules.impl.render.NotificationModule;
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

    public void setState(boolean enabled) {
        if (this.state == enabled) {
            return;
        }
        this.state = enabled;
        if (enabled) {
            NotificationModule.addNotification(new Notification(this.name, "Toggled", "Enabled " + this.name, true, true, false));
            this.onEnable();
        } else {
            NotificationModule.addNotification(new Notification(this.name, "Toggled", "Disabled " + this.name, false, true, false));
            this.onDisable();
        }
        AegisClient.moduleManager.modules.forEach(module -> module.onToggle(this));
    }

    public Category getCategory() {
        return category;
    }

    protected Module(String string, int key, boolean enabled, Category category) {
        this.name = string;
        this.key = key;
        this.state = enabled;
        this.category = category;
    }

    public Module(String name, String desc, int key, Category category) {
        this.name = name;
        this.key = key;
        this.category = category;
        this.describe = desc;
    }

    public Module(String string, int key, Category category) {
        this.name = string;
        this.key = key;
        this.category = category;
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

    public void onToggle(Module module) {
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
        List<Setting> settings = new ArrayList<>();
        try {
            for (Field field : this.getClass().getFields()) {
                field.setAccessible(true);
                Object value = field.get(this);
                if (value instanceof Setting set) {
                    settings.add(set);
                }
                if (!(value instanceof ColorSetting clr)) continue;
                settings.addAll(List.of(clr.r, clr.g, clr.b, clr.a));
            }
            settings.sort(Comparator.comparing(Setting::getName));
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return settings;
    }

    public int getDisplayLength() {
        String tag = this.getTag();
        return tag != null ? this.name.length() + tag.length() : this.name.length();
    }

    public String getTag() {
        return "";
    }
}
