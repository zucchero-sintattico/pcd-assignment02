package assignment.algorithm;

public class AlgorithmConfiguration {

    public final int numberOfPathProducer;
    public final int numberOfPathConsumer;
    public final int numberOfStatisticsConsumer;

    public AlgorithmConfiguration(final int numberOfPathProducer, final int numberOfPathConsumer, final int numberOfStatisticsConsumer) {
        this.numberOfPathProducer = numberOfPathProducer;
        this.numberOfPathConsumer = numberOfPathConsumer;
        this.numberOfStatisticsConsumer = numberOfStatisticsConsumer;
    }

    public static AlgorithmConfigurationBuilder builder() {
        return new AlgorithmConfigurationBuilder();
    }

}
