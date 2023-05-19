package assignment02.lib.report.live;

import assignment02.lib.Monitor;
import assignment02.lib.report.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class LiveReport extends Monitor implements ReportAsyncBuilder, ObservableAsyncReport {

    private final CompletableFuture<Report> futureReport = new CompletableFuture<>();
    private final List<Statistic> statistics = new ArrayList<>();
    private final List<Statistic> topStatistics = new ArrayList<>();
    private final Map<Range, Integer> distribution = new HashMap<>();

    /**
     * Observability
     */
    private final List<NumberOfFilesChangeListener> numberOfFilesChangeListeners = new ArrayList<>();
    private final List<TopNChangeListener> topNChangeListeners = new ArrayList<>();
    private final List<DistributionChangeListener> distributionChangeListeners = new ArrayList<>();
    private ReportConfiguration configuration;

    @Override
    public CompletableFuture<Report> getReport() {
        return futureReport;
    }

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
        this.topStatistics.add(statistic);
        this.topStatistics.sort(Comparator.comparingInt(x -> -x.linesCount));
    }

    @Override
    public void addStatistic(Statistic statistic) {
        this.monitored(() -> {
            this.statistics.add(statistic);

            // update top
            if (this.topStatistics.size() < this.configuration.n) {
                this.insertTopSorted(statistic);
                this.notifyTopNChange(this.topStatistics);
            } else {
                if (this.topStatistics.get(this.configuration.n - 1).linesCount < statistic.linesCount) {
                    this.topStatistics.remove(this.configuration.n - 1);
                    this.insertTopSorted(statistic);
                    this.notifyTopNChange(this.topStatistics);
                }
            }

            // update distribution
            int linesCount = statistic.linesCount;
            for (Range range : this.distribution.keySet()) {
                if (range.getStart() <= linesCount && linesCount < range.getEnd()) {
                    this.distribution.put(range, this.distribution.get(range) + 1);
                    this.notifyDistributionChange(this.distribution);
                    break;
                }
            }

            this.notifyNumberOfFilesChanged(this.statistics.size());
        });
    }


    @Override
    public void complete() {
        futureReport.complete(new ReportImpl(this.statistics, this.topStatistics, this.distribution));
    }

    /**
     * Observablity
     **/

    @Override
    public void registerOnNumberOfFilesChanges(NumberOfFilesChangeListener listener) {
        this.monitored(() -> this.numberOfFilesChangeListeners.add(listener));
    }

    protected void notifyNumberOfFilesChanged(final int newNumber) {
        this.numberOfFilesChangeListeners.forEach(x -> x.accept(newNumber));
    }

    @Override
    public void registerOnTopNChange(TopNChangeListener listener) {
        this.monitored(() -> this.topNChangeListeners.add(listener));
    }

    protected void notifyTopNChange(final List<Statistic> newTop) {
        this.topNChangeListeners.forEach(x -> x.accept(newTop));
    }

    @Override
    public void registerOnDistributionChange(DistributionChangeListener listener) {
        this.monitored(() -> this.distributionChangeListeners.add(listener));
    }

    @Override
    public void stop() {
        this.complete();
    }

    protected void notifyDistributionChange(final Map<Range, Integer> newDistribution) {
        this.distributionChangeListeners.forEach(x -> x.accept(newDistribution));
    }
}
