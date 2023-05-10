package assignment02;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface SourceAnalyser {

    default CompletableFuture<Report> getReport(final Path directory) {
        return this.analyzeSources(directory).getReport(directory);
    }

    LiveReport analyzeSources(final Path directory);

}
