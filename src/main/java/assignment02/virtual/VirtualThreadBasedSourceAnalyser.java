package assignment02.virtual;

import assignment02.ReportConfiguration;
import assignment02.executor.TaskBasedSourceAnalyzer;

import java.util.concurrent.Executors;

public class VirtualThreadBasedSourceAnalyser extends TaskBasedSourceAnalyzer {
    public VirtualThreadBasedSourceAnalyser(final ReportConfiguration configuration) {
        super(configuration, Executors.newVirtualThreadPerTaskExecutor());
    }
}
