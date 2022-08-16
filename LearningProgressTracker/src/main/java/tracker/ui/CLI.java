package tracker.ui;

import tracker.ui.menu.MainMenu;
import tracker.ui.menu.Menu;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Command line user interface for the project
 */
public class CLI {

    private static CLI instance;

    public static CLI getInstance(InputStream inputStream) {
        if (instance == null) {
            instance = new CLI(inputStream);
        }
        return instance;
    }

    private final Scanner scanner;

    private CLI(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public void handleCommands() {
        System.out.println("Learning Progress Tracker");

        Menu menu = MainMenu.getInstance();

        while (menu != null) {
            menu.render();
            menu.resolveCommand(scanner.nextLine());
            menu = menu.getNextMenu();
        }

        System.out.println("Bye");
    }
}
