package assignment02.lib.report;

public interface ReportAsyncBuilder {

    void setReportConfiguration(final ReportConfiguration configuration);

    void addStatistic(final Statistic statistic);

    void complete();

}

