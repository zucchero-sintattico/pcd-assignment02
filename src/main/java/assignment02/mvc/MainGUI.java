package assignment02.mvc;

import assignment.mvc.controller.Controller;
import assignment.mvc.controller.ControllerImpl;
import assignment.mvc.model.Model;
import assignment.mvc.model.ModelImpl;
import assignment.mvc.view.View;
import assignment.mvc.view.ViewImpl;

public class MainGUI {
    static public void main(String[] args) {

        final Model model = new ModelImpl();
        final Controller controller = new ControllerImpl(model);
        final View view = new ViewImpl();

        view.setController(controller);
        controller.setView(view);

        view.start();
    }
}

