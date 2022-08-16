package tracker.ui.menu;

import tracker.data.model.Points;
import tracker.data.model.Student;
import tracker.data.storage.PointsStorage;
import tracker.data.storage.StudentStorage;
import tracker.util.StringUtil;
import tracker.util.validator.EmailValidator;
import tracker.util.validator.NameValidator;

public class AddStudentsMenu extends Menu {

    private static AddStudentsMenu instance;

    public static AddStudentsMenu getInstance() {
        if (instance == null) {
            instance = new AddStudentsMenu();
        }
        return instance;
    }

    private AddStudentsMenu() {
        super();
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
        // By default, we think that menu won't change
        setNextMenu(this);

        if (command == null || command.isEmpty() || command.isBlank()) {
            System.out.println("Incorrect credentials.");
            return;
        }

        // If user wants to stop adding students
        if ("back".equals(command)) {
            System.out.printf("Total %d students have been added.%n", studentsAdded);
            setNextMenu(MainMenu.getInstance());
            return;
        }

        try {
            registerStudent(command);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        studentsAdded++;
        System.out.println("The student has been added.");
    }

    private void registerStudent(String command) {
        // Look for firstName, lastName and email whitespace separators
        Integer[] firstWhitespaces = StringUtil.findFirstWhitespacesSequence(command);
        Integer[] lastWhitespaces = StringUtil.findLastWhitespacesSequence(command);

        if (firstWhitespaces == null || firstWhitespaces[0].equals(lastWhitespaces[0])) {
            throw new IllegalArgumentException("Incorrect credentials.");
        }

        // Split name and email
        String name = command.substring(0, lastWhitespaces[0]);
        String email = command.substring(lastWhitespaces[1] + 1);

        NameValidator nameValidator = new NameValidator(name);
        EmailValidator emailValidator = new EmailValidator(email);

        if (!nameValidator.isFirstNameValid()) {
            throw new IllegalArgumentException("Incorrect first name.");
        }
        if (!nameValidator.isLastNameValid()) {
            throw new IllegalArgumentException("Incorrect last name.");
        }
        if (!emailValidator.isValid()) {
            throw new IllegalArgumentException("Incorrect email.");
        }

        String firstName = nameValidator.getFirstName();
        String lastName = nameValidator.getLastName();
        Student student = new Student(firstName, lastName, email);

        boolean studentSaved = studentStorage.save(student);
        if (!studentSaved) {
            throw new IllegalArgumentException("This email is already taken.");
        }

        Points studentPoints = new Points(student.getId(), 0, 0, 0, 0);
        PointsStorage.getInstance().save(studentPoints);
    }
}
