package tracker.ui.menu;

import tracker.util.EmailValidator;
import tracker.util.NameValidator;

import java.util.Arrays;

public class AddStudentsMenu extends Menu {

    @Override
    public void renderOnCreate() {
        System.out.println("Enter student credentials or 'back' to return:");
    }

    private static AddStudentsMenu instance;

    public static AddStudentsMenu getInstance() {
        if (instance == null) {
            instance = new AddStudentsMenu();
            nextMenu = instance;
        }
        return instance;
    }

    private AddStudentsMenu() {}

    @Override
    public void resolveCommand(String command) {
        // If user wants to stop adding students
        if ("back".equals(command)) {
            nextMenu = MainMenu.getInstance();
            return;
        }

        // Splitting user input into parts by the whitespace
        String[] inputParts = command.split("\\s+");

        // Last part is his email, all others are parts of his name
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < inputParts.length - 1; i++) {
            nameBuilder.append(inputParts[i]);
        }

        String name = nameBuilder.toString();
        String email = inputParts[inputParts.length - 1];

        NameValidator nameValidator = new NameValidator(name);
        EmailValidator emailValidator = new EmailValidator(email);


    }
}
