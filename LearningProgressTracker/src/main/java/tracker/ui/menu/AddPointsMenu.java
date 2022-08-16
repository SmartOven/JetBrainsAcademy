package tracker.ui.menu;

import tracker.data.model.Points;
import tracker.data.storage.PointsStorage;
import tracker.util.validator.AddPointsRequestValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddPointsMenu extends Menu {

    private static AddPointsMenu instance;

    public static AddPointsMenu getInstance() {
        if (instance == null) {
            instance = new AddPointsMenu();
        }
        return instance;
    }

    private AddPointsMenu() {
        super();
    }

    @Override
    public void onCreate() {
        System.out.println("Enter an id and points or 'back' to return:");
    }

    @Override
    public void resolveCommand(String command) {
        // By default, we think that menu won't change
        setNextMenu(this);

        // Go back to main menu
        if ("back".equals(command)) {
            setNextMenu(MainMenu.getInstance());
            return;
        }

        // Try to add points to the existing student
        AddPointsRequestValidator validator = new AddPointsRequestValidator(command);

        // Incorrect request
        if (!validator.isValid()) {
            // Check if passed UUID was valid
            if (!validator.isValidID()) {
                System.out.println("No student is found for id=" + validator.getGivenID());
                return;
            }

            // If it was, then it is something wrong with points format
            System.out.println("Incorrect points format.");
            return;
        }

        UUID studentID = validator.getId();
        Optional<Points> findStudentPoints = PointsStorage.getInstance().find(studentID);

        // No student found
        if (findStudentPoints.isEmpty()) {
            System.out.println("No student is found for id=" + studentID);
            return;
        }

        // Getting additional points and current points
        List<Integer> additionalPoints = validator.getAdditionalPoints();
        Points studentPoints = findStudentPoints.get();

        // Updating points
        studentPoints.addPoints(additionalPoints);

        System.out.println("Points updated.");
    }
}
