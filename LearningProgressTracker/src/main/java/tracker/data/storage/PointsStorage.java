package tracker.data.storage;

import tracker.data.model.Points;

import java.util.*;

public class PointsStorage {
    private static PointsStorage instance;

    public static PointsStorage getInstance() {
        if (instance == null) {
            instance = new PointsStorage();
        }
        return instance;
    }

    private PointsStorage() {
        studentPoints = new HashMap<>();
        studentPointsList = new LinkedList<>();
    }

    private final Map<UUID, Points> studentPoints;
    private final List<Points> studentPointsList;

    // CRUD operations
    public List<Points> findAll() {
        return new ArrayList<>(studentPointsList);
    }

    public Optional<Points> find(UUID studentID) {
        Points points = studentPoints.getOrDefault(studentID, null);
        if (points == null) {
            return Optional.empty();
        }
        return Optional.of(points);
    }

    public void save(Points points) {
        studentPoints.put(points.getStudentID(), points);
        studentPointsList.add(points);
    }

    public void saveAll(List<Points> pointsList) {
        for (Points points : pointsList) {
            save(points);
        }
    }

    public void delete(Points points) {
        studentPoints.remove(points.getStudentID());
        studentPointsList.remove(points);
    }

    public void deleteAll() {
        studentPoints.clear();
        studentPointsList.clear();
    }
}
