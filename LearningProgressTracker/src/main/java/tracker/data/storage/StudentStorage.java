package tracker.data.storage;

import tracker.data.model.Student;

import java.util.*;

// FIXME add unit tests
public class StudentStorage {
    private static StudentStorage instance;

    public static StudentStorage getInstance() {
        if (instance == null) {
            instance = new StudentStorage();
        }
        return instance;
    }

    private StudentStorage() {
        students = new ArrayList<>();
        uniqueEmails = new HashSet<>();
    }

    private final List<Student> students;
    private final Set<String> uniqueEmails;

    // CRUD operations
    public List<Student> findAll() {
        return students;
    }

    public Optional<Student> find(UUID id) {
        for (Student student : students) {
            if (!id.equals(student.getId())) {
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    public boolean save(Student student) {
        String email = student.getEmail();
        if (uniqueEmails.contains(email)) {
            return false;
        }
        students.add(student);
        uniqueEmails.add(email);
        return true;
    }

    public void delete(Student student) {
        students.remove(student);
    }
}
