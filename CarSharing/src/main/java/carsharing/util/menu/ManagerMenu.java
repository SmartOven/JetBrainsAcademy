package carsharing.util.menu;

import carsharing.db.dao.CompanyDao;
import carsharing.db.tables.Company;
import carsharing.util.menu.factory.MenuFactory;

import java.util.List;

public class ManagerMenu implements Menu {
    private final List<String> options;

    public ManagerMenu() {
        options = List.of(
                "Back",
                "Company list",
                "Create a company"
        );
    }

    @Override
    public List<String> getOptionsList() {
        return options;
    }

    @Override
    public void render() {
        for (int i = 1; i < options.size(); i++) {
            System.out.println(i + ". " + options.get(i));
        }
        System.out.println("0. " + options.get(0));
    }

    @Override
    public Menu doAction(String id) {
        switch (id) {
            case "0":
                return MenuFactory.getMainMenu();
            case "1":
                List<Company> companies = CompanyDao.getInstance().getAll();
                if (companies == null || companies.size() == 0) {
                    System.out.println("The company list is empty!");
                    return this;
                }

                CompanyChoosingMenu menu = MenuFactory.getCompanyChoosingMenu();
                menu.setCompanies(companies);
                return menu;
            case "2":
                return MenuFactory.getCreateCompanyMenu();
            default:
                System.out.println("Wrong option!");
                return this;
        }
    }
}
