package banking.customerIDGenerator;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerIDGenerator {
    /**
     * Single Thread Singleton implementation
     */
    private static CustomerIDGenerator instance = null;
    public static CustomerIDGenerator getInstance() {
        if (instance == null) {
            String databaseUrl = "jdbc:sqlite:" + "D:\\DiskApps\\Programming\\GitRepos\\JetBrainsAcademy\\SimpleBankingSystem\\src\\main\\resources\\bank_accounts.db";
            instance = new CustomerIDGenerator(databaseUrl);
        }
        return instance;
    }

    private long nextAvailableID;
    private final SQLiteDataSource dataSource;
    private CustomerIDGenerator(String databaseUrl) {
        nextAvailableID = 0;
        dataSource = new SQLiteDataSource();
        dataSource.setUrl(databaseUrl);
        dataSource.setDatabaseName("CustomerID");
        createTableIfNotExists();
    }

    public long getNextAvailableID() {
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet customerID = statement.executeQuery(
                     "SELECT id FROM customer_id"
             )) {
            customerID.next();
            nextAvailableID = customerID.getInt("id");
        } catch (SQLException e) {
            System.out.println("Data not selected (customer_id)");
        }

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("UPDATE customer_id SET id = " + (nextAvailableID + 1));
        } catch (SQLException e) {
            System.out.println("Data not updated (customer_id)");
        }
        return nextAvailableID;
    }

    private void createTableIfNotExists() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS customer_id(id INTEGER)");
        } catch (SQLException e) {
            System.out.println("Table not created (customer_id)");
        }

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("INSERT INTO customer_id VALUES (0)");
        } catch (SQLException e) {
            System.out.println("Data not inserted (customer_id)");
        }
    }
}
