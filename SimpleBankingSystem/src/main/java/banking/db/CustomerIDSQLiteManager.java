package banking.db;

import java.sql.*;

public class CustomerIDSQLiteManager extends SQLiteManager {
    public CustomerIDSQLiteManager() {
    }

    /**
     * Creates table for available customer ID
     * Insert first available customer ID into table
     * Using transaction for the procedure
     */
    public void createAvailableCustomerIDTableIfNotExists() {
        try (Connection connection = dataSource.getConnection()) {
            // Return if table exists
            try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT name FROM sqlite_master WHERE type='table' AND name= 'customer_id'"
                )) {
                if (resultSet.next()) {
                    return;
                }
            }

            // Disable auto-commit mode and make savepoint at start
            connection.setAutoCommit(false);
            Savepoint savepointStart = connection.setSavepoint();

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(
                        "CREATE TABLE IF NOT EXISTS customer_id(" +
                                "id INTEGER)"
                );
                statement.executeUpdate(
                        "INSERT INTO customer_id VALUES (0)"
                );
            } catch (SQLException e) {
                connection.rollback(savepointStart);
                System.err.println("Transaction (AvailableCustomerID) was interrupted!");
            }

            connection.commit();
        } catch (SQLException e) {
            System.err.println("DB connection lost!");
        }
    }

    /**
     * Select nextAvailableID, increment it and update value in the table
     *
     * @return nextAvailableID
     */
    public int incrementAndGetNextAvailableCustomerID() {
        int nextAvailableID = 0;
        try (Connection connection = dataSource.getConnection()) {
            // Select nextAvailableCustomerID
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(
                         "SELECT id FROM customer_id"
                 )) {
                resultSet.next();
                nextAvailableID = resultSet.getInt("id");
            } catch (SQLException e) {
                System.err.println("Data not found (AvailableCustomerID)");
            }

            // Update nextAvailableCustomerID (increment and write)
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(
                        "UPDATE customer_id " +
                                "SET id = " + (nextAvailableID + 1) + " " +
                                "WHERE id = " + nextAvailableID
                );
            } catch (SQLException e) {
                System.err.println("Data not updated (AvailableCustomerID)");
            }
        } catch (SQLException e) {
            System.err.println("DB connection lost!");
        }

        return nextAvailableID;
    }
}
