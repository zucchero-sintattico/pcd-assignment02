package lib.architecture;

public interface QueueConsumer<T> extends Consumer<T> {
    void onQueueClosed();
}
