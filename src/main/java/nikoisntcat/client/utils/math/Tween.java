package nikoisntcat.client.utils.math;

public abstract class Tween {
    protected int duration;
    private static int field2164;
    protected TweenState tweenState;
    public TimerUtil timer = new TimerUtil();
    public double endPoint;

    public void method1755(double endPoint) {
        this.endPoint = endPoint;
    }

    public void setTweenState() {
        this.setType(this.tweenState.other());
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getEndPoint() {
        return this.endPoint;
    }

    protected boolean method1759() {
        return false;
    }

    public boolean method1760(TweenState state) {
        return this.shouldEnd() && this.tweenState.equals(state);
    }

    public double lerp() {
        return 1.0 - (double)this.timer.systemTimeDiff() / (double)this.duration * this.endPoint;
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

    public boolean shouldEnd() {
        return this.timer.passed(this.duration);
    }

    public TweenState getTweenType() {
        return this.tweenState;
    }

    public Tween(int ms, double endPoint, TweenState state) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.tweenState = state;
    }

    public void update() {
        this.timer.update();
    }

    public Tween(int ms, double endPoint) {
        this(ms, endPoint, TweenState.FIRST);
    }

    public Tween setType(TweenState state) {
        if (this.tweenState != state) {
            this.tweenState = state;
            this.timer.setCurrentMs(System.currentTimeMillis() - ((long)this.duration - Math.min((long)this.duration, this.timer.systemTimeDiff())));
        }
        return this;
    }

    public Double get() {
        if (this.tweenState.isFirst()) {
            if (this.shouldEnd()) {
                return this.endPoint;
            }
            return this.smooth((double)this.timer.systemTimeDiff() / (double)this.duration) * this.endPoint;
        }
        if (this.shouldEnd()) {
            return 0.0;
        }
        if (this.method1759()) {
            double d = Math.min((long)this.duration, Math.max(0L, (long)this.duration - this.timer.systemTimeDiff()));
            return this.smooth(d / (double)this.duration) * this.endPoint;
        }
        return (1.0 - this.smooth((double)this.timer.systemTimeDiff() / (double)this.duration)) * this.endPoint;
    }
}
