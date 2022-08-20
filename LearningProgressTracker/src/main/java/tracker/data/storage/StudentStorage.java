package tracker.data.storage;

import tracker.data.model.Student;

import java.util.*;

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
        return new ArrayList<>(students);
    }

    public Optional<Student> find(UUID id) {
        for (Student student : students) {
            if (id.equals(student.getId())) {
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

    public int saveAll(List<Student> studentsList) {
        int countSaved = 0;
        for (Student student : studentsList) {
            if (save(student)) {
                countSaved++;
            }
        }
        return countSaved;
    }

    public boolean saveAll(List<Student> studentsList, boolean isTransactional) {
        if (!isTransactional) {
            saveAll(studentsList);
            return true;
        }
        Set<String> emailsSet = new HashSet<>(uniqueEmails);
        for (Student student : studentsList) {
            String email = student.getEmail();
            if (emailsSet.contains(email)) {
                return false;
            }
            emailsSet.add(email);
        }
        students.addAll(studentsList);
        return true;
    }

    public void delete(Student student) {
        String email = student.getEmail();
        if (!uniqueEmails.contains(email)) {
            return; // nothing to delete
        }
        students.remove(student);
        uniqueEmails.remove(email);
    }

    public void deleteAll() {
        students.clear();
        uniqueEmails.clear();
    }
}
