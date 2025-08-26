package nikoisntcat.client.utils.math;

public class TimerUtil {
    public long currentMs = System.currentTimeMillis();

    public void setCurrentMs(long time) {
        this.currentMs = time;
    }

    public long currentTimeInSeconds() {
        return System.nanoTime() / 1000000L;
    }

    public boolean method1902(double d) {
        if (d == 0.0) {
            return true;
        }
        return (double)(System.currentTimeMillis() - this.currentMs) >= d;
    }

    public boolean passed(long ms) {
        if (ms <= 0L) {
            return true;
        }
        return System.currentTimeMillis() - this.currentMs >= ms;
    }

    public long systemTimeDiff() {
        return System.currentTimeMillis() - this.currentMs;
    }

    public void update() {
        this.currentMs = System.currentTimeMillis();
    }

    public TimerUtil add(int n) {
        this.currentMs = this.currentTimeInSeconds() + (long)n;
        return this;
    }
}
