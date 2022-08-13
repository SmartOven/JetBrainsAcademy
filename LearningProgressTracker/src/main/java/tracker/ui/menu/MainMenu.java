package tracker.ui.menu;

public class MainMenu extends Menu {

    private static MainMenu instance;

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
            nextMenu = instance;
        }
        return instance;
    }

    private MainMenu() {}

    @Override
    public void resolveCommand(String command) {
        // No user input
        if (command == null || command.isEmpty() || command.isBlank()) {
            System.out.println("No input.");
            return;
        }

        // User wants to exit
        if ("exit".equals(command)) {
            nextMenu = null;
            return;
        }

        // Adding students
        if ("add students".equals(command)) {
            nextMenu = AddStudentsMenu.getInstance();
            return;
        }

        // Unknown user command
        System.out.println("Error: unknown command!");
    }
}
