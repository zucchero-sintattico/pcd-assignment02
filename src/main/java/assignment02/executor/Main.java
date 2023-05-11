package assignment02.executor;

import assignment02.LiveReport;
import assignment02.Report;
import assignment02.ReportConfiguration;
import assignment02.SourceAnalyser;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        final SourceAnalyser sourceAnalyser = new TaskBasedSourceAnalyzer(configuration);
        LiveReport liveReport = sourceAnalyser.analyzeSources(Path.of("."));
        CompletableFuture<Report> report = liveReport.getReport();
        report.thenAccept(System.out::println);
    }
}
