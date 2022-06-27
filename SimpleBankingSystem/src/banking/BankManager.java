package banking;

import banking.account.BankAccount;

import java.util.Random;

/**
 * Wrapper class for Bank class
 */
public class BankManager {
    Bank bank;
    public BankManager(Bank bank) {
        this.bank = bank;
    }

    public void createNewAccount() {
        BankAccount bankAccount = new BankAccount();
        String pinCode = generatePinCode();
        bank.createAccount(pinCode, bankAccount);
    }

    private String generatePinCode() {
        Random randomPinGenerator = new Random();
        String number = String.valueOf(randomPinGenerator.nextInt() % 10000);
        String leadingZeros = "0".repeat(4 - number.length());
        return leadingZeros + number;
    }
}
