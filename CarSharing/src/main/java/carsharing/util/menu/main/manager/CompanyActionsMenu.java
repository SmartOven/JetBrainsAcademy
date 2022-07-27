package carsharing.util.menu.main.manager;

import carsharing.db.dao.CarDao;
import carsharing.db.tables.Car;
import carsharing.db.tables.Company;
import carsharing.util.menu.Menu;
import carsharing.util.MenuFactory;

import java.util.List;

public class CompanyActionsMenu implements Menu {
    private final List<String> options;
    private Company company;

    public CompanyActionsMenu() {
        options = List.of(
                "Back",
                "Car list",
                "Create a car"
        );
    }

    public void setCompanyName(Company company) {
        this.company = company;
    }

    @Override
    public void render() {
        System.out.println("'" + company.getName() + "' company");
        for (int i = 1; i < options.size(); i++) {
            System.out.println(i + ". " + options.get(i));
        }
        System.out.println("0. " + options.get(0));

    }

    @Override
    public Menu doAction(String id) {
        switch (id) {
            case "0":
                return MenuFactory.getManagerMenu();
            case "1":
                List<Car> cars = CarDao.getInstance().getCompanyCars(company);

                if (isCarListEmpty(cars)) {
                    System.out.println("The car list is empty!");
                    return this;
                }
                
                System.out.println("'" + company.getName() + "' cars");
                for (int i = 0; i < cars.size(); i++) {
                    System.out.println(i + 1 + ". " + cars.get(i).getName());
                }

                return this;
            case "2":
                CreateCarMenu menu = MenuFactory.getCreateCarMenu();
                menu.setCompany(company);
                return menu;
            default:
                System.out.println("This is not an option!");
                return this;
        }
    }

    private boolean isCarListEmpty(List<Car> cars) {
        return cars == null || cars.size() == 0;
    }
}
