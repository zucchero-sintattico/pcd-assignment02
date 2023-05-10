package assignment02;

import java.util.*;

public class ReportImpl implements Report {

    private final List<Statistic> allStats = new ArrayList<>();
    private final List<Statistic> topStats = new ArrayList<>();
    private final Map<Range, Integer> distribution = new HashMap<>();

    private ReportConfiguration configuration;

    @Override
    public void setReportConfiguration(ReportConfiguration configuration) {
        this.configuration = configuration;

        // Build ranges
        final int rangeSize = this.configuration.maxl / this.configuration.nl;
        for (int i = 0; i < this.configuration.nl; i++) {
            this.distribution.put(new Range(i * rangeSize, (i + 1) * rangeSize), 0);
        }
        this.distribution.put(new Range(this.configuration.nl * rangeSize, Integer.MAX_VALUE), 0);
    }

    private void insertTopSorted(Statistic statistic) {
        this.topStats.add(statistic);
        this.topStats.sort(Comparator.comparingInt(x -> -x.linesCount));
    }

    @Override
    public void addStatistic(Statistic statistic) {
        this.allStats.add(statistic);

        // update top
        if (this.topStats.size() < this.configuration.n) {
            this.insertTopSorted(statistic);
        } else {
            if (this.topStats.get(this.configuration.n - 1).linesCount < statistic.linesCount) {
                this.topStats.remove(this.configuration.n - 1);
                this.insertTopSorted(statistic);
            }
        }

        // update distribution
        int linesCount = statistic.linesCount;
        for (Range range : this.distribution.keySet()) {
            if (range.getStart() <= linesCount && linesCount < range.getEnd()) {
                this.distribution.put(range, this.distribution.get(range) + 1);
                break;
            }
        }
    }

    @Override
    public Integer getNumberOfFiles() {
        return this.allStats.size();
    }

    @Override
    public List<Statistic> getTop() {
        return Collections.unmodifiableList(this.topStats);
    }

    @Override
    public Map<Range, Integer> getDistribution() {
        return Collections.unmodifiableMap(this.distribution);
    }

    @Override
    public String toString() {
        return "ReportImpl{" + "\n" +
                "allStats=" + allStats + "\n" +
                "topStats=" + topStats + "\n" +
                "distribution=" + distribution + "\n";
    }
}
