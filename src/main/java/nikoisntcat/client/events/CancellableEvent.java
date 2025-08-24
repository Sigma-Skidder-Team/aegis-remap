package nikoisntcat.client.events;

public class CancellableEvent extends Event {
    private boolean cancelled = false;

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }
}
