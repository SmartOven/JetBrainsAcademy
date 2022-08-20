package tracker.data.storage;

import tracker.util.Course;

import java.util.*;

public class NotifiedStudentsStorage {
    private static NotifiedStudentsStorage instance;

    public static NotifiedStudentsStorage getInstance() {
        if (instance == null) {
            instance = new NotifiedStudentsStorage();
        }
        return instance;
    }

    private NotifiedStudentsStorage() {
        notified = new HashMap<>();
    }

    private final Map<UUID, Set<Course>> notified;

    public boolean isNotified(UUID studentID, Course completedCourse) {
        return notified.getOrDefault(studentID, Collections.emptySet()).contains(completedCourse);
    }

    public void setNotified(UUID studentID, Course completedCourse) {
        notified.putIfAbsent(studentID, new HashSet<>());
        notified.get(studentID).add(completedCourse);
    }
}
