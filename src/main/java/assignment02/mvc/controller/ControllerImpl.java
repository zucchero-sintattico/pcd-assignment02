package assignment02.mvc.controller;

import assignment02.SourceAnalyzer;
import assignment02.event.EventBasedSourceAnalyser;
import assignment02.executor.TaskBasedSourceAnalyzer;
import assignment02.lib.report.ObservableAsyncReport;
import assignment02.lib.report.ReportConfiguration;
import assignment02.mvc.model.AlgorithmStatus;
import assignment02.mvc.view.View;
import assignment02.reactive.ReactiveSourceAnalyzer;
import assignment02.virtual.VirtualThreadBasedSourceAnalyzer;

import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

public class ControllerImpl implements Controller {
    private ObservableAsyncReport model;
    private View view;
    private SourceAnalyzer analyzer;
    private ReportConfiguration reportConfiguration;
    private AlgorithmStatus algorithmStatus = AlgorithmStatus.IDLE;

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void startAlgorithm(Path path, int topN, int nOfIntervals, int maxL, AnalyzerType analyzerType) {
        this.reportConfiguration = new ReportConfiguration(topN, nOfIntervals, maxL);
        this.setAnalyzer(analyzerType);
        this.model = this.analyzer.analyzeSources(path);
        this.registerModelListeners();
        this.algorithmStatus = AlgorithmStatus.RUNNING;
        this.view.updateAlgorithmStatus(this.algorithmStatus);
        this.observeAsyncCompletion();
    }

    /**
     * This method is used to observe the completion of the asynchronous computation.
     * It is implemented using a new thread that waits for the completion of the computation.
     * When the computation is completed, the algorithm status is updated.
     */
    private void observeAsyncCompletion() {
        new Thread(() -> {
            try {
                this.model.getReport().get();
                if (this.algorithmStatus != AlgorithmStatus.STOPPED) {
                    this.algorithmStatus = AlgorithmStatus.FINISHED;
                    this.view.updateAlgorithmStatus(this.algorithmStatus);
                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void registerModelListeners() {
        this.model.registerOnNumberOfFilesChanges(this.view::updateNumberOfFiles);
        this.model.registerOnTopNChange(this.view::updateTopN);
        this.model.registerOnDistributionChange(this.view::updateDistribution);
    }

    private void setAnalyzer(AnalyzerType analyzerType) {
        this.analyzer = switch (analyzerType) {
            case EVENT -> new EventBasedSourceAnalyser(this.reportConfiguration);
            case TASK -> new TaskBasedSourceAnalyzer(this.reportConfiguration);
            case VIRTUAL -> new VirtualThreadBasedSourceAnalyzer(this.reportConfiguration);
            case REACTIVE -> new ReactiveSourceAnalyzer(this.reportConfiguration);
        };
    }

    @Override
    public void stopAlgorithm() {
        if (this.algorithmStatus == AlgorithmStatus.RUNNING) {
            this.algorithmStatus = AlgorithmStatus.STOPPED;
            this.view.updateAlgorithmStatus(this.algorithmStatus);
            this.analyzer.stop();
        }
    }
}
