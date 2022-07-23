package carsharing.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Using H2 database
public class DBManager {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL;

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DBManager(String dbName) {
//        String path = "./src/carsharing/db/";
        String path = "./CarSharing/src/main/resources/";
        DB_URL = "jdbc:h2:" + path + dbName;
    }

    public void createCompanyTableIfNotExists() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS COMPANY(ID INTEGER, NAME VARCHAR);";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Use carefully
    public void dropCompanyTableIfExists() {
        String sqlQuery = "DROP TABLE IF EXISTS COMPANY;";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
