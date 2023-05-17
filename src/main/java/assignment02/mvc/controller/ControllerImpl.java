package assignment02.mvc.controller;

import assignment02.AnalyzerType;
import assignment02.SourceAnalyzer;
import assignment02.event.EventBasedSourceAnalyser;
import assignment02.executor.TaskBasedSourceAnalyzer;
import assignment02.lib.report.Report;
import assignment02.lib.report.ReportConfiguration;
import assignment02.lib.report.live.LiveReport;
import assignment02.mvc.view.*;
import assignment02.reactive.ReactiveSourceAnalyzer;
import assignment02.virtual.VirtualThreadBasedSourceAnalyzer;

import java.nio.file.Path;

public class ControllerImpl implements Controller {
    private View view;
    private SourceAnalyzer analyzer;
    private ReportConfiguration reportConfiguration;
    private final LiveReport model;


    public ControllerImpl(LiveReport model) {
        this.model = model;
    }

    @Override
    public void setView(View view) {
        this.view = view;
        this.model.registerOnTopNChange((topN) -> this.view.updateTopN(topN));
        this.model.registerOnDistributionChange((distribution) -> this.view.updateDistribution(distribution));
        this.model.registerOnNumberOfFilesChanges((numberOfFiles) -> this.view.updateNumberOfFiles(numberOfFiles));
    }

    @Override
    public void startAlgorithm(Path path, int topN, int nOfIntervals, int maxL, AnalyzerType analyzerType) {
        this.reportConfiguration = new ReportConfiguration(topN, nOfIntervals, maxL);
        this.setAnalyzer(analyzerType);
        this.model.setReportConfiguration(this.reportConfiguration);
        // todo???
    }

    private void setAnalyzer(AnalyzerType analyzerType) {
        switch (analyzerType) {
            case EVENT -> this.analyzer = new EventBasedSourceAnalyser(this.reportConfiguration);
            case TASK -> this.analyzer = new TaskBasedSourceAnalyzer(this.reportConfiguration);
            case VIRTUAL -> this.analyzer = new VirtualThreadBasedSourceAnalyzer(this.reportConfiguration);
            case REACTIVE -> this.analyzer = new ReactiveSourceAnalyzer(this.reportConfiguration);
        }
    }

    @Override
    public void stopAlgorithm() {
        // todo
    }
}
