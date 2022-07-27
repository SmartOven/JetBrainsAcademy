package carsharing.util;

import carsharing.util.menu.MainMenu;
import carsharing.util.menu.Menu;
import carsharing.util.menu.main.CreateCustomerMenu;
import carsharing.util.menu.main.customer.CustomerMenu;
import carsharing.util.menu.main.ManagerMenu;
import carsharing.util.menu.main.ChooseCustomerMenu;
import carsharing.util.menu.main.customer.rentCar.ChooseCarMenu;
import carsharing.util.menu.main.customer.rentCar.ChooseRentCarCompanyMenu;
import carsharing.util.menu.main.manager.ChooseCompanyMenu;
import carsharing.util.menu.main.manager.CompanyActionsMenu;
import carsharing.util.menu.main.manager.CreateCarMenu;
import carsharing.util.menu.main.manager.CreateCompanyMenu;

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

    public static ChooseCompanyMenu getCompanyChoosingMenu() {
        ChooseCompanyMenu chooseCompanyMenu = (ChooseCompanyMenu) menus.getOrDefault("companyChoosingMenu", new ChooseCompanyMenu());
        menus.putIfAbsent("companyChoosingMenu", chooseCompanyMenu);
        return chooseCompanyMenu;
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

    public static CustomerMenu getCustomerMenu() {
        CustomerMenu customerMenu = (CustomerMenu) menus.getOrDefault("customerMenu", new CustomerMenu());
        menus.putIfAbsent("customerMenu", customerMenu);
        return customerMenu;
    }

    public static CreateCustomerMenu getCreateCustomerMenu() {
        CreateCustomerMenu createCustomerMenu = (CreateCustomerMenu) menus.getOrDefault("createCustomerMenu", new CreateCustomerMenu());
        menus.putIfAbsent("createCustomerMenu", createCustomerMenu);
        return createCustomerMenu;
    }

    public static ChooseCustomerMenu getChoosingCustomerMenu() {
        ChooseCustomerMenu chooseCustomerMenu = (ChooseCustomerMenu) menus.getOrDefault("choosingCustomerMenu", new ChooseCustomerMenu());
        menus.putIfAbsent("choosingCustomerMenu", chooseCustomerMenu);
        return chooseCustomerMenu;
    }

    public static ChooseRentCarCompanyMenu getChooseRentCarCompanyMenu() {
        ChooseRentCarCompanyMenu chooseRentCarCompanyMenu = (ChooseRentCarCompanyMenu) menus.getOrDefault("chooseRentCarCompanyMenu", new ChooseRentCarCompanyMenu());
        menus.putIfAbsent("chooseRentCarCompanyMenu", chooseRentCarCompanyMenu);
        return chooseRentCarCompanyMenu;
    }

    public static ChooseCarMenu getChooseCarMenu() {
        ChooseCarMenu chooseCarMenu = (ChooseCarMenu) menus.getOrDefault("chooseCarMenu", new ChooseCarMenu());
        menus.putIfAbsent("chooseCarMenu", chooseCarMenu);
        return chooseCarMenu;
    }
}
