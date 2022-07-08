package banking.db;

import banking.bankAccount.BankAccount;
import banking.bankAccount.BankAccountUtil;

import java.sql.*;

public class BankAccountsSQLiteManager extends SQLiteManager {
    public BankAccountsSQLiteManager() {
    }

    /**
     * Creates table for bank accounts
     */
    public void createBankAccountsTableIfNotExists() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS card(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "number TEXT UNIQUE NOT NULL," +
                            "pin TEXT NOT NULL," +
                            "balance INTEGER DEFAULT 0)"
            );
        } catch (SQLException e) {
            System.err.println("BankAccounts table wasn't created!");
        }
    }

    /**
     * Inserting new bank account into table
     *
     * @param bankAccount BankAccount to insert
     */
    public void insertBankAccountIntoTable(BankAccount bankAccount) {
        try (Connection connection = dataSource.getConnection()) {
            String insertQuery =
                    "INSERT INTO card (number, pin, balance)" +
                            " VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                // Building insert query
                preparedStatement.setString(1, bankAccount.getCardNumber());
                preparedStatement.setString(2, bankAccount.getPinCode());
                preparedStatement.setInt(3, bankAccount.getBalance());

                // Inserting new bank account into table
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("BankAccount not inserted!");
        }
    }

    /**
     * Removing bank account from table
     *
     * @param bankAccount BankAccount to remove
     */
    public void removeBankAccountFromTable(BankAccount bankAccount) {
        String deleteQuery =
                "DELETE FROM card " +
                        "WHERE number = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, bankAccount.getCardNumber());

            // Removing bank account from table
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Account wasn't deleted (card)");
        }
    }

    /**
     * Updating bank account balance (with opening connection)
     *
     * @param bankAccount BankAccount to update balance
     * @param balance     new balance
     */
    private void setBalance(BankAccount bankAccount, int balance) {
        try (Connection connection = dataSource.getConnection()) {
            boolean skipResult = setBalance(connection, bankAccount, balance);
        } catch (SQLException e) {
            System.err.println("DB connection lost!");
        }
    }

    /**
     * Updating bank account balance (with opened connection)
     *
     * @param connection  pre-opened connection
     * @param bankAccount BankAccount to update balance
     * @param balance     new balance
     */
    private boolean setBalance(Connection connection, BankAccount bankAccount, int balance) {
        String updateQuery =
                "UPDATE card " +
                        "SET balance = ? " +
                        "WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, balance);
            preparedStatement.setString(2, bankAccount.getCardNumber());

            // Updating bank account balance
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Balance wasn't updated!");
            return false;
        }
        return true;
    }

    /**
     * Adding money to the account
     *
     * @param bankAccount BankAccount to get income
     * @param amount      amount of money to add
     */
    public void addIncome(BankAccount bankAccount, int amount) {
        setBalance(bankAccount, bankAccount.getBalance() + amount);
    }

    /**
     * Transferring money from one bank account cardNumber another
     *
     * @param bankAccount src bank account
     * @param cardNumber  cardNumber of destination bank account
     * @param amount      money amount cardNumber be transferred
     * @return transfer status
     */
    public MoneyTransferStatus transferMoney(BankAccount bankAccount, String cardNumber, int amount) {
        // If not enough money
        if (bankAccount.getBalance() < amount) {
            return MoneyTransferStatus.NOT_ENOUGH_MONEY;
        }

        // If bankAccount card number equals cardNumber
        if (bankAccount.getCardNumber().equals(cardNumber)) {
            return MoneyTransferStatus.SAME_ACCOUNT_TRANSFER;
        }

        // If bankAccount card number or cardNumber are not valid
        if (!BankAccountUtil.isValidCardNumber(bankAccount.getCardNumber()) ||
                !BankAccountUtil.isValidCardNumber(cardNumber)) {
            return MoneyTransferStatus.NOT_VALID_CARD_NUMBER;
        }

        // If bankAccount card number or cardNumber doesn't exist in database table
        try (Connection connection = dataSource.getConnection()) {
            if (!bankAccountExists(connection, bankAccount.getCardNumber()) ||
                    !bankAccountExists(connection, cardNumber)) {
                return MoneyTransferStatus.CARD_NUMBER_DOESNT_EXIST;
            }
        } catch (SQLException e) {
            System.err.println("DB connection lost!");
            return MoneyTransferStatus.SQL_ERROR;
        }

        BankAccount destinationAccount = getBankAccount(cardNumber);

        // Making money transfer as transaction
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            // Subtracting amount from src balance
            if (!setBalance(connection, bankAccount, bankAccount.getBalance() - amount)) {
                connection.rollback();
            }

            // Adding amount money to destinationAccount balance
            assert destinationAccount != null; // will never be null actually
            if (!setBalance(connection, destinationAccount, destinationAccount.getBalance() + amount)) {
                connection.rollback();
            }

            // Saving changes
            connection.commit();
        } catch (SQLException e) {
            System.err.println("DB connection lost!");
            return MoneyTransferStatus.SQL_ERROR;
        }

        return MoneyTransferStatus.SUCCESS_TRANSFER;
    }

    /**
     * @param connection DB connection
     * @param cardNumber cardNumber to be checked
     * @return true if bank account with cardNumber exists; else false
     */
    private boolean bankAccountExists(Connection connection, String cardNumber) {
        String selectQuery =
                "SELECT COUNT(*) AS 'cardNumberExists' " +
                        "FROM card " +
                        "WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, cardNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt("cardNumberExists") == 1;
            }
        } catch (SQLException e) {
            System.err.println("DB connection lost!");
            return false;
        }
    }

    public enum MoneyTransferStatus {
        SUCCESS_TRANSFER,
        NOT_ENOUGH_MONEY,
        SAME_ACCOUNT_TRANSFER,
        NOT_VALID_CARD_NUMBER,
        CARD_NUMBER_DOESNT_EXIST,
        SQL_ERROR
    }

    public BankAccount getBankAccount(String cardNumber, String pinCode) {
        BankAccount bankAccount = new BankAccount(cardNumber, pinCode);

        try (Connection connection = dataSource.getConnection()) {
            String selectQuery =
                    "SELECT balance " +
                            "FROM card " +
                            "WHERE number = ?" +
                            "AND pin = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, bankAccount.getCardNumber());
                preparedStatement.setString(2, bankAccount.getPinCode());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Bank account doesn't exist
                    if (!resultSet.next()) {
                        return null;
                    }

                    bankAccount.setBalance(resultSet.getInt("balance"));
                    return bankAccount;
                }
            }
        } catch (SQLException e) {
            System.err.println("DB connection lost!");
            return null;
        }
    }

    private BankAccount getBankAccount(String cardNumber) {
        BankAccount bankAccount;

        try (Connection connection = dataSource.getConnection()) {
            String selectQuery =
                    "SELECT pin, balance " +
                            "FROM card " +
                            "WHERE number = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, cardNumber);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Bank account doesn't exist
                    if (!resultSet.next()) {
                        return null;
                    }

                    bankAccount = new BankAccount(cardNumber, resultSet.getString("pin"));
                    bankAccount.setBalance(resultSet.getInt("balance"));
                    return bankAccount;
                }
            }
        } catch (SQLException e) {
            System.err.println("DB connection lost!");
            return null;
        }
    }
}
