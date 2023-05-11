package assignment02.lib;

import assignment02.Statistic;
import assignment02.lib.Report;
import assignment02.lib.ReportConfiguration;

import java.util.concurrent.CompletableFuture;

public interface LiveReport {

    CompletableFuture<Report> getReport();

    void setReportConfiguration(final ReportConfiguration configuration);

    void addStatistic(final Statistic statistic);

    void complete();

}

