package lib.queue;

public interface Enqueuable<T> {
    void enqueue(T value);
}
