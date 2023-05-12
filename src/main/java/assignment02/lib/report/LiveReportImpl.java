package assignment02.lib.report;

import assignment02.lib.Monitor;

import java.util.concurrent.CompletableFuture;

public class LiveReportImpl extends Monitor implements LiveReport {

    private final Report report = new ReportImpl();

    private final CompletableFuture<Report> futureReport = new CompletableFuture<>();

    @Override
    public CompletableFuture<Report> getReport() {
        return futureReport;
    }

    @Override
    public void setReportConfiguration(ReportConfiguration configuration) {
        this.report.setReportConfiguration(configuration);
    }

    @Override
    public void addStatistic(Statistic statistic) {
        this.monitored(() -> report.addStatistic(statistic));
    }

    @Override
    public void complete() {
        futureReport.complete(report);
    }
}
