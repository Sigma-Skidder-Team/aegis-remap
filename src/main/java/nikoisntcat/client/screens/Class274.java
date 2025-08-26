package nikoisntcat.client.screens;

import nikoisntcat.client.utils.math.TweenType;
import nikoisntcat.client.utils.math.Tween;

public class Class274
extends Tween {
    @Override
    protected double smooth(double x) {
        return 1.0 - (x - 1.0) * (x - 1.0);
    }

    public Class274(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public Class274(int ms, double endPoint, TweenType state) {
        super(ms, endPoint, state);
    }
}
