package nikoisntcat.client.screens;

import nikoisntcat.client.utils.math.TweenState;
import nikoisntcat.client.utils.math.Tween;

public class EaseOutQuadTween
extends Tween {
    @Override
    protected double smooth(double x) {
        return 1.0 - (x - 1.0) * (x - 1.0);
    }

    public EaseOutQuadTween(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public EaseOutQuadTween(int ms, double endPoint, TweenState state) {
        super(ms, endPoint, state);
    }
}
