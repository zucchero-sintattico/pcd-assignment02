package assignment.jpf;

import assignment.algorithm.AlgorithmConfiguration;
import assignment.jpf.agents.PathConsumerSimplified;
import assignment.jpf.agents.PathProducerSimplified;
import assignment.jpf.agents.StatisticConsumerSimplified;
import gov.nasa.jpf.vm.Verify;
import lib.synchronization.ActionBarrier;
import lib.synchronization.Barrier;
import lib.synchronization.QueueMonitor;
import lib.synchronization.StopMonitor;

import java.util.HashSet;
import java.util.Set;

public class MainSimplified {
    public static void main(String[] args) throws InterruptedException {

        Verify.beginAtomic();
        final AlgorithmConfiguration configuration = AlgorithmConfiguration.builder()
                .withNumberOfPathProducer(1)
                .withNumberOfPathConsumer(1)
                .withNumberOfStatisticsConsumer(1)
                .build();
        final QueueMonitor<Integer> firstQueue = new QueueMonitor<>();
        final QueueMonitor<String> secondQueue = new QueueMonitor<>();
        final Barrier barrier = new ActionBarrier(configuration.numberOfPathConsumer, secondQueue::close);
        final StopMonitor stopMonitor = new StopMonitor();

        final Set<PathProducerSimplified> producers = new HashSet<>();
        for (int i = 0; i < configuration.numberOfPathProducer; i++) {
            producers.add(new PathProducerSimplified(firstQueue, stopMonitor));
        }

        final Set<PathConsumerSimplified> consumers = new HashSet<>();
        for (int i = 0; i < configuration.numberOfPathConsumer; i++) {
            consumers.add(new PathConsumerSimplified(firstQueue, secondQueue, barrier, stopMonitor));
        }

        final Set<StatisticConsumerSimplified> statisticConsumers = new HashSet<>();
        for (int i = 0; i < configuration.numberOfStatisticsConsumer; i++) {
            statisticConsumers.add(new StatisticConsumerSimplified(secondQueue, stopMonitor));
        }

        statisticConsumers.forEach(Thread::start);
        consumers.forEach(Thread::start);
        producers.forEach(Thread::start);
        Verify.endAtomic();

        for (PathProducerSimplified producer : producers) {
            producer.join();
        }

        for (PathConsumerSimplified consumer : consumers) {
            consumer.join();
        }

        for (StatisticConsumerSimplified statisticConsumer : statisticConsumers) {
            statisticConsumer.join();
        }
    }
}
