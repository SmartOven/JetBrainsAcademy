package carsharing.util.menu.main.customer.rentCar;

import carsharing.db.dao.CarDao;
import carsharing.db.dao.CustomerDao;
import carsharing.db.tables.Car;
import carsharing.db.tables.Company;
import carsharing.db.tables.Customer;
import carsharing.util.MenuFactory;
import carsharing.util.menu.Menu;
import carsharing.util.menu.main.customer.CustomerMenu;

import java.util.ArrayList;
import java.util.List;

public class ChooseCarMenu implements Menu {
    List<String> cars;
    Company company;
    Customer customer;

    public void setCompany(Company company) {
        this.company = company;

        // Getting car names
        cars = new ArrayList<>();
        CarDao.getInstance().getCompanyCars(company).forEach(
                car -> cars.add(car.getName())
        );
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void render() {
        if (cars == null) {
            System.out.println("Company is not set!");
            return;
        }

        // Printing them
        System.out.println("Choose a car:");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println(i + 1 + ". " + cars.get(i));
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
        String inputErrorMessage = parseOptionIndex(id, cars);
        if (inputErrorMessage != null) {
            System.out.print(inputErrorMessage);
            return this;
        }

        String carName = cars.get(Integer.parseInt(id) - 1);
        Car carInfo = CarDao.getInstance()
                .getByName(carName)
                .orElse(null);
        if (carInfo == null) {
            System.out.println("Car with name = " + carName + " doesn't exist!");
            return this;
        }


        int rentedCarID = carInfo.getId();
        CustomerDao.getInstance().update(customer, new String[]{
                String.valueOf(customer.getId()),
                customer.getName(),
                String.valueOf(rentedCarID)
        });
        customer.setRented_car_id(rentedCarID);
        System.out.println("You rented '" + carName + "'");

        CustomerMenu menu = MenuFactory.getCustomerMenu();
        menu.setCustomer(customer);
        return menu;
    }
}
