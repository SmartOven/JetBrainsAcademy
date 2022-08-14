package tracker.ui.menu;

import org.junit.jupiter.api.*;
import tracker.util.ConsoleUtil;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {

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
    @DisplayName("All tests together")
    void resolveCommand() {
        // Testing
        MainMenu menu = MainMenu.getInstance();
        menu.resolveCommand("exit");
        assertNull(menu.getNextMenu());

        menu = MainMenu.getInstance();
        menu.resolveCommand("");
        assertEquals(menu, menu.getNextMenu());

        menu.resolveCommand("go back");
        assertEquals(menu, menu.getNextMenu());

        menu.resolveCommand("add students");
        assertEquals(menu.getNextMenu(), AddStudentsMenu.getInstance());
    }
}