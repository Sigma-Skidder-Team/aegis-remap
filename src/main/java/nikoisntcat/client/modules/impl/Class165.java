package nikoisntcat.client.modules.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.events.impl.Render3DEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.Setting;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ColorSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.math.TimerUtil;
import nikoisntcat.client.utils.math.Tween;

public class Class165 extends Module {
    public NumberSetting field1603;
    private int field1604 = 0;
    private TimerUtil field1605;
    public static LivingEntity field1606;
    public static LivingEntity field1607;
    public NumberSetting field1608 = new NumberSetting((String)Class165.method1240('\u0007'), 3.0, 8.0, 3.0, 0.1);
    public ColorSetting field1609;
    public boolean field1610 = false;
    public static LivingEntity field1611;
    private LinkedBlockingQueue field1612;
    public NumberSetting field1613;
    private Map field1614;
    private Map field1615;
    private boolean field1616 = false;
    public BooleanSetting field1617;
    public ModeSetting field1618;
    public ModeSetting field1619;
    public NumberSetting field1620 = new NumberSetting((String)Class165.method1240('\b'), 3.0, 16.0, 3.0, 0.1);
    private int field1621 = 0;
    public boolean field1622 = false;
    private boolean field1623 = false;
    private List field1624;
    private int field1625 = 0;
    public NumberSetting field1626;
    public ModeSetting field1627;
    public NumberSetting field1628;
    public static volatile LivingEntity field1629;
    public NumberSetting field1630 = new NumberSetting((String)Class165.method1240('\t'), 0.0, 20.0, 0.0, 1.0);
    public BooleanSetting field1631 = new BooleanSetting((String)Class165.method1240('\n'), false);
    public ModeSetting field1632;

    private native void method1227(Entity var1, Tween var2);

    @Override
    public native void onTick();

    private native boolean method1228(LivingEntity var1);

    private native void method1229();

    private native boolean method1230(Setting var1);

    private native List method1231(Entity var1);

    public native LivingEntity method1232();

    @Override
    public native void onDisable();

    @Override
    public native void onRender3D(Render3DEvent var1);

    @Override
    public native void onEnable();

    @Override
    public native void onMotion(MotionEvent var1);

    private native void method1233();

    public Class165() {
        super((String)Class165.method1240('\u0006'), 0, Category.COMBAT);
        this.field1617 = new BooleanSetting((String)Class165.method1240('\u000b'), false);
        this.field1632 = new ModeSetting((String)Class165.method1240('\f'), (String)Class165.method1240('\r'), List.of((String)Class165.method1240('\r'), (String)Class165.method1240('\u000e')));
        this.field1628 = new NumberSetting((String)Class165.method1240('\u000f'), 0.0, 5.0, 0.0, 1.0);
        this.field1626 = new NumberSetting((String)Class165.method1240('\u0010'), 0.0, 5.0, 0.0, 1.0);
        this.field1619 = new ModeSetting((String)Class165.method1240('\u0011'), (String)Class165.method1240('\u0000'), List.of((String)Class165.method1240('\u0000'), (String)Class165.method1240('\u0003'), (String)Class165.method1240('\u0002'), (String)Class165.method1240('\u0001')));
        this.field1627 = new ModeSetting((String)Class165.method1240('\u0012'), (String)Class165.method1240('\u0013'), List.of((String)Class165.method1240('\u0013'), (String)Class165.method1240('\u0014')));
        this.field1618 = new ModeSetting((String)Class165.method1240('\u0015'), (String)Class165.method1240('\u0000'), List.of((String)Class165.method1240('\u0000'), (String)Class165.method1240('\u0005'), (String)Class165.method1240('\u0004')));
        //this.field1609 = new ColorSetting((String)Class165.method1240('\u0016'), true, this::method1230);
        //this.field1603 = new NumberSetting((String)Class165.method1240('\u0017'), 0.4, 0.7, 0.5, 0.01, this::method1239);
        //this.field1613 = new NumberSetting((String)Class165.method1240('\u0018'), 15.0, 40.0, 25.0, 1.0, this::method1236);
        this.field1624 = new ArrayList();
        this.field1612 = new LinkedBlockingQueue();
        this.field1605 = new TimerUtil();
        this.field1615 = new HashMap();
        this.field1614 = new HashMap();
        if (this.field1609 != null) {
            this.field1609.method1912(new Color(0, 124, 255, 100));
        }
    }

    private native void method1234(List var1, Render3DEvent var2, Entity var3, List var4);

    public native void method1235(boolean var1, boolean var2);

    private native boolean method1236(Setting var1);

    private native void method1237();

    private native void method1238(boolean var1, boolean var2);

    @Override
    public native void onReceivePacket(PacketReceiveEvent var1);

    @Override
    public native void onPreTick();

    private native boolean method1239(Setting var1);

    private static native Object method1240(char var0);
}
