package tracker.data.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracker.data.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentStorageTest {

    private final StudentStorage storage;

    // Test data
    private final Student s1;
    private final Student s2;
    private final Student s3;

    public StudentStorageTest() {
        storage = StudentStorage.getInstance();
        s1 = new Student("fn1", "ln1", "email1@example.com");
        s2 = new Student("fn2", "ln2", "email2@example.com");
        s3 = new Student("fn3", "ln3", "email2@example.com");
    }

    @BeforeEach
    void setUp() {
        storage.deleteAll();
    }

    @Test
    void findAll() {
        storage.saveAll(List.of(s1, s2));
        assertEquals(2, storage.findAll().size());
    }

    @Test
    void find() {
        storage.save(s1);
        storage.find(s1.getId());
    }

    @Test
    void save() {
        storage.save(s2);
        storage.find(s2.getId());
        boolean isSaved = storage.save(s3);
        assertFalse(isSaved);
    }

    @Test
    void saveAll() {
        int savedCount = storage.saveAll(List.of(s1, s2));
        assertEquals(2, storage.findAll().size());
        assertEquals(2, savedCount);
        storage.deleteAll();

        storage.saveAll(List.of(s1, s2, s3));
        assertEquals(2, storage.findAll().size());
        storage.deleteAll();

        boolean isSaved = storage.saveAll(List.of(s1, s2, s3), true);
        assertEquals(0, storage.findAll().size());
        assertFalse(isSaved);
        storage.deleteAll();
    }

    @Test
    void delete() {
        storage.saveAll(List.of(s1, s2));
        storage.delete(s1);
        assertNull(storage.find(s1.getId()).orElse(null));
    }
}