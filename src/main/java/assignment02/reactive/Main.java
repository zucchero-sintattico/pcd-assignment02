package assignment02.reactive;

import assignment02.SourceAnalyzer;
import assignment02.lib.report.Report;
import assignment02.lib.report.ReportConfiguration;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        final SourceAnalyzer sourceAnalyser = new ReactiveSourceAnalyzer(configuration);
        final CompletableFuture<Report> report = sourceAnalyser.getReport(Path.of("."));
        report.thenAccept(System.out::println);
        while (!report.isDone()) {
            Thread.sleep(100);
        }
    }
}
