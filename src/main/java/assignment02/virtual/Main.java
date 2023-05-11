package assignment02.virtual;

import assignment02.ReportConfiguration;

public class Main {
    public static void main(String[] args) {
        final var configuration = new ReportConfiguration(3, 10, 20);
        final var sourceAnalyser = new VirtualThreadBasedSourceAnalyser(configuration);
        var liveReport = sourceAnalyser.analyzeSources(java.nio.file.Path.of("."));
        var report = liveReport.getReport();
        report.thenAccept(System.out::println);
    }
}
