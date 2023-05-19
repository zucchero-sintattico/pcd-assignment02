package assignment02;

import assignment02.lib.report.ObservableAsyncReport;
import assignment02.lib.report.Report;

import java.nio.file.Path;
import java.util.concurrent.Future;

public interface SourceAnalyzer {

    default Future<Report> getReport(final Path directory) {
        return this.analyzeSources(directory).getReport();
    }

    ObservableAsyncReport analyzeSources(final Path directory);

    void stop();

}
