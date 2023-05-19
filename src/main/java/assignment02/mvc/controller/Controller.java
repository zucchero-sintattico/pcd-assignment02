package assignment02.mvc.controller;

import assignment02.mvc.view.View;

import java.nio.file.Path;

public interface Controller {
    
    void setView(View view);

    void startAlgorithm(Path path, int topN, int nOfIntervals, int maxL, AnalyzerType analyzerType);

    void stopAlgorithm();

}
