package assignment02.executor;

import assignment02.Report;
import assignment02.ReportConfiguration;
import assignment02.SourceAnalyser;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) {
        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        final SourceAnalyser sourceAnalyser = new TaskBasedSourceAnalyzer(configuration);
        CompletableFuture<Report> report = sourceAnalyser.getReport(Path.of("."));
        report.thenAccept(System.out::println);
    }
}
