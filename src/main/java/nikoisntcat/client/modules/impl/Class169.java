package nikoisntcat.client.modules.impl;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.events.impl.MoveInputEvent;
import nikoisntcat.client.events.impl.PacketReceiveEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.settings.Setting;
import nikoisntcat.client.settings.impl.BooleanSetting;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class Class169 extends Module {
    private int field1659;
    public NumberSetting field1660;
    private boolean field1661 = false;
    public BooleanSetting field1662;
    private boolean field1663 = false;
    private int field1664;
    public NumberSetting field1665;
    public BooleanSetting field1666;
    public BooleanSetting field1667;
    public BooleanSetting field1668;
    public BooleanSetting field1669;
    public NumberSetting field1670;
    public ModeSetting field1671 = new ModeSetting((String)Class169.method1259('\u0005'), (String)Class169.method1259('\u0001'), List.of((String)Class169.method1259('\u0001'), (String)Class169.method1259('\u0000'), (String)Class169.method1259('\u0002')));
    private int field1672;
    public BooleanSetting field1673;
    public boolean field1674 = false;
    public BooleanSetting field1675;
    private LinkedBlockingQueue field1676;
    public BooleanSetting field1677;
    public BooleanSetting field1678;
    public BooleanSetting field1679;
    private boolean field1680 = false;
    public static boolean $skidonion$554265449;

    private native Boolean method1247();

    private native boolean method1248(Setting var1);

    @Override
    public native void onMoveInput(MoveInputEvent var1);

    private native Supplier method1249();

    private native boolean method1250(Setting var1);

    private native boolean method1251(Setting var1);

    @Override
    public native void onTick(CallbackInfo var1);

    @Override
    public native void onPreTick();

    private native void method1252();

    @Override
    public native void onReceivePacket(PacketReceiveEvent var1);

    private native boolean method1253(Setting var1);

    public Class169() {
        super((String)Class169.method1259('\u0004'), 0, false, Category.COMBAT);
        //this.field1660 = new NumberSetting((String)Class169.method1259('\u0006'), 0.0, 1.0, 0.0, 0.01, this::method1257);
        //this.field1665 = new NumberSetting((String)Class169.method1259('\u0007'), 0.0, 1.0, 0.0, 0.01, this::method1251);
        //this.field1679 = new BooleanSetting((String)Class169.method1259('\b'), true, this::method1250);
        this.field1669 = new BooleanSetting((String)Class169.method1259('\t'), false);
        this.field1668 = new BooleanSetting((String)Class169.method1259('\n'), false);
        this.field1666 = new BooleanSetting((String)Class169.method1259('\u000b'), false);
        this.field1675 = new BooleanSetting((String)Class169.method1259('\f'), true);
        //this.field1670 = new NumberSetting((String)Class169.method1259('\r'), 0.0, 100.0, 0.0, 1.0, this::method1254);
        this.field1677 = new BooleanSetting((String)Class169.method1259('\u000e'), false);
        //this.field1662 = new BooleanSetting((String)Class169.method1259('\u000f'), false, this::method1256);
        //this.field1673 = new BooleanSetting((String)Class169.method1259('\u0010'), false, this::method1248);
        //this.field1678 = new BooleanSetting((String)Class169.method1259('\u0011'), false, this::method1253);
        //this.field1667 = new BooleanSetting((String)Class169.method1259('\u0012'), false, this::method1255);
        this.field1676 = new LinkedBlockingQueue();
    }

    private native boolean method1254(Setting var1);

    private native boolean method1255(Setting var1);

    private native boolean method1256(Setting var1);

    @Override
    public native void onDisable();

    private native boolean method1257(Setting var1);

    public native void method1258();

    @Override
    public native void onEnable();

    @Override
    public native void onMotion(MotionEvent var1);

    private static native Object method1259(char var0);
}
