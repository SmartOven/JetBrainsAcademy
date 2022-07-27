package carsharing.util.menu.main;

import carsharing.db.dao.CustomerDao;
import carsharing.db.tables.Customer;
import carsharing.util.menu.Menu;
import carsharing.util.MenuFactory;

public class CreateCustomerMenu implements Menu {
    private final String activeOption;

    public CreateCustomerMenu() {
        activeOption = "Enter the customer name:";
    }

    @Override
    public void render() {
        System.out.println(activeOption);
    }

    @Override
    public Menu doAction(String id) {
        Customer customer = new Customer(id);
        CustomerDao.getInstance().save(customer);
        System.out.println("The customer was added!");
        return MenuFactory.getMainMenu();
    }
}
