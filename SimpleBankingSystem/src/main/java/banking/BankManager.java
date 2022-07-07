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
    private final Bank bank;
    private BankAccount currentBankAccount;
    private String currentPinCode;
    private BankManagerState state;

    private static final Map<BankManagerState, String> operationsInfo;

    static {
        operationsInfo = new HashMap<>();
        operationsInfo.put(BankManagerState.NOT_LOGGED, "1. Create an account\n2. Log into account\n0. Exit");
        operationsInfo.put(BankManagerState.LOGGED, "1. Balance\n2. Add income\n3. Do transfer\n4. Close account\n5. Log out\n0. Exit");
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
                    case "1" -> {
                        String[] createdBankAccountInfo = createNewAccount();
                        System.out.println("Your card has been created\nYour card number:");
                        System.out.println(createdBankAccountInfo[0]);
                        System.out.println("Your card PIN:");
                        System.out.println(createdBankAccountInfo[1]);
                    }
                    case "2" -> {
                        System.out.println("Enter your card number:");
                        String userCardNumber = console.nextLine();
                        System.out.println("Enter your PIN:");
                        String pinCode = console.nextLine();
                        if (!CardNumber.isValidCardNumber(userCardNumber)) {
                            System.out.println("Wrong card number or PIN!");
                        } else {
                            CardNumber cardNumber = new StandardCardNumber(userCardNumber);
                            boolean loginSuccessful = loginIntoAccount(pinCode, cardNumber);
                            if (loginSuccessful) {
                                System.out.println("You have successfully logged in!");
                                state = BankManagerState.LOGGED;
                                currentPinCode = pinCode;
                            } else {
                                System.out.println("Wrong card number or PIN!");
                            }
                        }
                    }
                }
            } else if (state.equals(BankManagerState.LOGGED)) {
                switch (userInput) {
                    case "1" -> System.out.println("Balance: " + currentBankAccount.getBalance());
                    case "2" -> {
                        System.out.println("Enter income:");
                        int income = Integer.parseInt(console.nextLine());
                        bank.addIncome(currentBankAccount, income);
                        System.out.println("Income was added!");
                    }
                    case "3" -> {
                        System.out.println("Transfer");
                        System.out.println("Enter card number:");
                        String transferCardNumber = console.nextLine();
                        if (transferCardNumber.equals(currentBankAccount.getCardNumber().toString())) {
                            System.out.println("You can't transfer money to the same account!");
                        } else if (!CardNumber.isValidCardNumber(transferCardNumber)) {
                            System.out.println("Probably you made a mistake in the card number. Please try again!");
                        } else if (!bank.hasBankAccount(transferCardNumber)) {
                            System.out.println("Such a card does not exist.");
                        } else {
                            System.out.println("Enter how much money you want to transfer:");
                            int amount = Integer.parseInt(console.nextLine());
                            int moneyHas = currentBankAccount.getBalance();
                            if (moneyHas < amount) {
                                System.out.println("Not enough money!");
                            } else {
                                bank.transferMoney(currentBankAccount.getCardNumber().toString(), transferCardNumber, amount);
                                System.out.println("Success!");
                            }
                        }
                    }
                    case "4" -> {
                        bank.deleteAccount(currentBankAccount);
                        System.out.println("The account has been closed!");
                        currentBankAccount = null;
                        state = BankManagerState.NOT_LOGGED;
                    }
                    case "5" -> {
                        System.out.println("You have successfully logged out!");
                        currentBankAccount = null;
                        state = BankManagerState.NOT_LOGGED;
                    }
                }
                if (currentBankAccount != null) {
                    currentBankAccount = bank.getBankAccount(currentPinCode, currentBankAccount.getCardNumber());
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
