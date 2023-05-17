package assignment02.mvc.view;


import assignment02.lib.report.Range;
import assignment02.lib.report.Statistic;
import assignment02.mvc.controller.Controller;
import assignment02.mvc.model.AlgorithmStatus;

import java.util.List;
import java.util.Map;


public interface View {

    void setController(Controller controller);

    void updateAlgorithmStatus(AlgorithmStatus status);

    void updateTopN(List<Statistic> stats);

    void updateDistribution(Map<Range, Integer> distribution);

    void updateNumberOfFiles(int numberOfFiles);

    void start();
}
