package assignment02.executor;

import assignment02.SourceAnalyser;
import assignment02.Statistic;
import assignment02.lib.LiveReport;
import assignment02.lib.LiveReportImpl;
import assignment02.lib.ReportConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskBasedSourceAnalyzer implements SourceAnalyser {

    private final ReportConfiguration configuration;
    private final LiveReport liveReport = new LiveReportImpl();
    private final ExecutorService pathProducerExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService pathConsumerExecutor;

    public TaskBasedSourceAnalyzer(final ReportConfiguration configuration, final ExecutorService pathConsumerExecutor) {
        this.configuration = configuration;
        this.pathConsumerExecutor = pathConsumerExecutor;
    }

    public TaskBasedSourceAnalyzer(final ReportConfiguration configuration) {
        this(configuration, Executors.newCachedThreadPool());
    }

    private void analyzeFileTask(final Path path) {
        try {
            this.liveReport.addStatistic(new Statistic(path, Files.readAllLines(path).size()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void scanTask(final Path directory) {
        try (final var paths = Files.walk(directory)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        this.pathConsumerExecutor.submit(() -> this.analyzeFileTask(path));
                    });
            this.pathProducerExecutor.shutdown();
            this.pathConsumerExecutor.shutdown();
            this.pathConsumerExecutor.awaitTermination(1, TimeUnit.MINUTES);
            this.liveReport.complete();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LiveReport analyzeSources(final Path directory) {
        this.liveReport.setReportConfiguration(this.configuration);
        this.pathProducerExecutor.submit(() -> this.scanTask(directory));
        return this.liveReport;
    }
}