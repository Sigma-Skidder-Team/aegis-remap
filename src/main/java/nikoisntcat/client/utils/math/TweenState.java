package nikoisntcat.client.utils.math;

public enum TweenState {
    FIRST,
    SECOND;

    public TweenState other() {
        if (this == FIRST) {
            return SECOND;
        }
        return FIRST;
    }

    public boolean isFirst() {
        return this == FIRST;
    }
}
