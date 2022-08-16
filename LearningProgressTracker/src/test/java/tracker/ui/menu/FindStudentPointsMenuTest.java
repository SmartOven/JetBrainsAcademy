package tracker.ui.menu;

import org.junit.jupiter.api.*;
import tracker.data.model.Points;
import tracker.data.storage.PointsStorage;
import tracker.util.ConsoleUtil;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FindStudentPointsMenuTest {

    private FindStudentPointsMenu menu;

    public FindStudentPointsMenuTest() {
        menu = FindStudentPointsMenu.getInstance();
    }

    @BeforeEach
    void setUp() {
        // Set system out to byte array
        ConsoleUtil.setCustomSystemOut();
        menu = FindStudentPointsMenu.getInstance();
        PointsStorage.getInstance().deleteAll();
    }

    @AfterEach
    void tearDown() {
        // Roll it back to standard system out
        ConsoleUtil.setDefaultSystemOut();
    }

    @AfterAll
    static void afterAll() {
        PointsStorage.getInstance().deleteAll();
    }

    @Test
    @DisplayName("resolveCommand smoke test")
    void resolveCommand() {
        // Saving test data
        UUID id = UUID.randomUUID();
        PointsStorage.getInstance().save(new Points(id, 1, 2, 3, 4));

        menu.resolveCommand(id.toString());

        String expected = id + " points: Java=1; DSA=2; Databases=3; Spring=4";
        assertEquals(expected, ConsoleUtil.getOutputString());
    }

    // FIXME
    //  Add more tests
}