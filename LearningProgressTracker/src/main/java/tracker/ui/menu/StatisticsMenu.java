package tracker.ui.menu;

import tracker.util.Statistics;

import java.util.List;

public class StatisticsMenu extends Menu {

    private static StatisticsMenu instance;

    public static StatisticsMenu getInstance() {
        if (instance == null) {
            instance = new StatisticsMenu();
        }
        return instance;
    }

    private StatisticsMenu() {
        super();
    }

    @Override
    public void onCreate() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        System.out.println("Most popular: " + Statistics.getMostPopular());
        System.out.println("Least popular: " + Statistics.getLeastPopular());
        System.out.println("Highest activity: " + Statistics.getHighestActivity());
        System.out.println("Lowest activity: " + Statistics.getLowestActivity());
        System.out.println("Easiest course: " + Statistics.getEasiestCourse());
        System.out.println("Hardest course: " + Statistics.getHardestCourse());
    }

    @Override
    public void resolveCommand(String command) {
        // By default, we think that menu won't change
        setNextMenu(this);

        // Go back to main menu
        if ("back".equals(command)) {
            setNextMenu(MainMenu.getInstance());
            return;
        }

        List<String> info = Statistics.getCourseInfo(command);

        if (info == null) {
            System.out.println("Unknown course.");
            return;
        }

        info.forEach(System.out::println);
    }
}
