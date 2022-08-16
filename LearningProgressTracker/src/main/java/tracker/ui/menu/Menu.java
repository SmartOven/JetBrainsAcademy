package tracker.ui.menu;

/**
 * Menu class to work with user through the console
 * Every concrete menu has to be singleton
 * If menu has state, the state need to be passed in constructor arguments
 */
public abstract class Menu {

    protected Menu nextMenu;

    public Menu() {
        nextMenu = this;
        onCreate();
    }

    // Printing necessary info each time user input something
    // #overrideme
    public void render() {
    }

    // Use this to print necessary info when getting into menu first time
    // or to initialize menu variables
    public void onCreate() {
    }

    // Resolving user command
    // #overrideme
    public abstract void resolveCommand(String command);

    // Getting into the next menu (if needed)
    public Menu getNextMenu() {
        return nextMenu;
    }

    public void setNextMenu(Menu nextMenu) {
        this.nextMenu = nextMenu;
    }
}
