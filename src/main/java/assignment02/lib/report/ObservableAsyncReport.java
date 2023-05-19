package assignment02.lib.report;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Consumer;


public interface ObservableAsyncReport {

    Future<Report> getReport();

    void registerOnNumberOfFilesChanges(final NumberOfFilesChangeListener listener);

    void registerOnTopNChange(final TopNChangeListener listener);

    void registerOnDistributionChange(final DistributionChangeListener listener);


    interface NumberOfFilesChangeListener extends Consumer<Integer> {
    }

    interface TopNChangeListener extends Consumer<List<Statistic>> {
    }

    interface DistributionChangeListener extends Consumer<Map<Range, Integer>> {
    }

}
