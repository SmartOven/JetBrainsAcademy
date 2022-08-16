package tracker.data.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tracker.data.model.Points;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PointsStorageTest {

    private final PointsStorage storage;

    // Test data
    private final Points p1;
    private final Points p2;

    public PointsStorageTest() {
        storage = PointsStorage.getInstance();
        p1 = new Points(UUID.randomUUID(), 0, 0, 0, 0);
        p2 = new Points(UUID.randomUUID(), 1, 1, 1, 1);
    }

    @BeforeEach
    void setUp() {
        storage.deleteAll();
    }

    @Test
    void findAll() {
        storage.save(p1);
        storage.save(p2);
        List<Points> storagePoints = storage.findAll();
        assertEquals(2, storagePoints.size());
        assertEquals(p1, storagePoints.get(0));
        assertEquals(p2, storagePoints.get(1));
    }

    @Test
    void find() {
        storage.save(p1);
        Points points = storage.find(p1.getStudentID()).orElse(null);
        assertNotNull(points);
        assertEquals(p1, points);
    }

    @Test
    void save() {
        storage.save(p1);
        assertEquals(1, storage.findAll().size());
        storage.save(p2);
        assertEquals(2, storage.findAll().size());
    }

    @Test
    void saveAll() {
        storage.saveAll(List.of(p1, p2));
        assertEquals(2, storage.findAll().size());
    }

    @Test
    void delete() {
        storage.save(p1);
        storage.save(p2);
        assertEquals(2, storage.findAll().size());
        assertEquals(p1, storage.find(p1.getStudentID()).orElse(null));
        storage.delete(p1);
        assertEquals(1, storage.findAll().size());
        assertNull(storage.find(p1.getStudentID()).orElse(null));
    }
}