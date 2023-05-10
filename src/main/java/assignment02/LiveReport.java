package assignment02;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public interface LiveReport {

    CompletableFuture<Report> getReport(final Path directory);

    void setReportConfiguration(final ReportConfiguration configuration);

    void addStatistic(final Statistic statistic);

    void complete();

}

