package carsharing.util.menu.main.customer.rentCar;

import carsharing.db.dao.CarDao;
import carsharing.db.dao.CompanyDao;
import carsharing.db.tables.Car;
import carsharing.db.tables.Company;
import carsharing.db.tables.Customer;
import carsharing.util.MenuFactory;
import carsharing.util.menu.Menu;
import carsharing.util.menu.main.customer.CustomerMenu;

import java.util.ArrayList;
import java.util.List;

public class ChooseRentCarCompanyMenu implements Menu {
    List<String> options;
    Customer customer;

    public ChooseRentCarCompanyMenu() {
        options = new ArrayList<>();
        CompanyDao.getInstance().getAll().forEach(
                company -> options.add(company.getName())
        );
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void render() {
        System.out.println("Choose a company:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + 1 + ". " + options.get(i));
        }
        System.out.println("0. Back");
    }

    @Override
    public Menu doAction(String id) {
        if ("0".equals(id)) {
            CustomerMenu menu = MenuFactory.getCustomerMenu();
            menu.setCustomer(customer);
            return menu;
        }

        // Check if input is correct
        String inputErrorMessage = parseOptionIndex(id, options);
        if (inputErrorMessage != null) {
            System.out.print(inputErrorMessage);
            return this;
        }

        String companyName = options.get(Integer.parseInt(id) - 1);
        Company company = CompanyDao.getInstance()
                .getByName(companyName)
                .orElse(null);
        if (company == null) {
            System.out.println("There is no company with name: " + companyName);
            return this;
        }

        List<Car> companyCarsList = CarDao.getInstance()
                .getCompanyCars(company);
        if (companyCarsList.isEmpty()) {
            System.out.println("No available cars in the 'Company name' company");
            return this;
        }

        ChooseCarMenu menu = MenuFactory.getChooseCarMenu();
        menu.setCustomer(customer);
        menu.setCompany(company);
        return menu;
    }
}
