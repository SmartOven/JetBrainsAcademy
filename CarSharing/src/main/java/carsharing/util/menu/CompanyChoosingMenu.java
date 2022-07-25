package carsharing.util.menu;

import carsharing.db.tables.Company;
import carsharing.util.menu.factory.MenuFactory;

import java.util.ArrayList;
import java.util.List;

public class CompanyChoosingMenu implements Menu {
    private List<String> options;

    public void setCompanies(List<Company> companies) {
        options = new ArrayList<>();
        for (Company company : companies) {
            options.add(company.getName());
        }
    }

    @Override
    public List<String> getOptionsList() {
        return options;
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
        menu.setCompanyName(new Company(index + 1, companyName));
        return menu;
    }
}
