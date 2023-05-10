package assignment.mvc.controller;

import assignment.algorithm.AlgorithmConfiguration;
import assignment.algorithm.AlgorithmStatus;
import assignment.algorithm.AssignmentAlgorithm;
import assignment.logger.Logger;
import assignment.logger.LoggerMonitor;
import assignment.mvc.model.Model;
import assignment.mvc.model.ModelConfiguration;
import assignment.mvc.view.View;

import java.nio.file.Path;

public class ControllerImpl implements Controller {

    public static final int NUMBER_OF_PATH_PRODUCER = 1;
    public static final int NUMBER_OF_PATH_CONSUMER = 5;
    public static final int NUMBER_OF_STATISTICS_CONSUMER = 1;
    private final Logger logger = LoggerMonitor.getInstance();
    private final Model model;
    private final AlgorithmConfiguration config;
    private AssignmentAlgorithm algorithm;
    private AlgorithmStatus status = AlgorithmStatus.IDLE;
    private View view;

    public ControllerImpl(Model model) {
        this.model = model;
        this.model.registerOnTopNChange((topN) -> this.view.updateTopN(topN));
        this.model.registerOnDistributionChange((distribution) -> this.view.updateDistribution(distribution));
        this.model.registerOnNumberOfFilesChange((numberOfFiles) -> this.view.updateNumberOfFiles(numberOfFiles));

        this.config = AlgorithmConfiguration.builder()
                .withNumberOfPathProducer(NUMBER_OF_PATH_PRODUCER)
                .withNumberOfPathConsumer(NUMBER_OF_PATH_CONSUMER)
                .withNumberOfStatisticsConsumer(NUMBER_OF_STATISTICS_CONSUMER)
                .build();
        this.logger.log("Controller created, AlgorithmConfiguration: " + this.config);
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void startAlgorithm(Path path, int topN, int nOfIntervals, int maxL) {
        this.model.reset();
        final ModelConfiguration modelConfiguration = new ModelConfiguration(topN, nOfIntervals, maxL);
        this.model.setConfiguration(modelConfiguration);

        final Thread algorithmThread = new Thread(() -> {
            this.algorithm = new AssignmentAlgorithm(this.model, path, this.config, true);
            this.algorithm.start();
            this.status = AlgorithmStatus.RUNNING;
            this.logger.log("Algorithm started");
            this.view.updateAlgorithmStatus(this.status);
            try {
                this.algorithm.join();
                if (this.status == AlgorithmStatus.STOPPED) {
                    return;
                }
                this.status = AlgorithmStatus.FINISHED;
                this.logger.log("Algorithm finished");
                this.view.updateAlgorithmStatus(this.status);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        algorithmThread.start();
    }

    @Override
    public void stopAlgorithm() {
        if (this.status != AlgorithmStatus.RUNNING) {
            return;
        }
        this.algorithm.stop();
        this.status = AlgorithmStatus.STOPPED;
        this.logger.log("Algorithm stopped");
        this.view.updateAlgorithmStatus(this.status);
    }
}
