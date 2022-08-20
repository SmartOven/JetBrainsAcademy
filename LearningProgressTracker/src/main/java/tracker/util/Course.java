package tracker.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Course {
    JAVA,
    DSA,
    DATABASES,
    SPRING;

    public static final Map<Course, Integer> courseCompletionPoints;
    private static final Map<Course, String> courseToString;
    private static final Map<String, Course> stringToCourse;

    static {
        courseCompletionPoints = new HashMap<>();
        courseCompletionPoints.put(Course.JAVA, 600);
        courseCompletionPoints.put(Course.DSA, 400);
        courseCompletionPoints.put(Course.DATABASES, 480);
        courseCompletionPoints.put(Course.SPRING, 550);

        courseToString = new HashMap<>();
        courseToString.put(Course.JAVA, "Java");
        courseToString.put(Course.DSA, "DSA");
        courseToString.put(Course.DATABASES, "Databases");
        courseToString.put(Course.SPRING, "Spring");

        stringToCourse = new HashMap<>();
        for (Map.Entry<Course, String> entry : courseToString.entrySet()) {
            stringToCourse.put(entry.getValue().toLowerCase(Locale.ROOT), entry.getKey());
        }
    }

    public static Course fromString(String s) {
        Course course = stringToCourse.getOrDefault(s, null);
        if (course == null) {
            throw new IllegalArgumentException("Unknown course string!");
        }
        return course;
    }

    public boolean isCompleted(int points) {
        return points >= courseCompletionPoints.get(this);
    }

    @Override
    public String toString() {
        return courseToString.get(this);
    }
}
