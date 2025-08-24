package nikoisntcat.client.screens;

import nikoisntcat.client.utils.math.Class125;
import nikoisntcat.client.utils.math.Class272;

public class Class274
extends Class272 {
    @Override
    protected double method1754(double x) {
        return 1.0 - (x - 1.0) * (x - 1.0);
    }

    public Class274(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public Class274(int ms, double endPoint, Class125 state) {
        super(ms, endPoint, state);
    }
}
