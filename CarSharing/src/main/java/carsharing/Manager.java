package carsharing;

import carsharing.db.MigrationManager;
import carsharing.db.dao.CompanyDao;
import carsharing.db.tables.Company;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class Manager {
    private static final Map<State, String[]> stateOptions;
    private static final Map<String, Function<String, String>> optionActions;
    private static State state;
    private static Scanner scanner;

    static {
        stateOptions = new HashMap<>();
        stateOptions.put(State.MAIN_MENU, new String[]{"Exit", "Log in as a manager"});
        stateOptions.put(State.LOGGED_MANAGER, new String[]{"Back", "Company list", "Create a company"});

        optionActions = new HashMap<>();
        optionActions.put(
                "Exit",
                input -> {
                    state = State.EXIT;
                    return null;
                }
        );
        optionActions.put(
                "Log in as a manager",
                input -> {
                    state = State.LOGGED_MANAGER;
                    return null;
                }
        );
        optionActions.put(
                "Back",
                input -> {
                    state = State.MAIN_MENU;
                    return null;
                }
        );
        optionActions.put(
                "Company list",
                input -> {
                    CompanyDao companyDao = CompanyDao.getInstance();
                    List<Company> companies = companyDao.getAll();
                    if (companies.size() == 0) {
                        System.out.println("The company list is empty!");
                        return null;
                    }
                    StringBuilder result = new StringBuilder();
                    for (int i = 0; i < companies.size(); i++) {
                        String companyName = companies.get(i).getName();
                        result.append(i + 1).append(". ").append(companyName).append("\n");
                    }
                    System.out.println(result);
                    return null;
                }
        );
        optionActions.put(
                "Create a company",
                input -> {
                    System.out.println("Enter the company name:");
                    String companyName = scanner.nextLine();

                    Company company = new Company(companyName);
                    CompanyDao companyDao = CompanyDao.getInstance();
                    companyDao.save(company);

                    System.out.println("The company was created!");
                    return null;
                }
        );
    }

    public static void work(Scanner console) {
        scanner = console;
        // Making necessary migrations to update database to the latest version
        MigrationManager migrationManager = MigrationManager.getInstance();
        migrationManager.migrate();

        state = State.MAIN_MENU;

        do {
            printUserOptions();
            String userInput = scanner.nextLine();
            String option = stateOptions.get(state)[Integer.parseInt(userInput)];
            optionActions.get(option).apply(null);

        } while (!state.equals(State.EXIT));
    }

    private static void printUserOptions() {
        String[] options = stateOptions.get(state);
        for (int i = 1; i < options.length; i++) {
            System.out.println(i + ". " + options[i]);
        }
        System.out.println("0. " + options[0]);
    }

    private enum State {
        MAIN_MENU,
        LOGGED_MANAGER,
        EXIT
    }
}
