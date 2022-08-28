package advisor;

import advisor.controller.Controller;
import advisor.view.View;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Command line user interface for the project
 * Combines Controller and View:
 * 1. Controller receives user input from the console
 * 2. View prints data into the console
 */
public class CLI {

    private static CLI instance;

    public static CLI getInstance(InputStream inputStream) {
        return getInstance(new Scanner(inputStream));
    }

    public static CLI getInstance(Scanner scanner) {
        if (instance == null) {
            instance = new CLI(scanner);
        }
        return instance;
    }

    private final Scanner scanner;

    private CLI(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Creates Controller and View
     * Until controller finish his work with user it receives commands from console and resolve them
     * View returns current info, that is being printed into the console
     */
    public void work() {
        Controller controller = Controller.getInstance();
        View view = View.getInstance();

        while (!controller.isFinishedWork()) {
            String command = scanner.nextLine();
            controller.resolveInput(command);
            System.out.println(view.getViewString());
        }
    }
}
