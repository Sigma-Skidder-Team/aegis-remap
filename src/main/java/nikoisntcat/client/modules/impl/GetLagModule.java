package nikoisntcat.client.modules.impl;

import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
public class GetLagModule extends Module {
    public GetLagModule() {
        super("GetLag", 0, Category.RENDER);
    }

    @Override
    public void onMotion(MotionEvent motionEvent) {
        motionEvent.method1408(motionEvent.method1401() + 1000.0);
    }
}
