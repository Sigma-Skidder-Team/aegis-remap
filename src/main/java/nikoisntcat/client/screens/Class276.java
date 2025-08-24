package nikoisntcat.client.screens;

import nikoisntcat.client.utils.math.Class125;
import nikoisntcat.client.utils.math.Class272;

public class Class276
extends Class272 {
    @Override
    protected double method1754(double x) {
        return -2.0 * Math.pow(x, 3.0) + 3.0 * Math.pow(x, 2.0);
    }

    public Class276(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public Class276(int ms, double endPoint, Class125 state) {
        super(ms, endPoint, state);
    }
}
