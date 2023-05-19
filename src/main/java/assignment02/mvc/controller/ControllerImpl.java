package assignment02.mvc.controller;

import assignment02.AnalyzerType;
import assignment02.SourceAnalyzer;
import assignment02.event.EventBasedSourceAnalyser;
import assignment02.executor.TaskBasedSourceAnalyzer;
import assignment02.lib.report.ObservableAsyncReport;
import assignment02.lib.report.ReportConfiguration;
import assignment02.mvc.view.View;
import assignment02.reactive.ReactiveSourceAnalyzer;
import assignment02.virtual.VirtualThreadBasedSourceAnalyzer;

import java.nio.file.Path;

public class ControllerImpl implements Controller {
    private ObservableAsyncReport model;
    private View view;
    private SourceAnalyzer analyzer;
    private ReportConfiguration reportConfiguration;

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
        if (this.model != null) {
            this.model.stop();
            this.analyzer.stop();
        }
    }
}
