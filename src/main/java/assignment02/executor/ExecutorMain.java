package assignment02.executor;

import assignment02.SourceAnalyzer;
import assignment02.lib.report.ReportConfiguration;

import java.nio.file.Path;

public class ExecutorMain {
    public static void main(String[] args) {
        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        final SourceAnalyzer sourceAnalyser = new TaskBasedSourceAnalyzer(configuration);
        sourceAnalyser.getReport(Path.of(".")).thenAccept(System.out::println);
    }
}
