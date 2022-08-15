package tracker.ui.menu;

import tracker.data.model.Student;
import tracker.data.storage.StudentStorage;
import tracker.util.validator.EmailValidator;
import tracker.util.validator.NameValidator;
import tracker.util.StringUtil;

public class AddStudentsMenu extends Menu {

    private static AddStudentsMenu instance;

    public static AddStudentsMenu getInstance() {
        if (instance == null) {
            instance = new AddStudentsMenu();
            nextMenu = instance;
            instance.onCreate();
        }
        return instance;
    }

    private AddStudentsMenu() {
        studentStorage = StudentStorage.getInstance();
    }

    private int studentsAdded;
    private final StudentStorage studentStorage;

    @Override
    public void onCreate() {
        studentsAdded = 0;
        System.out.println("Enter student credentials or 'back' to return:");
    }

    @Override
    public void resolveCommand(String command) {
        if (command == null || command.isEmpty() || command.isBlank()) {
            System.out.println("Incorrect credentials.");
            return;
        }

        // If user wants to stop adding students
        if ("back".equals(command)) {
            System.out.printf("Total %d students have been added.%n", studentsAdded);
            nextMenu = MainMenu.getInstance();
            return;
        }

        // Look for firstName, lastName and email whitespace separators
        Integer[] firstWhitespaces = StringUtil.findFirstWhitespacesSequence(command);
        Integer[] lastWhitespaces = StringUtil.findLastWhitespacesSequence(command);

        if (firstWhitespaces == null || firstWhitespaces[0].equals(lastWhitespaces[0])) {
            System.out.println("Incorrect credentials.");
            return;
        }

        // Split name and email
        String name = command.substring(0, lastWhitespaces[0]);
        String email = command.substring(lastWhitespaces[1] + 1);

        NameValidator nameValidator = new NameValidator(name);
        EmailValidator emailValidator = new EmailValidator(email);

        if (!nameValidator.isFirstNameValid()) {
            System.out.println("Incorrect first name.");
            return;
        }

        if (!nameValidator.isLastNameValid()) {
            System.out.println("Incorrect last name.");
            return;
        }

        if (!emailValidator.isValid()) {
            System.out.println("Incorrect email.");
            return;
        }

        // FIXME add unit tests for the block from here
        String firstName = nameValidator.getFirstName();
        String lastName = nameValidator.getLastName();
        Student student = new Student(firstName, lastName, email);

        boolean studentSaved = studentStorage.save(student);
        if (!studentSaved) {
            System.out.println("This email is already taken.");
            return;
        }
        // FIXME to here

        studentsAdded++;
        System.out.println("The student has been added.");
    }

    private void registerStudent() {

    }
}
