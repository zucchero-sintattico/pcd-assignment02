package assignment02.mvc.model;

import assignment.Statistic;
import assignment.mvc.model.listeners.DistributionChangeListener;
import assignment.mvc.model.listeners.NumberOfFilesChangeListener;
import assignment.mvc.model.listeners.TopChangeListener;

import java.util.*;

public class ModelImpl implements Model {

    private final List<NumberOfFilesChangeListener> numberOfFilesChangeListeners = new ArrayList<>();
    private final List<TopChangeListener> topChangeListeners = new ArrayList<>();
    private final List<DistributionChangeListener> distributionChangeListeners = new ArrayList<>();

    private final List<Statistic> allStats = new ArrayList<>();
    private final List<Statistic> topStats = new ArrayList<>();
    private final Map<Range, Integer> distribution = new HashMap<>();

    private ModelConfiguration configuration;

    @Override
    public void setConfiguration(ModelConfiguration configuration) {
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
        this.notifyNumberOfFilesChangeListeners();

        // update top
        if (this.topStats.size() < this.configuration.n) {
            this.insertTopSorted(statistic);
            this.notifyTopChangeListeners();
        } else {
            if (this.topStats.get(this.configuration.n - 1).linesCount < statistic.linesCount) {
                this.topStats.remove(this.configuration.n - 1);
                this.insertTopSorted(statistic);
                this.notifyTopChangeListeners();
            }
        }

        // update distribution
        int linesCount = statistic.linesCount;
        for (Range range : this.distribution.keySet()) {
            if (range.getStart() <= linesCount && linesCount < range.getEnd()) {
                this.distribution.put(range, this.distribution.get(range) + 1);
                this.notifyDistributionChangeListeners();
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
    public void registerOnNumberOfFilesChange(NumberOfFilesChangeListener listener) {
        this.numberOfFilesChangeListeners.add(listener);
    }

    private void notifyNumberOfFilesChangeListeners() {
        for (NumberOfFilesChangeListener listener : this.numberOfFilesChangeListeners) {
            listener.consume(this.getNumberOfFiles());
        }
    }

    @Override
    public void registerOnTopNChange(TopChangeListener listener) {
        this.topChangeListeners.add(listener);
    }

    private void notifyTopChangeListeners() {
        for (TopChangeListener listener : this.topChangeListeners) {
            listener.consume(this.getTop());
        }
    }

    @Override
    public void registerOnDistributionChange(DistributionChangeListener listener) {
        this.distributionChangeListeners.add(listener);
    }

    @Override
    public void reset() {
        this.allStats.clear();
        this.topStats.clear();
        this.distribution.clear();

    }

    private void notifyDistributionChangeListeners() {
        for (DistributionChangeListener listener : this.distributionChangeListeners) {
            listener.consume(this.getDistribution());
        }
    }
}
