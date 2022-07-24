package carsharing.util.menu;

import java.util.List;

/**
 * Usage:
 * <pre>{@code
 * // Class ConcreteMenu implements Menu
 * Menu menu = new ConcreteMenu();
 *
 * // Prints menu to the System.out
 * menu.render();
 *
 * String optionID = console.nextLine();
 *
 * // Replaces current menu with other Menu object such as OtherConcreteMenu for example
 * menu = menu.doAction(optionID);
 * }</pre>
 *
 */
public interface Menu {
    List<String> getOptionsList();
    void render();
    Menu doAction(String id);
}
