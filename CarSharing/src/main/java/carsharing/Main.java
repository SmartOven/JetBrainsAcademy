package carsharing;

import carsharing.db.DBManager;

public class Main {
    public static void main(String[] args) {
        final String dbName = resolveDBName(args);

        DBManager manager = new DBManager(dbName);
        manager.createCompanyTableIfNotExists();
    }

    private static String resolveDBName(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("-databaseFileName".equals(args[i]) && i + 1 < args.length) {
                return args[i + 1];
            }
        }

        return "database";
    }
}
