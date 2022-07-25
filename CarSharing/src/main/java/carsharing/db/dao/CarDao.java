package carsharing.db.dao;

import carsharing.db.tables.Car;
import carsharing.db.tables.Company;
import carsharing.util.AppProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDao implements Dao<Car> {
    private static CarDao instance;

    public static CarDao getInstance() {
        String dbUrl = AppProperties.getDbUrl();
        if (instance == null) {
            instance = new CarDao(dbUrl);
        }
        return instance;
    }

    private CarDao(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    private final String dbUrl;

    /**
     * Finds car with given id if it does exist
     *
     * @param id car id
     * @return car with ID = id
     */
    @Override
    public Optional<Car> get(int id) {
        return Optional.empty();
    }

    /**
     * @return all cars
     */
    @Override
    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();

        String insertQuery = "SELECT ID, NAME, COMPANY_ID " +
                "FROM CAR";

        // Getting result of SQL query
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Adding every found company
                while (resultSet.next()) {
                    cars.add(new Car(
                            resultSet.getInt("ID"),
                            resultSet.getString("NAME"),
                            resultSet.getInt("COMPANY_ID")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }

    /**
     * Inserts new car into the CAR table
     *
     * @param car car to be inserted into table
     */
    @Override
    public void save(Car car) {
        String insertQuery = "INSERT INTO CAR (NAME, COMPANY_ID)" +
                "VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, car.getName());
            preparedStatement.setInt(2, car.getCompany_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates params of existing car
     *
     * @param car    car to be updated
     * @param params new car params = [ID, NAME, COMPANY_ID]
     */
    @Override
    public void update(Car car, String[] params) {
        String insertQuery = "UPDATE CAR " +
                "SET NAME = ? AND COMPANY_ID = ?" +
                "WHERE ID = ?";

        String name = params[1];
        int company_id = Integer.parseInt(params[2]);

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, company_id);
            preparedStatement.setInt(3, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes car from the table
     *
     * @param car car to be deleted
     */
    @Override
    public void delete(Car car) {
        String insertQuery = "DELETE FROM CAR " +
                "WHERE ID = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(true);
            preparedStatement.setInt(1, car.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getting cars by company_id
     *
     * @param company company to search for
     * @return list of company's cars
     */
    public List<Car> getCompanyCars(Company company) {
        List<Car> cars = new ArrayList<>();

        String insertQuery = "SELECT ID, NAME, COMPANY_ID " +
                "FROM CAR " +
                "WHERE COMPANY_ID = ?";

        // Getting result of SQL query
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setInt(1, company.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Adding every found company
                while (resultSet.next()) {
                    cars.add(new Car(
                            resultSet.getInt("ID"),
                            resultSet.getString("NAME"),
                            resultSet.getInt("COMPANY_ID")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars;
    }
}
