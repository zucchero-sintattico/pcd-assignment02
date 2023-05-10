package assignment.jpf.agents;

import lib.architecture.QueueConsumerThread;
import lib.synchronization.QueueMonitor;
import lib.synchronization.StopMonitor;

public class StatisticConsumerSimplified extends QueueConsumerThread<String> {
    //final Logger logger = LoggerMonitor.getInstance();

    public StatisticConsumerSimplified(final QueueMonitor<String> queue, final StopMonitor stopMonitor) {
        super(queue, stopMonitor);
    }

    @Override
    public void consume(final String value) {
        //logger.log("Consuming " + value);
    }

    @Override
    public void onQueueClosed() {
        //logger.log("Statistic Consumer finished");
    }

}