package lib.architecture;

import lib.synchronization.QueueMonitor;
import lib.synchronization.StopMonitor;

/**
 * Thread that consumes values from a queue monitor.
 * @param <T> Type of values to consume.
 * @see QueueMonitor
 */
public abstract class QueueConsumerThread<T> extends Thread implements QueueConsumer<T> {
    private final QueueMonitor<T> queueMonitor;
    private final StopMonitor stopMonitor;

    public QueueConsumerThread(final QueueMonitor<T> queueMonitor) {
        this(queueMonitor, new StopMonitor());
    }

    public QueueConsumerThread(final QueueMonitor<T> queueMonitor, final StopMonitor stopMonitor) {
        this.queueMonitor = queueMonitor;
        this.stopMonitor = stopMonitor;
    }

    @Override
    public final void run() {
        while (this.queueMonitor.isOpen()) {
            if (this.stopMonitor.hasToBeStopped()) {
                break;
            }
            this.queueMonitor.dequeue().ifPresent(this::consume);
        }
        this.onQueueClosed();
    }
}
