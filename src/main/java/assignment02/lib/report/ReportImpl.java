package assignment02.lib.report;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReportImpl implements Report {

    private final List<Statistic> statistics;
    private final List<Statistic> topStatistics;
    private final Map<Range, Integer> distribution;
    private ReportConfiguration configuration;

    public ReportImpl(final List<Statistic> statistics, final List<Statistic> topStatistic, final Map<Range, Integer> distribution) {
        this.statistics = statistics;
        this.topStatistics = topStatistic;
        this.distribution = distribution;
    }
    
    @Override
    public Integer getNumberOfFiles() {
        return this.statistics.size();
    }

    @Override
    public List<Statistic> getTop() {
        return Collections.unmodifiableList(this.topStatistics);
    }

    @Override
    public Map<Range, Integer> getDistribution() {
        return Collections.unmodifiableMap(this.distribution);
    }

    @Override
    public String toString() {
        return "ReportImpl{" + "\n" +
                "allStats=" + statistics + "\n" +
                "topStats=" + topStatistics + "\n" +
                "distribution=" + distribution + "\n";
    }
}
