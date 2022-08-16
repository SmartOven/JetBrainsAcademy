package tracker.data.storage;

import tracker.data.model.Points;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PointsStorage {
    private static PointsStorage instance;

    public static PointsStorage getInstance() {
        if (instance == null) {
            instance = new PointsStorage();
        }
        return instance;
    }

    private PointsStorage() {
        studentPoints = new ArrayList<>();
    }

    private final List<Points> studentPoints;

    // CRUD operations
    public List<Points> findAll() {
        return studentPoints;
    }

    public Optional<Points> find(UUID studentID) {
        for (Points points : studentPoints) {
            if (studentID.equals(points.getStudentID())) {
                return Optional.of(points);
            }
        }
        return Optional.empty();
    }

    public void save(Points points) {
        studentPoints.add(points);
    }

    public void saveAll(List<Points> pointsList) {
        studentPoints.addAll(pointsList);
    }

    public void delete(Points points) {
        studentPoints.remove(points);
    }

    public void deleteAll() {
        studentPoints.clear();
    }
}
