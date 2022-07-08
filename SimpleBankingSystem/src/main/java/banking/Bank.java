package banking;

import banking.bankAccount.BankAccount;
import banking.bankAccount.BankAccountUtil;
import banking.bankAccount.builder.CardNumberBuilder;
import banking.bankAccount.builder.Director;
import banking.db.BankAccountsSQLiteManager;
import banking.db.CustomerIDSQLiteManager;

import java.io.FileNotFoundException;

public class Bank {
    private final BankAccountsSQLiteManager accountsManager;
    private final CustomerIDSQLiteManager customerIDManager;

    public Bank(String source) {
        accountsManager = new BankAccountsSQLiteManager();
        customerIDManager = new CustomerIDSQLiteManager();

        try {
            accountsManager.setDataSource(source);
            customerIDManager.setDataSource(source);
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        }

        accountsManager.createBankAccountsTableIfNotExists();
        customerIDManager.createAvailableCustomerIDTableIfNotExists();
    }

    public BankAccount createNewBankAccount() {
        // Building cardNumber
        Director director = new Director();
        CardNumberBuilder builder = new CardNumberBuilder();
        director.constructCardNumber(builder, customerIDManager);
        String cardNumber = builder.getResult();

        // Generating pin code
        String pinCode = BankAccountUtil.generatePinCode();

        // Creating new bank account
        BankAccount bankAccount = new BankAccount(cardNumber, pinCode);

        // Inserting into database table
        accountsManager.insertBankAccountIntoTable(bankAccount);

        return bankAccount;
    }

    public BankAccount loginIntoAccount(String cardNumber, String pinCode) {
        return accountsManager.getBankAccount(cardNumber, pinCode);
    }

    public void addIncome(BankAccount bankAccount, int amount) {
        accountsManager.addIncome(bankAccount, amount);
    }

    public BankAccountsSQLiteManager.MoneyTransferStatus transferMoney(BankAccount bankAccount, String cardNumber, int amount) {
        return accountsManager.transferMoney(bankAccount, cardNumber, amount);
    }

    public void closeAccount(BankAccount bankAccount) {
        accountsManager.removeBankAccountFromTable(bankAccount);
    }

    public void refreshAccountInfo(BankAccount bankAccount) {
        BankAccount refreshed = accountsManager.getBankAccount(bankAccount.getCardNumber(), bankAccount.getPinCode());
        bankAccount.setBalance(refreshed.getBalance());
    }
}
