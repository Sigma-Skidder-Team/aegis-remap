package nikoisntcat.client.utils.math;

public class TimerUtil {
    public long currentMs = System.currentTimeMillis();

    public void setCurrentMs(long time) {
        this.currentMs = time;
    }

    public long currentTimeInSeconds() {
        return System.nanoTime() / 1000000L;
    }

    public boolean passed(double ms) {
        if (ms == 0.0) {
            return true;
        }
        return (double)(System.currentTimeMillis() - this.currentMs) >= ms;
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

    public TimerUtil add(int ms) {
        this.currentMs = this.currentTimeInSeconds() + (long)ms;
        return this;
    }
}
