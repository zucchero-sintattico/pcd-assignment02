package assignment.agents;

import assignment.Statistic;
import assignment.logger.Logger;
import assignment.logger.LoggerMonitor;
import assignment.queue.PathQueue;
import assignment.queue.StatisticQueue;
import assignment02.lib.architecture.QueueConsumerThread;
import assignment02.lib.synchronization.Barrier;
import assignment02.lib.synchronization.StopMonitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Consumes paths from the queue and produces statistics for each path.
 * Once a statistic is produced, it is enqueued in the statistics queue.
 * When the queue is closed, it notifies the barrier in order to safely close the statistics queue.
 */
public class PathConsumer extends QueueConsumerThread<Path> {
    private final Logger logger = LoggerMonitor.getInstance();
    private final StatisticQueue statsQueue;
    private final Barrier barrier;

    public PathConsumer(final PathQueue pathQueue, final StatisticQueue statsQueue, final Barrier barrier, final StopMonitor stopMonitor) {
        super(pathQueue, stopMonitor);
        this.statsQueue = statsQueue;
        this.barrier = barrier;
    }

    @Override
    public void consume(final Path filepath) {
        try {
            //Thread.sleep(10);
            final int lines = Files.readAllLines(filepath).size();
            this.logger.log("Consumed " + filepath + " has " + lines + " lines");
            this.statsQueue.enqueue(new Statistic(filepath, lines));
        } catch (IOException e) {
            System.out.println(e);
        }
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
