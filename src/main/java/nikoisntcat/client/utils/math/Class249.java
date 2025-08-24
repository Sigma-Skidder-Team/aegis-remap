package nikoisntcat.client.utils.math;

public final class Class249 {
    public static float method1679(float x) {
        return x < 0.0f ? 0.0f : (x > 1.0f ? 1.0f : x);
    }

    public static float method1680(float x) {
        return (x = Class249.method1679(x)) == 1.0f ? 1.0f : 1.0f - (float)Math.pow(2.0, -10.0f * x);
    }

    private Class249() {
    }

    public static float method1681(float x) {
        x = Class249.method1679(x);
        float f = 1.0f - x;
        return 1.0f - f * f * f;
    }

    public static float method1682(float x) {
        x = Class249.method1679(x);
        float f = 1.0f - x;
        return 1.0f - f * f * f * f * f;
    }

    public static float method1683(float x) {
        return (x = Class249.method1679(x)) < 0.5f ? 2.0f * x * x : 1.0f - (float)Math.pow(-2.0f * x + 2.0f, 2.0) / 2.0f;
    }
}
