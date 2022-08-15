package tracker.data.model;

import java.util.List;
import java.util.UUID;

// FIXME add unit tests
public class Points {
    private final UUID studentID;
    private final List<Integer> coursesPoints;

    public Points(UUID studentID) {
        this(studentID, 0, 0, 0, 0);
    }

    public Points(UUID studentID, int javaPoints, int dsaPoints, int databasesPoints, int springPoints) {
        this(studentID, List.of(javaPoints, dsaPoints, databasesPoints, springPoints));
    }

    public Points(UUID studentID, List<Integer> coursesPoints) {
        this.studentID = studentID;
        this.coursesPoints = coursesPoints;
    }

    public UUID getStudentID() {
        return studentID;
    }

    public List<Integer> getCoursesPoints() {
        return coursesPoints;
    }

    public void addPoints(int javaPoints, int dsaPoints, int databasesPoints, int springPoints) {
        coursesPoints.set(0, coursesPoints.get(0) + javaPoints);
        coursesPoints.set(1, coursesPoints.get(1) + dsaPoints);
        coursesPoints.set(2, coursesPoints.get(2) + databasesPoints);
        coursesPoints.set(3, coursesPoints.get(3) + springPoints);
    }
}
