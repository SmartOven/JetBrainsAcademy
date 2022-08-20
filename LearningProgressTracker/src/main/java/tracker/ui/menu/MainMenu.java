package tracker.ui.menu;

import tracker.data.model.Points;
import tracker.data.model.Student;
import tracker.data.storage.NotifiedStudentsStorage;
import tracker.data.storage.PointsStorage;
import tracker.data.storage.StudentStorage;
import tracker.util.Course;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class MainMenu extends Menu {

    private static MainMenu instance;

    public static MainMenu getInstance() {
        if (instance == null) {
            instance = new MainMenu();
        }
        return instance;
    }

    private MainMenu() {
        super();
    }

    @Override
    public void resolveCommand(String command) {
        // By default, we think that menu won't change
        setNextMenu(this);

        // No user input
        if (command == null || command.isEmpty() || command.isBlank()) {
            System.out.println("No input.");
            return;
        }

        // User wants to exit
        if ("exit".equals(command)) {
            setNextMenu(null);
            return;
        }

        // User inputs wrong exit command
        if ("back".equals(command)) {
            System.out.println("Enter 'exit' to exit the program");
            return;
        }

        // Adding students
        if ("add students".equals(command)) {
            setNextMenu(AddStudentsMenu.getInstance());
            return;
        }

        // Printing existing students ids
        if ("list".equals(command)) {
            List<UUID> ids = getStudentsIDsList();

            if (ids.isEmpty()) {
                System.out.println("No students found");
                return;
            }

            System.out.println("Students:");
            getStudentsIDsList().forEach(System.out::println);
            return;
        }

        // Add points
        if ("add points".equals(command)) {
            setNextMenu(AddPointsMenu.getInstance());
            return;
        }

        // Find student points by his ID
        if ("find".equals(command)) {
            setNextMenu(FindStudentPointsMenu.getInstance());
            return;
        }

        // Show statistics
        if ("statistics".equals(command)) {
            setNextMenu(StatisticsMenu.getInstance());
            return;
        }

        // Notify students who had completed the courses
        if ("notify".equals(command)) {
            Set<UUID> notifiedNow = new HashSet<>();
            PointsStorage pointsStorage = PointsStorage.getInstance();
            NotifiedStudentsStorage notifiedStorage = NotifiedStudentsStorage.getInstance();

            // Iterate every student points
            for (Points points : pointsStorage.findAll()) {
                UUID studentID = points.getStudentID();

                // Iterate every course
                for (Course course : Course.values()) {
                    int coursePoints = points.getCoursePoints(course);

                    // If student completed the course and wasn't notified about that -> notify
                    if (course.isCompleted(coursePoints) && !notifiedStorage.isNotified(studentID, course)) {
                        notifiedNow.add(studentID);
                        notifiedStorage.setNotified(studentID, course);
                        Student student = StudentStorage.getInstance().find(studentID).orElse(null);
                        assert student != null;
                        System.out.printf(
                                "To: %s%nRe: Your Learning Progress%nHello, %s! You have accomplished our %s course!%n",
                                student.getEmail(),
                                student.getFirstName() + " " + student.getLastName(),
                                course
                        );
                    }
                }
            }
            System.out.printf("Total %d students have been notified.%n", notifiedNow.size());
            return;
        }

        // Unknown user command
        System.out.println("Unknown command!");
    }

    private List<UUID> getStudentsIDsList() {
        return StudentStorage
                .getInstance()
                .findAll()
                .stream()
                .map(Student::getId)
                .collect(Collectors.toList());
    }
}
