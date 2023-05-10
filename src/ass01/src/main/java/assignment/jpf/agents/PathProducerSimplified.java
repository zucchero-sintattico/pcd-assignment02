package assignment.jpf.agents;

import lib.architecture.QueueProducerThread;
import lib.synchronization.QueueMonitor;
import lib.synchronization.StopMonitor;


public class PathProducerSimplified extends QueueProducerThread<Integer> {

    private final StopMonitor stopMonitor;

    public PathProducerSimplified(final QueueMonitor<Integer> queue, final StopMonitor stopMonitor) {
        super(queue);
        this.stopMonitor = stopMonitor;
    }

    @Override
    public void run() {
        if (!this.stopMonitor.hasToBeStopped()) {
            this.produce(1);
        }
        this.closeQueue();
    }
}