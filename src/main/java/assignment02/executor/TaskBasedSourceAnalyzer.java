package assignment02.executor;

import assignment02.SourceAnalyzer;
import assignment02.lib.report.ObservableAsyncReport;
import assignment02.lib.report.ReportConfiguration;
import assignment02.lib.report.Statistic;
import assignment02.lib.report.live.ExecutorBasedLiveReport;
import assignment02.lib.report.live.LiveReport;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskBasedSourceAnalyzer implements SourceAnalyzer {

    private final ReportConfiguration configuration;
    private final LiveReport liveReport = new ExecutorBasedLiveReport();
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
    public ObservableAsyncReport analyzeSources(final Path directory) {
        this.liveReport.setReportConfiguration(this.configuration);
        this.pathProducerExecutor.submit(() -> this.scanTask(directory));
        return this.liveReport;
    }

    @Override
    public void stop() {
        this.pathProducerExecutor.shutdownNow();
        this.pathConsumerExecutor.shutdownNow();
    }
}