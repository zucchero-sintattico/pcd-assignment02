package assignment02.mvc;


import assignment02.lib.report.live.ExecutorBasedLiveReport;
import assignment02.lib.report.live.LiveReport;
import assignment02.mvc.controller.Controller;
import assignment02.mvc.controller.ControllerImpl;
import assignment02.mvc.view.View;
import assignment02.mvc.view.ViewImpl;

public class MainGUI {
    static public void main(String[] args) {

        final LiveReport model = new ExecutorBasedLiveReport();
        final Controller controller = new ControllerImpl(model);
        final View view = new ViewImpl();

        view.setController(controller);
        controller.setView(view);

        view.start();
    }
}

