package nikoisntcat.client.modules;

import nikoisntcat.AegisClient;
import nikoisntcat.client.events.impl.*;
import nikoisntcat.client.utils.MinecraftUtil;
import nikoisntcat.client.settings.impl.BooleanSetting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
        if (!AegisClient.field2306) {
            if (bl) {
                //Class181.method1302(new Notification(this.name, "Toggled", "Enabled " + this.name, true, true, false));
                this.onEnable();
            } else {
                //Class181.method1302(new Notification(this.name, "Toggled", "Disabled " + this.name, false, true, false));
                this.onDisable();
            }
            AegisClient.moduleManager.field2009.forEach(module -> module.method1219(this));
        }
    }

    public Category getCategory() {
        return category;
    }

    protected Module(String string, int n, boolean bl, Category moduleCategory) {
        this.name = string;
        this.key = n;
        this.setState(bl);
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

    public void onPacketSend(PacketSendEvent event) {
    }

    public void method1199(CallbackInfo callbackInfo) {
    }

    public void onRender3D(Render3DEvent render3DEvent) {
    }

    public void onMotion(MotionEvent motionEvent) {
    }

    public boolean method1222() {
        return this.state && this.field1594;
    }

    public void onSlow(SlowdownEvent event) {
    }

    public void method1219(Module module) {
    }

    public void method1214() {
    }

    public void method1196() {
    }

    public void method1197() {
    }

    public void method1209(Render2DEvent render2DEvent) {
    }

    public void method1208(Class216 class216) {
    }

    public void method1221(StrafeEvent strafeEvent) {
    }

    public void method1205(Class217 class217) {
    }

    public void method1212(Class212 class212) {
    }

    public void method1210(Class221 class221) {
    }

    public void method1216() {
        AegisClient.configManager.method1393();
        this.setState(!this.state);
    }

    public boolean isEnabled() {
        return this.state;
    }
}
