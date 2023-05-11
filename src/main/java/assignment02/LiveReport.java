package assignment02;

import java.util.concurrent.CompletableFuture;

public interface LiveReport {

    CompletableFuture<Report> getReport();

    void setReportConfiguration(final ReportConfiguration configuration);

    void addStatistic(final Statistic statistic);

    void complete();

}

