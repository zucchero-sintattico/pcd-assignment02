package assignment02.mvc;


import assignment02.mvc.controller.Controller;
import assignment02.mvc.controller.ControllerImpl;
import assignment02.mvc.view.View;
import assignment02.mvc.view.ViewImpl;

public class MainGUI {
    static public void main(String[] args) {

        final Controller controller = new ControllerImpl();
        final View view = new ViewImpl();

        view.setController(controller);
        controller.setView(view);

        view.start();
    }
}

