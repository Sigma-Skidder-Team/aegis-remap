package nikoisntcat.client.screens;

import nikoisntcat.client.utils.math.TweenType;
import nikoisntcat.client.utils.math.Tween;

public class Class276
extends Tween {
    @Override
    protected double smooth(double x) {
        return -2.0 * Math.pow(x, 3.0) + 3.0 * Math.pow(x, 2.0);
    }

    public Class276(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public Class276(int ms, double endPoint, TweenType state) {
        super(ms, endPoint, state);
    }
}
