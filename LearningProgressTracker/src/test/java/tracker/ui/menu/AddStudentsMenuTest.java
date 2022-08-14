package tracker.ui.menu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tracker.util.ConsoleUtil;

import static org.junit.jupiter.api.Assertions.*;

class AddStudentsMenuTest {

    @BeforeEach
    void setUp() {
        // Set system out to byte array
        ConsoleUtil.setCustomSystemOut();
    }

    @AfterEach
    void tearDown() {
        // Roll it back to standard system out
        ConsoleUtil.setDefaultSystemOut();
    }

    @Test
    @DisplayName("Smoke test")
    void resolveCommand() {
        AddStudentsMenu menu = AddStudentsMenu.getInstance();

        menu.resolveCommand("aa bb email@example.com");
        assertEquals("The student has been added.", ConsoleUtil.getOutputLines()[1]);

        menu.resolveCommand("back");
        assertEquals("Total 1 students have been added.", ConsoleUtil.getOutputLines()[2]);
    }
}