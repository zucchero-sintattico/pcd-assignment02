package assignment02.event;

import assignment02.lib.LiveReport;
import assignment02.lib.Report;
import io.vertx.core.Future;

import java.nio.file.Path;

public interface SourceAnalyzer {

    Future<Report> getReport(final Path directory);

    LiveReport analyzeSources(final Path directory);
}
