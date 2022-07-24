package carsharing;

import carsharing.util.AppProperties;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Resolving database name to create it
        String dbName = getDBNameFromArgs(args);
        AppProperties.setDbName(dbName);

        // Starting manager
        Scanner console = new Scanner(System.in);
        Manager.work(console);
    }

    // JDBC driver name and database URL
    private static final String jdbcDriver = "org.h2.Driver";

    static {
        // Setting H2 database driver
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Parsing args from main method to find dbName argument
    private static String getDBNameFromArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("-databaseFileName".equals(args[i]) && i + 1 < args.length) {
                return args[i + 1];
            }
        }

        return "database";
    }
}
