package nikoisntcat.client.utils.math;


public abstract class Class272 {
    protected int field2163;
    private static int field2164;
    protected Class125 field2165;
    public TimerUtil field2166 = new TimerUtil();
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

    public boolean method1760(Class125 state) {
        return this.method1766() && this.field2165.equals((Object)state);
    }

    public double method1761() {
        return 1.0 - (double)this.field2166.method1904() / (double)this.field2163 * this.field2167;
    }

    public static void method1762(int delta) {
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

    protected abstract double method1754(double var1);

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
        return this.field2166.method1903(this.field2163);
    }

    public Class125 method1767() {
        return this.field2165;
    }

    public Class272(int ms, double endPoint, Class125 state) {
        this.field2163 = ms;
        this.field2167 = endPoint;
        this.field2165 = state;
    }

    public void method1768() {
        this.field2166.method1905();
    }

    public Class272(int ms, double endPoint) {
        this(ms, endPoint, Class125.field1464);
    }

    public Class272 method1769(Class125 state) {
        if (this.field2165 != state) {
            this.field2165 = state;
            this.field2166.method1900(System.currentTimeMillis() - ((long)this.field2163 - Math.min((long)this.field2163, this.field2166.method1904())));
        }
        return this;
    }

    public Double method1770() {
        if (this.field2165.method1005()) {
            if (this.method1766()) {
                return this.field2167;
            }
            return this.method1754((double)this.field2166.method1904() / (double)this.field2163) * this.field2167;
        }
        if (this.method1766()) {
            return 0.0;
        }
        if (this.method1759()) {
            double d = Math.min((long)this.field2163, Math.max(0L, (long)this.field2163 - this.field2166.method1904()));
            return this.method1754(d / (double)this.field2163) * this.field2167;
        }
        return (1.0 - this.method1754((double)this.field2166.method1904() / (double)this.field2163)) * this.field2167;
    }
}
