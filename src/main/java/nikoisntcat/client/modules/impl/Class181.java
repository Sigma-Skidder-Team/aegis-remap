package nikoisntcat.client.modules.impl;

import nikoisntcat.client.events.impl.Render2DEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
import nikoisntcat.client.screens.Class234;
import nikoisntcat.client.screens.Class274;
import nikoisntcat.client.screens.Class276;
import nikoisntcat.client.settings.impl.ModeSetting;
import nikoisntcat.client.settings.impl.NumberSetting;
import nikoisntcat.client.utils.math.Class125;
import nikoisntcat.client.utils.math.Class272;
import nikoisntcat.client.utils.render.Notification;
import nikoisntcat.client.utils.render.NotificationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Class181
extends Module {
    public NumberSetting field1762;
    public ModeSetting mode = new ModeSetting("Mode", "A", List.of("A", "B"));
    public NumberSetting field1764;
    public NumberSetting field1765;
    private static final List<Notification> field1766 = new ArrayList<>();
    private static final Map<Notification, ArrayList<Class272>> field1767 = new HashMap<>();
    private int field1768 = 0;
    public NumberSetting field1769 = new NumberSetting("PercentMS", 1000.0, 2000.0, 1.0, 1.0, e -> this.mode.getValue().equals("A"));
    static Object field1770;

    @Override
    public void onEnable() {
        field1766.clear();
        field1767.clear();
    }

    public static void addNotification(Notification notification) {
        field1766.add(notification);
        field1767.put(notification, new ArrayList<>());
    }

    @Override
    public void onRender2D(Render2DEvent event) {
        ArrayList<Notification> arrayList = new ArrayList<>();
        for (Notification notification : field1767.keySet()) {
            Class272 class272;
            ArrayList<Class272> arrayList2 = field1767.get(notification);
            float f = mc.getWindow().getScaledWidth();
            float f2 = mc.getWindow().getScaledHeight();
            String string = notification.field2366;
            String string2 = "";
            String string3 = "";
            if (this.mode.getValue().equals("A")) {
                if (notification.field2360) {
                    string2 = notification.field2362 ? "\uE901" : "\uE900";
                    string3 = notification.field2370;
                } else if (notification.field2369) {
                    string2 = "\uE902";
                    string3 = notification.field2368;
                } else {
                    string2 = "\uE903";
                    string3 = notification.field2368;
                }
            }
            float f3 = (int)this.field1764.getValue() * field1766.indexOf(notification);
            float f4 = notification.field2363 - (f2 - f3 - 50.0f);
            if (notification.field2365 != NotificationType.INIT) {
                class272 = (Class272)arrayList2.get(2);
                if (this.field1768 != field1766.size()) {
                    class272.method1768();
                }
            }
            switch (notification.field2365) {
                case INIT: {
                    arrayList2.clear();
                    notification.method1950(f, f2);
                    float f5 = (float)(Class234.method1564(event.getDrawContext(), (int)notification.field2364, (int)notification.field2363, string3, string, string2, 0.0f) / 95.0);
                    arrayList2.add(new Class276((int)((float)((int)this.field1769.getValue()) * f5), 1.0));
                    arrayList2.add(new Class274((int)this.field1765.getValue(), 1.0));
                    arrayList2.add(new Class276((int)this.field1762.getValue(), 1.0));
                    notification.field2365 = NotificationType.APPEAR;
                    break;
                }
                case APPEAR: {
                    Class272 class2722 = (Class272)arrayList2.get(1);
                    class272 = (Class272)arrayList2.get(2);
                    double d = Class234.method1564(event.getDrawContext(), (int)notification.field2364, (int)notification.field2363, string3, string, string2, class2722.method1770().floatValue());
                    notification.method1950((float)((double)f - d * (double)class2722.method1770().floatValue()), notification.field2363 - class272.method1770().floatValue() * f4);
                    if (!class2722.method1760(Class125.field1464)) break;
                    ((Class272)arrayList2.get(0)).method1768();
                    notification.field2365 = NotificationType.SHOWING;
                    break;
                }
                case DISAPPEAR: {
                    Class272 class2722 = (Class272)arrayList2.get(1);
                    class272 = (Class272)arrayList2.get(2);
                    double d = Class234.method1564(event.getDrawContext(), (int)notification.field2364, (int)notification.field2363, string3, string, string2, 1.0f - ((Class272)arrayList2.get(0)).method1770().floatValue());
                    notification.method1950((float)((double)f - d * (double)class2722.method1770().floatValue()), notification.field2363 - class272.method1770().floatValue() * f4);
                    if (!class2722.method1760(Class125.field1465)) break;
                    arrayList.add(notification);
                    break;
                }
                case SHOWING: {
                    Class272 class2722 = (Class272)arrayList2.get(1);
                    class272 = (Class272)arrayList2.get(2);
                    notification.method1950(notification.field2364, (int)((float)((int)notification.field2363) - class272.method1770().floatValue() * f4));
                    Class234.method1564(event.getDrawContext(), (int)notification.field2364, (int)notification.field2363, string3, string, string2, 1.0f - ((Class272)arrayList2.get(0)).method1770().floatValue());
                    if (!((Class272)arrayList2.get(0)).method1760(Class125.field1464)) break;
                    notification.field2365 = NotificationType.DISAPPEAR;
                    class2722.method1769(Class125.field1465);
                }
            }
        }
        this.field1768 = field1767.size();
        arrayList.forEach(e -> {
            field1766.remove(e);
            field1767.remove(e);
        });
    }

    public Class181() {
        super("Notification", 0, Category.RENDER);
        this.field1765 = new NumberSetting("TotalMS", 1000.0, 2000.0, 1.0, 1.0, e -> this.mode.getValue().equals("A"));
        this.field1762 = new NumberSetting("UpMS", 1000.0, 2000.0, 1.0, 1.0, e -> this.mode.getValue().equals("A"));
        this.field1764 = new NumberSetting("Space", 30.0, 100.0, 1.0, 1.0, e -> this.mode.getValue().equals("A"));
    }
}
