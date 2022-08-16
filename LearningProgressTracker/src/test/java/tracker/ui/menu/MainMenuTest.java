package tracker.ui.menu;

import org.junit.jupiter.api.*;
import tracker.data.model.Student;
import tracker.data.storage.StudentStorage;
import tracker.util.ConsoleUtil;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MainMenuTest {
    private MainMenu menu;

    public MainMenuTest() {
        menu = MainMenu.getInstance();
    }

    @BeforeEach
    void setUp() {
        // Set system out to byte array
        ConsoleUtil.setCustomSystemOut();
        menu = MainMenu.getInstance();
    }

    @AfterEach
    void tearDown() {
        // Roll it back to standard system out
        ConsoleUtil.setDefaultSystemOut();
    }

    @AfterAll
    static void afterAll() {
        StudentStorage.getInstance().deleteAll();
    }

    @Test
    void exitCommand() {
        menu.resolveCommand("exit");
        assertNull(menu.getNextMenu());
    }

    @Test
    void emptyCommand() {
        menu.resolveCommand("");
        assertEquals(menu, menu.getNextMenu());
        assertEquals("No input.", ConsoleUtil.getOutputString());
    }

    @Test
    void unknownCommand() {
        menu.resolveCommand("im not a command lol");
        assertEquals(menu, menu.getNextMenu());
    }

    @Test
    void wrongExitCommand() {
        menu.resolveCommand("back");
        assertEquals(menu, menu.getNextMenu());
        assertEquals("Enter 'exit' to exit the program", ConsoleUtil.getOutputString());
    }

    @Test
    void addStudentsCommand() {
        menu.resolveCommand("add students");
        assertEquals(menu.getNextMenu(), AddStudentsMenu.getInstance());
    }

    // May be later
    //    @ParameterizedTest
    //    @CsvSource("")
    @DisplayName("Add students and get the list of students")
    @Test
    void simpleTest() {
        Student s1 = new Student("fn1", "fn1", "email1@example.com");
        Student s2 = new Student("fn2", "fn2", "email2@example.com");
        Student s3 = new Student("fn3", "fn3", "email3@example.com");
        StudentStorage.getInstance().saveAll(List.of(s1, s2, s3));

        // Assert that all three students were printed in the way they were added
        menu.resolveCommand("list");

        String studentsIDsOutput = StudentStorage
                .getInstance()
                .findAll()
                .stream()
                .map(student -> student.getId().toString())
                .collect(Collectors.joining("\r\n"));

        StringBuilder expectedOutputBuilder = new StringBuilder("Students:");
        if (!studentsIDsOutput.isEmpty() && !studentsIDsOutput.isBlank()) {
            expectedOutputBuilder.append("\r\n");
            expectedOutputBuilder.append(studentsIDsOutput);
        }

        assertEquals(expectedOutputBuilder.toString(), ConsoleUtil.getOutputString());
    }

    @Test
    void emptyStudentList() {
        StudentStorage.getInstance().deleteAll();
        menu.resolveCommand("list");
        assertEquals("No students found", ConsoleUtil.getOutputString());
    }
}