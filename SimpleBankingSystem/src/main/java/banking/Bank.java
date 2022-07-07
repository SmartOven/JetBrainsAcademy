package banking;

import banking.account.BankAccount;
import banking.card.CardNumber;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteDataSource;

public class Bank {
    private final SQLiteDataSource dataSource;

    public Bank(String databaseUrl) {
        this.dataSource = new SQLiteDataSource();
        this.dataSource.setUrl(databaseUrl);
        createTableIfNotExists();
    }

    public void createAccount(String pinCode, BankAccount bankAccount) {
        insertIntoTable(pinCode, bankAccount);
    }

    public BankAccount getBankAccount(String pinCode, CardNumber cardNumber) {
        return getBankAccountFromTableIfExists(pinCode, cardNumber);
    }

    private void createTableIfNotExists() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            // Creating table
            // Name:            card
            // columns: id number pin balance
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS card(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "number TEXT UNIQUE NOT NULL," +
                            "pin TEXT NOT NULL," +
                            "balance INTEGER DEFAULT 0)"
            );
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    private void insertIntoTable(String pinCode, BankAccount bankAccount) {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                // Getting cardNumber as String and balance as int
                String number = bankAccount.getCardNumber().toString();
                int balance = bankAccount.getBalance();

                // Building "VALUES" String
                String values = "VALUES ('" + number + "', '" + pinCode + "'," + balance + " )";

                // Inserting new bank account into DB
                statement.executeUpdate(
                        "INSERT INTO card (number, pin, balance)" +
                                values
                );
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    private BankAccount getBankAccountFromTableIfExists(String pinCode, CardNumber cardNumber) {
        try (Connection con = dataSource.getConnection();
             Statement statement = con.createStatement();
             ResultSet bankAccountValues = statement.executeQuery(
                     "SELECT number, pin, balance " +
                             "FROM card " +
                             "WHERE number = '" + cardNumber.toString() + "'" +
                             "AND pin = '" + pinCode + "'"
             )) {
            // Getting bank account from table by its cardNumber and pinCode
            // If not exists -> return null
            if (!bankAccountValues.next()) {
                return null;
            }

            // If exists -> return BankAccount object with cardNumber and balance
            int balance = bankAccountValues.getInt("balance");
            return new BankAccount(cardNumber, balance);
        } catch (SQLException e) {
            return null;
        }
    }
}
