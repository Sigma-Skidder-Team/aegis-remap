package nikoisntcat.client.utils.math;

public class TimerUtil {
    public long currentMs = System.currentTimeMillis();

    public void setCurrentMs(long l) {
        this.currentMs = l;
    }

    public long method1901() {
        return System.nanoTime() / 1000000L;
    }

    public boolean method1902(double d) {
        if (d == 0.0) {
            return true;
        }
        return (double)(System.currentTimeMillis() - this.currentMs) >= d;
    }

    public boolean method1903(long l) {
        if (l <= 0L) {
            return true;
        }
        return System.currentTimeMillis() - this.currentMs >= l;
    }

    public long method1904() {
        return System.currentTimeMillis() - this.currentMs;
    }

    public void update() {
        this.currentMs = System.currentTimeMillis();
    }

    public TimerUtil method1906(int n) {
        this.currentMs = this.method1901() + (long)n;
        return this;
    }
}
