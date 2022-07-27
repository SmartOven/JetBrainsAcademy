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
    void render();
    Menu doAction(String id);

    default String parseOptionIndex(String id, List<String> options) {
        // Given input is not a number
        int index;
        try {
            index = Integer.parseInt(id) - 1;
        } catch (NumberFormatException e) {
            return "This is not a number!";
        }

        // Given input is not in range
        if (index < 0 || index >= options.size()) {
            return "This is not a number from the given range! " +
                    "Choose the number in range from 0 to " + options.size() + "\n";
        }
        return null;
    }
}
