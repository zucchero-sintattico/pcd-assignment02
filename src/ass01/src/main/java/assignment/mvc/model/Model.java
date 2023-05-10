package assignment.mvc.model;

import assignment.Statistic;
import assignment.mvc.model.listeners.DistributionChangeListener;
import assignment.mvc.model.listeners.NumberOfFilesChangeListener;
import assignment.mvc.model.listeners.TopChangeListener;

import java.util.List;
import java.util.Map;

public interface Model {
    Integer getNumberOfFiles();
    List<Statistic> getTop();
    Map<Range, Integer> getDistribution();

    void setConfiguration(ModelConfiguration configuration);

    void addStatistic(Statistic statistic);

    void registerOnNumberOfFilesChange(NumberOfFilesChangeListener listener);
    void registerOnTopNChange(TopChangeListener listener);
    void registerOnDistributionChange(DistributionChangeListener listener);

    void reset();
}
