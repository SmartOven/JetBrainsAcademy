package banking;

import banking.account.BankAccount;
import banking.card.CardNumber;
import banking.card.StandardCardNumber;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Wrapper class for Bank class
 */
public class BankManager {
    private Bank bank;
    private BankAccount currentBankAccount;
    private BankManagerState state;

    private static final Map<BankManagerState, String> operationsInfo;

    static {
        operationsInfo = new HashMap<>();
        operationsInfo.put(BankManagerState.NOT_LOGGED, "1. Create an account\n2. Log into account\n0. Exit");
        operationsInfo.put(BankManagerState.LOGGED, "1. Balance\n2. Log out\n0. Exit");
    }

    public BankManager(Bank bank) {
        this.bank = bank;
        this.currentBankAccount = null;
        this.state = BankManagerState.NOT_LOGGED;
    }

    public void work(Scanner console) {
        while (true) {
            // Print info and get command from user
            String infoMessage = operationsInfo.get(state);
            System.out.println(infoMessage);
            String userInput = console.nextLine();

            // If Exit -> terminate
            if (userInput.equals("0")) {
                System.out.println("Bye!");
                break;
            }

            // Else resolve user input
            if (state.equals(BankManagerState.NOT_LOGGED)) {
                switch (userInput) {
                    case "1":
                        String[] createdBankAccountInfo = createNewAccount();
                        System.out.println("Your card has been created\nYour card number:");
                        System.out.println(createdBankAccountInfo[0]);
                        System.out.println("Your card PIN:");
                        System.out.println(createdBankAccountInfo[1]);
                        break;
                    case "2":
                        System.out.println("Enter your card number:");
                        String userCardNumber = console.nextLine();
                        System.out.println("Enter your PIN:");
                        String pinCode = console.nextLine();
                        if (!CardNumber.isCardNumber(userCardNumber)) {
                            System.out.println("Wrong card number or PIN!");
                        } else {
                            CardNumber cardNumber = new StandardCardNumber(userCardNumber);
                            boolean loginSuccessful = loginIntoAccount(pinCode, cardNumber);
                            if (loginSuccessful) {
                                System.out.println("You have successfully logged in!");
                                state = BankManagerState.LOGGED;
                            } else {
                                System.out.println("Wrong card number or PIN!");
                            }
                        }
                        break;
                }
            } else if (state.equals(BankManagerState.LOGGED)) {
                switch (userInput) {
                    case "1":
                        System.out.println("Balance: " + currentBankAccount.getBalance());
                        break;
                    case "2":
                        System.out.println("You have successfully logged out!");
                        currentBankAccount = null;
                        state = BankManagerState.NOT_LOGGED;
                }
            }
        }
    }

    private String[] createNewAccount() {
        BankAccount bankAccount = new BankAccount();
        String pinCode = generatePinCode();
        bank.createAccount(pinCode, bankAccount);
        return new String[]{bankAccount.getCardNumber().toString(), pinCode};
    }

    public boolean loginIntoAccount(String pinCode, CardNumber cardNumber) {
        BankAccount bankAccount = bank.getBankAccount(pinCode, cardNumber);
        if (bankAccount == null) {
            return false;
        }
        currentBankAccount = bankAccount;
        return true;
    }

    public void logout() {
        currentBankAccount = null;
    }

    private String generatePinCode() {
        Random randomPinGenerator = new Random();
        String number = String.valueOf(randomPinGenerator.nextInt(10000));
        String leadingZeros = "0".repeat(4 - number.length());
        return leadingZeros + number;
    }
}
