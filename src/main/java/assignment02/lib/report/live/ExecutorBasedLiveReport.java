package assignment02.lib.report.live;

import assignment02.lib.report.Range;
import assignment02.lib.report.Statistic;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorBasedLiveReport extends LiveReport {

    private final ExecutorService handlerExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void notifyNumberOfFilesChanged(final int newNumber) {
        this.handlerExecutor.submit(() -> super.notifyNumberOfFilesChanged(newNumber));
    }

    @Override
    protected void notifyTopNChange(final List<Statistic> newTop) {
        this.handlerExecutor.submit(() -> super.notifyTopNChange(newTop));
    }

    @Override
    protected void notifyDistributionChange(final Map<Range, Integer> newDistribution) {
        this.handlerExecutor.submit(() -> super.notifyDistributionChange(newDistribution));
    }

}
