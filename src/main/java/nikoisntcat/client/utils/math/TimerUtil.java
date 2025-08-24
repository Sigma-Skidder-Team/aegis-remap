package nikoisntcat.client.utils.math;

public class TimerUtil {
    public long field2323 = System.currentTimeMillis();

    public void method1900(long l) {
        this.field2323 = l;
    }

    public long method1901() {
        return System.nanoTime() / 1000000L;
    }

    public boolean method1902(double d) {
        if (d == 0.0) {
            return true;
        }
        return (double)(System.currentTimeMillis() - this.field2323) >= d;
    }

    public boolean method1903(long l) {
        if (l <= 0L) {
            return true;
        }
        return System.currentTimeMillis() - this.field2323 >= l;
    }

    public long method1904() {
        return System.currentTimeMillis() - this.field2323;
    }

    public void method1905() {
        this.field2323 = System.currentTimeMillis();
    }

    public TimerUtil method1906(int n) {
        this.field2323 = this.method1901() + (long)n;
        return this;
    }
}
