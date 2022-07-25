package carsharing.util.menu.factory;

import carsharing.util.menu.*;

import java.util.HashMap;
import java.util.Map;

public class MenuFactory {
    private static final Map<String, Menu> menus;

    static {
        menus = new HashMap<>();
    }

    public static MainMenu getMainMenu() {
        MainMenu mainMenu = (MainMenu) menus.getOrDefault("mainMenu", new MainMenu());
        menus.putIfAbsent("mainMenu", mainMenu);
        return mainMenu;
    }

    public static ManagerMenu getManagerMenu() {
        ManagerMenu managerMenu = (ManagerMenu) menus.getOrDefault("managerMenu", new ManagerMenu());
        menus.putIfAbsent("managerMenu", managerMenu);
        return managerMenu;
    }

    public static CreateCompanyMenu getCreateCompanyMenu() {
        CreateCompanyMenu createCompanyMenu = (CreateCompanyMenu) menus.getOrDefault("createCompanyMenu", new CreateCompanyMenu());
        menus.putIfAbsent("createCompanyMenu", createCompanyMenu);
        return createCompanyMenu;
    }

    public static CompanyChoosingMenu getCompanyChoosingMenu() {
        CompanyChoosingMenu companyChoosingMenu = (CompanyChoosingMenu) menus.getOrDefault("companyChoosingMenu", new CompanyChoosingMenu());
        menus.putIfAbsent("companyChoosingMenu", companyChoosingMenu);
        return companyChoosingMenu;
    }

    public static CompanyActionsMenu getCompanyActionsMenu() {
        CompanyActionsMenu companyActionsMenu = (CompanyActionsMenu) menus.getOrDefault("companyActionsMenu", new CompanyActionsMenu());
        menus.putIfAbsent("companyActionsMenu", companyActionsMenu);
        return companyActionsMenu;
    }

    public static CreateCarMenu getCreateCarMenu() {
        CreateCarMenu createCarMenu = (CreateCarMenu) menus.getOrDefault("createCarMenu", new CreateCarMenu());
        menus.putIfAbsent("createCarMenu", createCarMenu);
        return createCarMenu;
    }
}
