package carsharing.util.menu.main.manager;

import carsharing.db.dao.CarDao;
import carsharing.db.tables.Car;
import carsharing.db.tables.Company;
import carsharing.util.menu.Menu;
import carsharing.util.MenuFactory;

public class CreateCarMenu implements Menu {
    private final String activeOption;
    private Company company;

    public CreateCarMenu() {
        activeOption = "Enter the car name:";
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public void render() {
        System.out.println(activeOption);
    }

    @Override
    public Menu doAction(String id) {
        CarDao.getInstance().save(new Car(id, company.getId()));
        System.out.println("The car was added!");
        return MenuFactory.getCompanyActionsMenu();
    }
}
