package nikoisntcat.client.modules.impl.misc;

import nikoisntcat.client.events.impl.MotionEvent;
import nikoisntcat.client.modules.Category;
import nikoisntcat.client.modules.Module;
public class GetLagModule extends Module {
    public GetLagModule() {
        super("GetLag", 0, Category.MISC);
    }

    @Override
    public void onMotion(MotionEvent motionEvent) {
        motionEvent.setEntityX(motionEvent.method1401() + 1000.0);
    }
}
