package lib.architecture;

public interface Producer<T> {
    void produce(final T value);
}
