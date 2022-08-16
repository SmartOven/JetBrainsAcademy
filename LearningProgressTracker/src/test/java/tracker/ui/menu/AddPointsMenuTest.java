package tracker.ui.menu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tracker.data.model.Points;
import tracker.data.storage.PointsStorage;
import tracker.util.ConsoleUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AddPointsMenuTest {

    private final AddPointsMenu menu;
    private final PointsStorage storage;

    public AddPointsMenuTest() {
        menu = AddPointsMenu.getInstance();
        storage = PointsStorage.getInstance();
    }

    @BeforeEach
    void setUp() {
        // Set system out to byte array
        ConsoleUtil.setCustomSystemOut();
        storage.deleteAll();
    }

    @AfterEach
    void tearDown() {
        // Roll it back to standard system out
        ConsoleUtil.setDefaultSystemOut();
        storage.deleteAll();
    }

    @Test
    @DisplayName("AddPointsMenu resolveCommand smoke test")
    void resolveCommand() {
        UUID studentID = UUID.randomUUID();
        List<Integer> startPoints = List.of(0, 0, 0, 0);
        Points testPoints = new Points(studentID, startPoints);

        // Saving test data into storage
        storage.save(testPoints);

        // Update points, check if output is correct
        String validAddPointsRequest = studentID + " 0 1 2 3";
        menu.resolveCommand(validAddPointsRequest);
        assertEquals("Points updated.", ConsoleUtil.getOutputString());

        // Get updated points, check if they exist
        Optional<Points> studentPoints = storage.find(studentID);
        assertTrue(studentPoints.isPresent());

        List<Integer> updatedPoints = studentPoints.get().getCoursesPoints();
        for (int i = 0; i < 4; i++) {
            assertEquals(i, updatedPoints.get(i) - startPoints.get(i));
        }
    }

    // FIXME
    //  Add more tests (invalid input, valid input)
}