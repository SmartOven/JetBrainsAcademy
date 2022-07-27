package carsharing.util.menu.main.manager;

import carsharing.db.dao.CompanyDao;
import carsharing.db.tables.Company;
import carsharing.util.menu.Menu;
import carsharing.util.MenuFactory;

public class CreateCompanyMenu implements Menu {
    private final String activeOption;

    public CreateCompanyMenu() {
        activeOption = "Enter the company name:";
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
