package assignment.mvc.controller;

import assignment.mvc.view.View;

import java.nio.file.Path;

public interface Controller {

    void setView(View view);

    void startAlgorithm(Path path, int topN, int nOfIntervals, int maxL);

    void stopAlgorithm();

}
