package assignment.algorithm;

public class AlgorithmConfigurationBuilder {
    int numberOfPathProducer = 1;
    int numberOfPathConsumer = 1;
    int numberOfStatisticsConsumer = 1;

    public AlgorithmConfigurationBuilder withNumberOfPathProducer(final int numberOfPathProducer) {
        this.numberOfPathProducer = numberOfPathProducer;
        return this;
    }

    public AlgorithmConfigurationBuilder withNumberOfPathConsumer(final int numberOfPathConsumer) {
        this.numberOfPathConsumer = numberOfPathConsumer;
        return this;
    }

    public AlgorithmConfigurationBuilder withNumberOfStatisticsConsumer(final int numberOfStatisticsConsumer) {
        this.numberOfStatisticsConsumer = numberOfStatisticsConsumer;
        return this;
    }

    public AlgorithmConfiguration build() {
        return new AlgorithmConfiguration(numberOfPathProducer, numberOfPathConsumer, numberOfStatisticsConsumer);
    }

}
