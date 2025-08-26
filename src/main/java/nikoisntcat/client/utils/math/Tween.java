package nikoisntcat.client.utils.math;


public abstract class Tween {
    protected int field2163;
    private static int field2164;
    protected TweenType field2165;
    public TimerUtil timer = new TimerUtil();
    protected double field2167;

    public void method1755(double endPoint) {
        this.field2167 = endPoint;
    }

    public void method1756() {
        this.method1769(this.field2165.method1006());
    }

    public void method1757(int duration) {
        this.field2163 = duration;
    }

    public double method1758() {
        return this.field2167;
    }

    protected boolean method1759() {
        return false;
    }

    public boolean method1760(TweenType state) {
        return this.method1766() && this.field2165.equals(state);
    }

    public double method1761() {
        return 1.0 - (double)this.timer.systemTimeDiff() / (double)this.field2163 * this.field2167;
    }

    public static void setDelta(int delta) {
        field2164 = delta;
    }

    public static float method1763(float current, float end, float smoothSpeed, float minSpeed) {
        float f = (end - current) * smoothSpeed;
        if (f > 0.0f) {
            f = Math.max(minSpeed, f);
            f = Math.min(end - current, f);
        } else if (f < 0.0f) {
            f = Math.min(-minSpeed, f);
            f = Math.max(end - current, f);
        }
        return current + f;
    }

    protected abstract double smooth(double x);

    public static float method1764(float current, float target, float speed) {
        boolean bl;
        if (current == target) {
            return current;
        }
        boolean bl2 = bl = target > current;
        if (speed < 0.0f) {
            speed = 0.0f;
        } else if ((double)speed > 1.0) {
            speed = 1.0f;
        }
        float f = (Math.max(target, current) - Math.min(target, current)) * speed;
        if (f < 0.1f) {
            f = 0.1f;
        }
        if (bl) {
            if ((current += f) >= target) {
                current = target;
            }
        } else if ((current -= f) <= target) {
            current = target;
        }
        return current;
    }

    public static int method1765() {
        return field2164;
    }

    public boolean method1766() {
        return this.timer.passed(this.field2163);
    }

    public TweenType method1767() {
        return this.field2165;
    }

    public Tween(int ms, double endPoint, TweenType state) {
        this.field2163 = ms;
        this.field2167 = endPoint;
        this.field2165 = state;
    }

    public void update() {
        this.timer.update();
    }

    public Tween(int ms, double endPoint) {
        this(ms, endPoint, TweenType.field1464);
    }

    public Tween method1769(TweenType state) {
        if (this.field2165 != state) {
            this.field2165 = state;
            this.timer.setCurrentMs(System.currentTimeMillis() - ((long)this.field2163 - Math.min((long)this.field2163, this.timer.systemTimeDiff())));
        }
        return this;
    }

    public Double method1770() {
        if (this.field2165.method1005()) {
            if (this.method1766()) {
                return this.field2167;
            }
            return this.smooth((double)this.timer.systemTimeDiff() / (double)this.field2163) * this.field2167;
        }
        if (this.method1766()) {
            return 0.0;
        }
        if (this.method1759()) {
            double d = Math.min((long)this.field2163, Math.max(0L, (long)this.field2163 - this.timer.systemTimeDiff()));
            return this.smooth(d / (double)this.field2163) * this.field2167;
        }
        return (1.0 - this.smooth((double)this.timer.systemTimeDiff() / (double)this.field2163)) * this.field2167;
    }
}
