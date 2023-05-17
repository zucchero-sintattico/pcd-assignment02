package assignment02.virtual;

import assignment02.executor.TaskBasedSourceAnalyzer;
import assignment02.lib.report.ReportConfiguration;

import java.util.concurrent.Executors;

public class VirtualThreadBasedSourceAnalyzer extends TaskBasedSourceAnalyzer {
    public VirtualThreadBasedSourceAnalyzer(final ReportConfiguration configuration) {
        super(configuration, Executors.newVirtualThreadPerTaskExecutor());
    }
}
