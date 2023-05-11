package assignment02;

import assignment02.lib.LiveReport;
import assignment02.lib.Report;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface SourceAnalyser {

    default CompletableFuture<Report> getReport(final Path directory) {
        return this.analyzeSources(directory).getReport();
    }

    LiveReport analyzeSources(final Path directory);

}
