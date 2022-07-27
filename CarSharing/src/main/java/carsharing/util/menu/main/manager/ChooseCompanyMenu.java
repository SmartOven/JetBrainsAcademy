package carsharing.util.menu.main.manager;

import carsharing.db.tables.Company;
import carsharing.util.menu.Menu;
import carsharing.util.MenuFactory;

import java.util.ArrayList;
import java.util.List;

public class ChooseCompanyMenu implements Menu {
    private List<String> options;

    public void setCompanies(List<Company> companies) {
        options = new ArrayList<>();
        companies.forEach(company -> options.add(company.getName()));
    }

    @Override
    public void render() {
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
            return MenuFactory.getManagerMenu();
        }

        // Go back -> to main menu
        if ("0".equals(id)) {
            return MenuFactory.getManagerMenu();
        }

        // Check if input is correct
        String inputErrorMessage = parseOptionIndex(id, options);
        if (inputErrorMessage != null) {
            System.out.print(inputErrorMessage);
            return this;
        }

        // Go to concrete company menu
        int index = Integer.parseInt(id) - 1;
        CompanyActionsMenu menu = MenuFactory.getCompanyActionsMenu();
        String companyName = options.get(index);
        menu.setCompanyName(new Company(index + 1, companyName));
        return menu;
    }
}
