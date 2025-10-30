package nikoisntcat.client.utils.render;

import nikoisntcat.client.utils.math.TimerUtil;

public class Notification {
    public float field2359;
    public boolean field2360;
    public float field2361;
    public boolean field2362;
    public float field2363;
    public float field2364;
    public NotificationState state;
    public String title;
    public TimerUtil field2367 = new TimerUtil();
    public String desc;
    public boolean field2369;
    public String field2370;

    public void method1945(float f) {
        this.field2359 = this.field2363 + f;
    }

    public void setState(NotificationState state) {
        this.state = state;
    }

    public void method1947(float f, float f2) {
        this.field2364 = f - this.method1948();
        this.field2361 = f;
        this.field2359 = f2 + this.method1952();
        this.field2363 = f2;
    }

    public float method1948() {
        return this.field2361 - this.field2364;
    }

    public void method1949(float f) {
        this.field2361 = this.field2364 + f;
    }

    public void method1950(float f, float f2) {
        this.field2361 = f + this.method1948();
        this.field2364 = f;
        this.field2359 = f2 + this.method1952();
        this.field2363 = f2;
    }

    public void method1951(float f, float f2) {
        this.field2361 += f;
        this.field2364 += f;
        this.field2359 += f2;
        this.field2363 += f2;
    }

    public Notification(String string, String title, String desc, boolean bl, boolean bl2, boolean bl3) {
        this.field2370 = string;
        this.state = NotificationState.INIT;
        this.title = title;
        this.desc = desc;
        this.field2362 = bl;
        this.field2360 = bl2;
        this.field2369 = bl3;
    }

    public float method1952() {
        return this.field2359 - this.field2363;
    }

    public void method1953(float f, float f2) {
        float f3 = this.method1948();
        this.field2364 = f - f3 / 2.0f;
        this.field2361 = f + f3 / 2.0f;
        this.field2359 = f2 + this.method1952();
        this.field2363 = f2;
    }
}