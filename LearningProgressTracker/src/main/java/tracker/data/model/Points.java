package tracker.data.model;

import tracker.util.Course;

import java.util.*;

public class Points {
    private final UUID studentID;
    private final Map<Course, Integer> coursesPoints;

    public Points(UUID studentID) {
        this(studentID, 0, 0, 0, 0);
    }

    public Points(UUID studentID, int javaPoints, int dsaPoints, int databasesPoints, int springPoints) {
        this(studentID, List.of(javaPoints, dsaPoints, databasesPoints, springPoints));
    }

    public Points(UUID studentID, List<Integer> coursesPointsList) {
        if (coursesPointsList == null || coursesPointsList.size() != Course.values().length) {
            int count = (coursesPointsList == null) ? 0 : coursesPointsList.size();
            throw new IllegalArgumentException("Required courses count is " + Course.values().length + " . Actual count is " + count);
        }
        this.studentID = studentID;
        coursesPoints = new HashMap<>();
        coursesPoints.put(Course.JAVA, coursesPointsList.get(0));
        coursesPoints.put(Course.DSA, coursesPointsList.get(1));
        coursesPoints.put(Course.DATABASES, coursesPointsList.get(2));
        coursesPoints.put(Course.SPRING, coursesPointsList.get(3));
    }

    public UUID getStudentID() {
        return studentID;
    }

    public List<Integer> getCoursesPoints() {
        return List.of(
                coursesPoints.get(Course.JAVA),
                coursesPoints.get(Course.DSA),
                coursesPoints.get(Course.DATABASES),
                coursesPoints.get(Course.SPRING)
        );
    }

    public void addPoints(int javaPoints, int dsaPoints, int databasesPoints, int springPoints) {
        coursesPoints.merge(Course.JAVA, javaPoints, Integer::sum);
        coursesPoints.merge(Course.DSA, dsaPoints, Integer::sum);
        coursesPoints.merge(Course.DATABASES, databasesPoints, Integer::sum);
        coursesPoints.merge(Course.SPRING, springPoints, Integer::sum);
    }

    public void addPoints(List<Integer> additionalPoints) {
        if (additionalPoints == null || additionalPoints.size() != 4) {
            throw new IllegalArgumentException("List is null or it's size is not equals to courses count");
        }
        addPoints(
                additionalPoints.get(0),
                additionalPoints.get(1),
                additionalPoints.get(2),
                additionalPoints.get(3)
        );
    }

    public int getCoursePoints(Course course) {
        return coursesPoints.get(course);
    }
}
