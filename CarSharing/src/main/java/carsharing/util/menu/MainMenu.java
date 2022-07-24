package carsharing.util.menu;

import carsharing.util.menu.factory.MenuFactory;

import java.util.List;

public class MainMenu implements Menu {
    private final List<String> options;

    public MainMenu() {
        options = List.of(
                "Exit",
                "Log in as a manager"
        );
    }

    @Override
    public List<String> getOptionsList() {
        return options;
    }

    @Override
    public void render() {
        for (int i = 1; i < options.size(); i++) {
            System.out.println(i + ". " + options.get(i));
        }
        System.out.println("0. " + options.get(0));
    }

    @Override
    public Menu doAction(String id) {
        switch (id) {
            case "0":
                return null;
            case "1":
                return MenuFactory.getManagerMenu();
            default:
                System.out.println("Wrong option!");
                return this;
        }
    }
}
