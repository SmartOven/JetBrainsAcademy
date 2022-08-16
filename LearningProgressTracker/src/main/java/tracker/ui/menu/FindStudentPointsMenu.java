package tracker.ui.menu;

import tracker.data.model.Points;
import tracker.data.storage.PointsStorage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FindStudentPointsMenu extends Menu {

    private static FindStudentPointsMenu instance;

    public static FindStudentPointsMenu getInstance() {
        if (instance == null) {
            instance = new FindStudentPointsMenu();
        }
        return instance;
    }

    private FindStudentPointsMenu() {
        super();
    }

    @Override
    public void onCreate() {
        System.out.println("Enter an id or 'back' to return");
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

        // Parsing student ID
        UUID studentID;
        try {
            studentID = UUID.fromString(command);
        } catch (Exception e) {
            System.out.println("No student is found for id=" + command);
            return;
        }

        // Finding student points by his ID
        Optional<Points> findStudentPoints = PointsStorage.getInstance().find(studentID);
        if (findStudentPoints.isEmpty()) {
            System.out.println("No student is found for id=" + command);
            return;
        }

        // Printing student courses points
        List<Integer> coursesPoints = findStudentPoints.get().getCoursesPoints();
        String studentPointsString = studentID + " points: " +
                "Java=" + coursesPoints.get(0) + "; " +
                "DSA=" + coursesPoints.get(1) + "; " +
                "Databases=" + coursesPoints.get(2) + "; " +
                "Spring=" + coursesPoints.get(3);
        System.out.println(studentPointsString);
    }
}
