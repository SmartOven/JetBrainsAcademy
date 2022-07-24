package carsharing.util.menu;

import carsharing.db.dao.CompanyDao;
import carsharing.db.tables.Company;
import carsharing.util.menu.factory.MenuFactory;

import java.util.List;

public class CreateCompanyMenu implements Menu {
    private final List<String> options;
    private final String activeOption;

    public CreateCompanyMenu() {
        options = List.of(
                "Enter the company name:"
        );
        activeOption = options.get(0);
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
        Company company = new Company(id);
        CompanyDao.getInstance().save(company);
        System.out.println("The company was created!");
        return MenuFactory.getManagerMenu();
    }
}
