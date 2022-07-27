package carsharing.util.menu;

import carsharing.db.dao.CustomerDao;
import carsharing.db.tables.Customer;
import carsharing.util.menu.main.ChooseCustomerMenu;
import carsharing.util.MenuFactory;

import java.util.List;

public class MainMenu implements Menu {
    private final List<String> options;

    public MainMenu() {
        options = List.of(
                "Exit",
                "Log in as a manager",
                "Log in as a customer",
                "Create a customer"
        );
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
            case "2":
                List<Customer> customers = CustomerDao.getInstance().getAll();
                if (customers == null || customers.size() == 0) {
                    System.out.println("The customer list is empty!");
                    return this;
                }

                ChooseCustomerMenu menu = MenuFactory.getChoosingCustomerMenu();
                menu.setCustomers(customers);
                return menu;
            case "3":
                return MenuFactory.getCreateCustomerMenu();
            default:
                System.out.println("This is not an option!");
                return this;
        }
    }
}
