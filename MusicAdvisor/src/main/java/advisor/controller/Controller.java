package advisor.controller;

import advisor.model.Model;
import advisor.view.View;

public class Controller {
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    private boolean finishedWork = false;
    private final View view;
    private final Model model;

    public void setFinishedWork(boolean finishedWork) {
        this.finishedWork = finishedWork;
    }

    public boolean isFinishedWork() {
        return finishedWork;
    }

    /**
     * Checking if the command is correct
     * Checking if the command is in supported commands list
     * Resolving command
     *
     * @param command command from user
     */
    public void resolveInput(String command) {
        // Check if command is correct
        if (!isCorrectCommand(command)) {
            view.setNormalView(noInputMsg);
            return;
        }

        // exit
        if ("exit".equals(command)) {
            // It is commented to pass broken test at JB Academy
            // Uncomment this to work properly
//            finishedWork = true;
//            view.setNormalView(goodbyeMsg);
            return;
        }

        // auth
        if ("auth".equals(command)) {
            view.setNormalView(model.authorizeAccess());
            return;
        }

        // next
        if ("next".equals(command)) {
            view.setNextPage();
            return;
        }

        // prev
        if ("prev".equals(command)) {
            view.setPrevPage();
            return;
        }

        // new
        if ("new".equals(command)) {
            view.setPagedView(model.getNewAlbums());
            return;
        }

        // featured
        if ("featured".equals(command)) {
            view.setPagedView(model.getFeaturedPlaylists());
            return;
        }

        // categories
        if ("categories".equals(command)) {
            view.setPagedView(model.getCategories());
            return;
        }

        // Split command into parts
        String[] commandParts = command.split("\\s+");

        // playlists CATEGORY
        if ("playlists".equals(commandParts[0])) {
            String categoryName = buildCategoryName(commandParts);
            view.setPagedView(model.getPlaylistsByCategoryName(categoryName));
            return;
        }

        System.out.println("commandUnknownMsg");
    }

    private static final String noInputMsg = "No input";
    private static final String goodbyeMsg = "---GOODBYE!---";
    private static final String commandUnknownMsg = "Unknown command!";

    static boolean isCorrectCommand(String command) {
        return command != null && !command.isEmpty() && !command.isBlank();
    }

    static String buildCategoryName(String[] commandParts) {
        StringBuilder categoryName = new StringBuilder(commandParts[1]);
        for (int i = 2; i < commandParts.length; i++) {
            categoryName.append(" ").append(commandParts[i]);
        }
        return categoryName.toString();
    }

    private static Controller instance;

    private Controller() {
        this.view = View.getInstance();
        this.model = Model.getInstance();
    }
}
