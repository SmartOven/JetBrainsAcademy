package carsharing;

import carsharing.db.MigrationManager;
import carsharing.util.menu.MainMenu;
import carsharing.util.menu.Menu;

import java.util.Scanner;

public class Manager {

    public static void work(Scanner console) {
        // Making necessary migrations to update database to the latest version
        MigrationManager migrationManager = MigrationManager.getInstance();
        migrationManager.migrate();

        Menu menu = new MainMenu();
        do {
            menu.render();
            String userInput = console.nextLine();
            menu = menu.doAction(userInput);
        } while (menu != null);
    }
}
