package assignment02.event;

import assignment02.SourceAnalyzer;
import assignment02.lib.report.ReportConfiguration;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        final SourceAnalyzer eventBasedSourceAnalyser = new EventBasedSourceAnalyser(configuration);
        eventBasedSourceAnalyser.getReport(Path.of("src")).thenAccept(System.out::println);
    }
}
