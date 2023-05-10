package lib.synchronization;

import lib.utils.CloseableQueue;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;

/**
 * A generic closeable queue that uses a monitor to synchronize access to the queue.
 *
 * @param <T> the type of the elements in the queue.
 * @see Monitor
 * @see CloseableQueue
 */
public class QueueMonitor<T> extends Monitor implements CloseableQueue<T> {
    private final Queue<T> queue = new ConcurrentLinkedQueue<>();
    private final Condition notEmpty = newCondition();
    private final Condition notFull = newCondition();
    private final int maxSize = 1000;
    private boolean open = true;

    @Override
    public void enqueue(final T value) {
        monitored(() -> {
            while (queue.size() == maxSize && open) {
                try {
                    notFull.await();
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.add(value);
            notEmpty.signal();
        });
    }

    @Override
    public Optional<T> dequeue() {
        return monitored(() -> {
            while (queue.isEmpty() && open) {
                try {
                    notEmpty.await();
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Optional<T> res = this.isOpen() ? Optional.of(queue.remove()) : Optional.empty();
            notFull.signal();
            return res;
        });
    }

    @Override
    public void close() {
        monitored(() -> {
            open = false;
            notEmpty.signalAll();
        });
    }

    @Override
    public boolean isOpen() {
        return monitored(() -> open || !this.queue.isEmpty());
    }
}
