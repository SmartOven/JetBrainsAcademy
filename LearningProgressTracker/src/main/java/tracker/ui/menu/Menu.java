package tracker.ui.menu;

/**
 * Menu class to work with user through the console
 * Every concrete menu has to be singleton
 * If menu has state, the state need to be passed in constructor arguments
 */
public abstract class Menu {

    protected static Menu nextMenu;

    // Printing necessary info each time user input something
    // #overrideme
    public void render() {}

    // Use this to print necessary info when getting into menu first time
    public void renderOnCreate() {}

    // Resolving user command
    // #overrideme
    public abstract void resolveCommand(String command);

    // Getting into the next menu (if needed)
    public Menu getNextMenu() {
        return nextMenu;
    }
}
