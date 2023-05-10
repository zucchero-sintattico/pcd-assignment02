package assignment02.lib.architecture;

import assignment02.lib.synchronization.QueueMonitor;

/**
 * Thread that produces values to a queue monitor.
 *
 * @param <T> Type of values to produce.
 * @see QueueMonitor
 */
public abstract class QueueProducerThread<T> extends Thread implements Producer<T> {
    private final QueueMonitor<T> queue;

    public QueueProducerThread(final QueueMonitor<T> queue) {
        this.queue = queue;
    }

    @Override
    public void produce(T value) {
        this.queue.enqueue(value);
    }

    final protected void closeQueue() {
        this.queue.close();
    }

    @Override
    abstract public void run();
}
