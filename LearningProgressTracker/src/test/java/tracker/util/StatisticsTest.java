package tracker.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tracker.ui.menu.AddPointsMenu;
import tracker.ui.menu.AddStudentsMenu;
import tracker.ui.menu.MainMenu;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsTest {

    @BeforeAll
    static void beforeAll() {
        // Add students
        AddStudentsMenu studentsMenu = AddStudentsMenu.getInstance();
        studentsMenu.resolveCommand("John Doe johnd@email.net");
        studentsMenu.resolveCommand("Jane Spark jspark@yahoo.com");

        // Get their UUIDs
        MainMenu mainMenu = MainMenu.getInstance();
        ConsoleUtil.setCustomSystemOut();
        mainMenu.resolveCommand("list");
        String[] ids = ConsoleUtil.getOutputLines();
        UUID id1 = UUID.fromString(ids[1]);
        UUID id2 = UUID.fromString(ids[2]);
        ConsoleUtil.setDefaultSystemOut();

        // Add them points
        AddPointsMenu pointsMenu = AddPointsMenu.getInstance();
        pointsMenu.resolveCommand(String.format("%s %d %d %d %d", id1, 8, 7, 7, 5));
        pointsMenu.resolveCommand(String.format("%s %d %d %d %d", id1, 7, 6, 9, 7));
        pointsMenu.resolveCommand(String.format("%s %d %d %d %d", id1, 6, 5, 5, 0));
        pointsMenu.resolveCommand(String.format("%s %d %d %d %d", id2, 8, 0, 8, 6));
        pointsMenu.resolveCommand(String.format("%s %d %d %d %d", id2, 7, 0, 0, 0));
        pointsMenu.resolveCommand(String.format("%s %d %d %d %d", id2, 9, 0, 0, 5));
    }

    @Test
    void testGlobalStats() {
        assertEquals(splitAndSort("Java, Databases, Spring"), splitAndSort(Statistics.getMostPopular()));
        assertEquals(splitAndSort("DSA"), splitAndSort(Statistics.getLeastPopular()));
        assertEquals(splitAndSort("Java"), splitAndSort(Statistics.getHighestActivity()));
        assertEquals(splitAndSort("DSA"), splitAndSort(Statistics.getLowestActivity()));
        assertEquals(splitAndSort("Java"), splitAndSort(Statistics.getEasiestCourse()));
        assertEquals(splitAndSort("Spring"), splitAndSort(Statistics.getHardestCourse()));
    }

//    @Test
//    void testStatsByCourse() {
//        System.out.println(Statistics.getCourseInfo("java"));
//        System.out.println(Statistics.getCourseInfo("dsa"));
//        System.out.println(Statistics.getCourseInfo("databases"));
//        System.out.println(Statistics.getCourseInfo("spring"));
//        assertTrue(true);
//    }

    private String splitAndSort(String s) {
        String[] arr = s.split(",\\s+");
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            sb.append(", ").append(arr[i]);
        }
        return sb.toString();
    }
}