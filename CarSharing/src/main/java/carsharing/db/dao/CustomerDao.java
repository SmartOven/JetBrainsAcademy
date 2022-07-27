package carsharing.db.dao;

import carsharing.db.tables.Customer;
import carsharing.util.AppProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDao implements Dao<Customer> {

    private static CustomerDao instance;

    public static CustomerDao getInstance() {
        String dbUrl = AppProperties.getDbUrl();
        if (instance == null) {
            instance = new CustomerDao(dbUrl);
        }
        return instance;
    }

    private CustomerDao(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    private final String dbUrl;

    /**
     * Finds customer with given id if it does exist
     *
     * @param id company id
     * @return company with ID = id
     */
    @Override
    public Optional<Customer> get(int id) {
        String insertQuery = "SELECT ID, NAME, RENTED_CAR_ID " +
                "FROM CUSTOMER " +
                "WHERE ID = ?";

        // Getting result of SQL query
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Parsing result
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(new Customer(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getInt("RENTED_CAR_ID")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Customer> getByName(String name) {
        String insertQuery = "SELECT ID, NAME, RENTED_CAR_ID " +
                "FROM CUSTOMER " +
                "WHERE NAME = ?";

        // Getting result of SQL query
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                // Parsing result
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                int rentCarId = resultSet.getInt("RENTED_CAR_ID");
                if (rentCarId == 0) {
                    return Optional.of(new Customer(
                            resultSet.getInt("ID"),
                            resultSet.getString("NAME")
                            ));
                }
                return Optional.of(new Customer(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        rentCarId
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * @return all customers
     */
    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();

        String insertQuery = "SELECT ID, NAME, RENTED_CAR_ID " +
                "FROM CUSTOMER";

        // Getting result of SQL query
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Adding every found company
                while (resultSet.next()) {
                    customers.add(new Customer(
                            resultSet.getInt("ID"),
                            resultSet.getString("NAME"),
                            resultSet.getInt("RENTED_CAR_ID")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    /**
     * Inserts new customer into the CUSTOMER table
     *
     * @param customer customer to be inserted into table
     */
    @Override
    public void save(Customer customer) {
        String insertQuery;
        if (customer.getRented_car_id() != -1) {
            insertQuery = "INSERT INTO CUSTOMER (ID, NAME, RENTED_CAR_ID) " +
                    "VALUES (DEFAULT, ?, ?)";
        } else {
            insertQuery = "INSERT INTO CUSTOMER (ID, NAME, RENTED_CAR_ID) " +
                    "VALUES (DEFAULT, ?, NULL)";
        }

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, customer.getName());
            if (customer.getRented_car_id() != -1) {
                preparedStatement.setInt(2, customer.getRented_car_id());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates params of existing customer
     *
     * @param customer customer to be updated
     * @param params   new customer params = [ID, NAME, RENTED_CAR_ID]
     */
    @Override
    public void update(Customer customer, String[] params) {
        String insertQuery;

        String name = params[1];
        boolean rentedCarIDIsNull = "null".equals(params[2]);
        if (rentedCarIDIsNull) {
            insertQuery = "UPDATE CUSTOMER " +
                    "SET NAME = ?, RENTED_CAR_ID = NULL " +
                    "WHERE ID = ?";
        } else {
            insertQuery = "UPDATE CUSTOMER " +
                    "SET NAME = ?, RENTED_CAR_ID = ? " +
                    "WHERE ID = ?";
        }

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, name);
            if (rentedCarIDIsNull) {
                preparedStatement.setInt(2, customer.getId());
            } else {
                preparedStatement.setInt(2, Integer.parseInt(params[2]));
                preparedStatement.setInt(3, customer.getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes customer from the table
     *
     * @param customer company to be deleted
     */
    @Override
    public void delete(Customer customer) {
        String insertQuery = "DELETE FROM CUSTOMER " +
                "WHERE ID = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(true);
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
