package nikoisntcat.client.utils.math;

public class Class288 {
    private float field2244;
    public float field2245;
    private float field2246;
    private final TimerUtil field2247;
    private float field2248;

    public void method1844(float f) {
        this.field2247.method1905();
        this.field2248 = f;
        this.field2246 = this.field2245 - this.field2248;
    }

    public float method1845() {
        long l = this.field2247.method1904();
        return this.field2248 + this.field2246 * Class288.method1846(Math.min((float)l / 1000.0f * this.field2244, 1.0f));
    }

    private static float method1846(float f) {
        f = 1.0f - f;
        float f2 = f * f;
        float f3 = f2 * f;
        float f4 = 0.0f * f3 + 0.0f * f2 - 0.0f * f + 0.0f;
        float f5 = 0.03f * f3 - 0.06f * f2 + 0.03f * f;
        float f6 = 0.0f * f3 + 0.0f * f2;
        float f7 = 1.0f * f3;
        return 1.0f - (f4 + f5 + f6 + f7);
    }

    public Class288(float f, float f2) {
        this.field2244 = f;
        this.field2247 = new TimerUtil();
        this.method1847(f2);
        this.method1844(f2);
    }

    public void method1847(float f) {
        if (this.field2245 == f) {
            return;
        }
        this.field2248 = this.method1845();
        this.field2247.method1905();
        this.field2246 = f - this.field2248;
        this.field2245 = f;
    }
}