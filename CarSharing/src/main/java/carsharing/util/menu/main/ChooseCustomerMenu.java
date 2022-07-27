package carsharing.util.menu.main;

import carsharing.db.dao.CustomerDao;
import carsharing.db.tables.Customer;
import carsharing.util.menu.Menu;
import carsharing.util.MenuFactory;
import carsharing.util.menu.main.customer.CustomerMenu;

import java.util.ArrayList;
import java.util.List;

public class ChooseCustomerMenu implements Menu {
    List<String> options;

    public void setCustomers(List<Customer> customers) {
        options = new ArrayList<>();
        customers.forEach(customer -> options.add(customer.getName()));
    }

    @Override
    public void render() {
        System.out.println("Choose a customer:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + 1 + ". " + options.get(i));
        }
        System.out.println("0. Back");
    }

    @Override
    public Menu doAction(String id) {
        if ("0".equals(id)) {
            return MenuFactory.getMainMenu();
        }

        // Check if input is correct
        String inputErrorMessage = parseOptionIndex(id, options);
        if (inputErrorMessage != null) {
            System.out.print(inputErrorMessage);
            return this;
        }

        // Go to concrete company menu
        int index = Integer.parseInt(id) - 1;
        CustomerMenu menu = MenuFactory.getCustomerMenu();
        String customerName = options.get(index);
        Customer customer = CustomerDao.getInstance().getByName(customerName).orElse(null);
        if (customer == null) {
            System.out.println("The customer with name = " + customerName + " doesn't exist!");
            return this;
        }
        menu.setCustomer(customer);
        return menu;
    }
}
