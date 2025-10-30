package nikoisntcat.client.screens;

import nikoisntcat.client.utils.math.TweenState;
import nikoisntcat.client.utils.math.Tween;

public class EaseInOutCubicTween
extends Tween {
    @Override
    protected double smooth(double x) {
        return -2.0 * Math.pow(x, 3.0) + 3.0 * Math.pow(x, 2.0);
    }

    public EaseInOutCubicTween(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public EaseInOutCubicTween(int ms, double endPoint, TweenState state) {
        super(ms, endPoint, state);
    }
}
