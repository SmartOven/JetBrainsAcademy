package tracker.ui.menu;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tracker.util.ConsoleUtil;

import static org.junit.jupiter.api.Assertions.*;

class AddStudentsMenuTest {

    private final AddStudentsMenu menu;

    public AddStudentsMenuTest() {
        menu = AddStudentsMenu.getInstance();
    }

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
    @DisplayName("Resolving commands smoke test")
    void resolveSimpleCommand() {
        menu.resolveCommand("aa bb email@example.com");
        assertEquals("The student has been added.", ConsoleUtil.getOutputLines()[0]);

        menu.resolveCommand("back");
        assertEquals("Total 1 students have been added.", ConsoleUtil.getOutputLines()[1]);
    }
}