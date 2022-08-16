package tracker.data.model;

import java.util.List;
import java.util.UUID;

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
        if (coursesPoints == null || coursesPoints.size() != 4) {
            int count = (coursesPoints == null) ? 0 : coursesPoints.size();
            throw new IllegalArgumentException("Required courses count is 4. Actual count is " + count);
        }
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
