package carsharing.util.menu;

import carsharing.db.dao.CompanyDao;
import carsharing.db.tables.Company;
import carsharing.util.menu.factory.MenuFactory;

import java.util.List;

public class CompanyChoosingMenu implements Menu {
    private final List<String> options;

    public CompanyChoosingMenu() {
        options = CompanyDao.getInstance()
                .getAll()
                .stream()
                .map(Company::getName)
                .toList();

    }

    @Override
    public List<String> getOptionsList() {
        return options;
    }

    @Override
    public void render() {
        if (isCompanyListEmpty()) {
            System.out.println("The company list is empty!");
            return;
        }

        System.out.println("Choose the company:");
        for (int i = 0; i < options.size(); i++) {
            System.out.println(i + 1 + ". " + options.get(i));
        }
        System.out.println("0. Back");
    }

    public boolean isCompanyListEmpty() {
        return options == null || options.size() == 0;
    }

    @Override
    public Menu doAction(String id) {
        // No companies -> go to main menu
        if (isCompanyListEmpty()) {
            return MenuFactory.getMainMenu();
        }

        // Go back -> to main menu
        if ("0".equals(id)) {
            return MenuFactory.getManagerMenu();
        }

        // Given input is not a number
        int index;
        try {
            index = Integer.parseInt(id) - 1;
        } catch (NumberFormatException e) {
            System.out.println("This is not a number!");
            return this;
        }

        // Given input is not in range
        if (index < 0 || index >= options.size()) {
            System.out.println("This is not a number from the given range! " +
                    "Choose the number in range from 1 to " + (options.size() - 1));
            return this;
        }

        // Go to concrete company menu
        CompanyActionsMenu menu = MenuFactory.getCompanyActionsMenu();
        String companyName = options.get(index);
        menu.setCompanyName(companyName);
        return menu;
    }
}
