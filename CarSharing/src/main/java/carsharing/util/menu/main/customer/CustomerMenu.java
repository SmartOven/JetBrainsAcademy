package carsharing.util.menu.main.customer;

import carsharing.db.dao.CarDao;
import carsharing.db.dao.CompanyDao;
import carsharing.db.dao.CustomerDao;
import carsharing.db.tables.Car;
import carsharing.db.tables.Company;
import carsharing.db.tables.Customer;
import carsharing.util.menu.Menu;
import carsharing.util.MenuFactory;
import carsharing.util.menu.main.customer.rentCar.ChooseRentCarCompanyMenu;

import java.util.List;

public class CustomerMenu implements Menu {
    List<String> options;
    Customer customer;

    public CustomerMenu() {
        options = List.of(
                "Rent a car",
                "Return a rented car",
                "My rented car"
        );
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void render() {
        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + 1 + ". " + options.get(i));
        }
        System.out.println("0. Back");
    }

    @Override
    public Menu doAction(String id) {
        switch (id) {
            case "0":
                return MenuFactory.getMainMenu();
            case "1":
                if (isCarRented()) {
                    System.out.println("You've already rented a car!");
                    return this;
                }

                List<Company> companies = CompanyDao.getInstance().getAll();
                if (companies == null || companies.size() == 0) {
                    System.out.println("The company list is empty!");
                    return this;
                }

                ChooseRentCarCompanyMenu menu = MenuFactory.getChooseRentCarCompanyMenu();
                menu.setCustomer(customer);
                return menu;
            case "2":
                returnRentedCar();
                return this;
            case "3":
                printRentedCarInfo();
                return this;
            default:
                System.out.println("This is not an option!");
                return this;
        }
    }

    private void printRentedCarInfo() {
        if (!isCarRented()) {
            System.out.println("You didn't rent a car!");
            return;
        }

        // Getting car by id
        Car rentedCar = CarDao.getInstance()
                .get(customer.getRented_car_id())
                .orElse(null);
        if (rentedCar == null) {
            System.out.println("The rented car with ID = " + customer.getRented_car_id() + " doesn't exist");
            return;
        }

        // Getting car's company by the car's company_id field
        Company carsCompany = CompanyDao.getInstance()
                .get(rentedCar.getCompany_id())
                .orElse(null);
        if (carsCompany == null) {
            System.out.println("Company with ID = " + rentedCar.getCompany_id() + " doesn't exist");
            return;
        }

        // Printing information
        System.out.println("Your rented car:");
        System.out.println(rentedCar.getName());
        System.out.println("Company:");
        System.out.println(carsCompany.getName());
    }

    private void returnRentedCar() {
        if (!isCarRented()) {
            System.out.println("You didn't rent a car!");
            return;
        }

        CustomerDao.getInstance().update(customer, new String[]{
                String.valueOf(customer.getId()),
                customer.getName(),
                "null"
        });
        System.out.println("You've returned a rented car!");
        customer.setRented_car_id(-1);
    }

    private boolean isCarRented() {
        return customer.getRented_car_id() != -1;
    }
}
