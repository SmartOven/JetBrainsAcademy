package carsharing.db.dao;

import carsharing.db.tables.Company;
import carsharing.util.AppProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Singleton
 * Working with COMPANY table
 */
public class CompanyDao implements Dao<Company> {
    private static CompanyDao instance;

    public static CompanyDao getInstance() {
        String dbUrl = AppProperties.getDbUrl();
        if (instance == null) {
            instance = new CompanyDao(dbUrl);
        }
        return instance;
    }

    private CompanyDao(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    private final String dbUrl;

    /**
     * Finds company with given id if it does exist
     *
     * @param id company id
     * @return company with ID = id
     */
    @Override
    public Optional<Company> get(int id) {
        String insertQuery = "SELECT ID, NAME " +
                "FROM COMPANY " +
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

                return Optional.of(new Company(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Company> getByName(String name) {
        String insertQuery = "SELECT ID, NAME " +
                "FROM COMPANY " +
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

                return Optional.of(new Company(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * @return all companies
     */
    @Override
    public List<Company> getAll() {
        List<Company> companies = new ArrayList<>();

        String insertQuery = "SELECT ID, NAME " +
                "FROM COMPANY";

        // Getting result of SQL query
        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // Adding every found company
                while (resultSet.next()) {
                    companies.add(new Company(
                            resultSet.getInt("ID"),
                            resultSet.getString("NAME")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return companies;
    }

    /**
     * Inserts new company into the COMPANY table
     *
     * @param company company to be inserted into table
     */
    @Override
    public void save(Company company) {
        String insertQuery = "INSERT INTO COMPANY (NAME)" +
                "VALUES (?)";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, company.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates params of existing company
     *
     * @param company company to be updated
     * @param params  new company params = [ID, NAME]
     */
    @Override
    public void update(Company company, String[] params) {
        String insertQuery = "UPDATE COMPANY " +
                "SET NAME = ? " +
                "WHERE ID = ?";
        String name = params[1];

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, company.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes company from the table
     *
     * @param company company to be deleted
     */
    @Override
    public void delete(Company company) {
        String insertQuery = "DELETE FROM COMPANY " +
                "WHERE ID = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            connection.setAutoCommit(true);
            preparedStatement.setInt(1, company.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
