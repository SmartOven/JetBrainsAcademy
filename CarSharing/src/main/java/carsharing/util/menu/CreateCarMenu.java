package carsharing.util.menu;

import carsharing.db.dao.CarDao;
import carsharing.db.tables.Car;
import carsharing.db.tables.Company;
import carsharing.util.menu.factory.MenuFactory;

import java.util.List;

public class CreateCarMenu implements Menu {
    private final List<String> options;
    private final String activeOption;
    private Company company;

    public CreateCarMenu() {
        options = List.of(
                "Enter the car name:"
        );
        activeOption = options.get(0);
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public List<String> getOptionsList() {
        return options;
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
