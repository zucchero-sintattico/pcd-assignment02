package assignment.jpf.agents;

import lib.architecture.QueueConsumerThread;
import lib.synchronization.Barrier;
import lib.synchronization.QueueMonitor;
import lib.synchronization.StopMonitor;

/**
 * Consumes paths from the queue and produces statistics for each path.
 * Once a statistic is produced, it is enqueued in the statistics queue.
 * When the queue is closed, it notifies the barrier in order to safely close the statistics queue.
 */
public class PathConsumerSimplified extends QueueConsumerThread<Integer> {
    private final QueueMonitor<String> statsQueue;
    private final Barrier barrier;

    public PathConsumerSimplified(final QueueMonitor<Integer> pathQueue, final QueueMonitor<String> statsQueue, final Barrier barrier, final StopMonitor stopMonitor) {
        super(pathQueue, stopMonitor);
        this.statsQueue = statsQueue;
        this.barrier = barrier;
    }

    @Override
    public void consume(final Integer element) {
        this.statsQueue.enqueue("test");
    }

    @Override
    public void onQueueClosed() {
        try {
            this.barrier.hitAndWaitAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
