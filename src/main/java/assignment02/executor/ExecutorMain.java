package assignment02.executor;

import assignment02.SourceAnalyzer;
import assignment02.lib.report.Report;
import assignment02.lib.report.ReportConfiguration;

import java.nio.file.Path;
import java.util.concurrent.Future;

public class ExecutorMain {
    public static void main(String[] args) {
        final ReportConfiguration configuration = new ReportConfiguration(3, 10, 20);
        final SourceAnalyzer sourceAnalyser = new TaskBasedSourceAnalyzer(configuration);
        final Future<Report> report = sourceAnalyser.getReport(Path.of("src"));
        try {
            System.out.println(report.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
