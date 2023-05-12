package assignment02.lib.report;

import java.util.List;
import java.util.Map;

public interface Report {
    Integer getNumberOfFiles();

    List<Statistic> getTop();

    Map<Range, Integer> getDistribution();

    void setReportConfiguration(ReportConfiguration configuration);

    void addStatistic(Statistic statistic);

}
