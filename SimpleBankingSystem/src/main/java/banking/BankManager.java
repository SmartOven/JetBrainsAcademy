package banking;

import banking.bankAccount.BankAccount;
import banking.bankAccount.BankAccountUtil;
import banking.db.BankAccountsSQLiteManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankManager {
    private enum BankManagerState {
        NOT_LOGGED,
        LOGGED
    }

    private final Bank bank;
    private BankAccount loggedBankAccount;
    private BankManagerState state;

    private static final Map<BankManagerState, String> operationsInfo;

    static {
        operationsInfo = new HashMap<>();
        operationsInfo.put(BankManagerState.NOT_LOGGED, "1. Create an account\n2. Log into account\n0. Exit");
        operationsInfo.put(BankManagerState.LOGGED, "1. Balance\n2. Add income\n3. Do transfer\n4. Close account\n5. Log out\n0. Exit");
    }

    public BankManager(Bank bank) {
        this.bank = bank;
        this.loggedBankAccount = null;
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
                        // Creating new bank account
                        BankAccount createdBankAccount = bank.createNewBankAccount();

                        // Output user info
                        System.out.println("Your card has been created\nYour card number:");
                        System.out.println(createdBankAccount.getCardNumber());
                        System.out.println("Your card PIN:");
                        System.out.println(createdBankAccount.getPinCode());
                    }
                    case "2" -> {
                        // Get user input: cardNumber and pinCode
                        System.out.println("Enter your card number:");
                        String cardNumber = console.nextLine();
                        System.out.println("Enter your PIN:");
                        String pinCode = console.nextLine();

                        // Check if cardNumber is valid
                        if (!BankAccountUtil.isValidCardNumber(cardNumber)) {
                            System.out.println("Wrong card number or PIN!");
                        } else {
                            BankAccount bankAccount = bank.loginIntoAccount(cardNumber, pinCode);

                            // Check if bankAccount with cardNumber and pinCode exists
                            if (bankAccount == null) {
                                System.out.println("Wrong card number or PIN!");
                            } else {
                                System.out.println("You have successfully logged in!");

                                // Setting logged bank account
                                loggedBankAccount = bankAccount;

                                // Set logged state
                                state = BankManagerState.LOGGED;
                            }
                        }
                    }
                }
            } else if (state.equals(BankManagerState.LOGGED)) {
                switch (userInput) {
                    case "1" -> System.out.println("Balance: " + loggedBankAccount.getBalance());
                    case "2" -> {
                        // Get user input: amount of money to add
                        System.out.println("Enter income:");
                        int income = Integer.parseInt(console.nextLine());

                        // Adding money to the bank account
                        bank.addIncome(loggedBankAccount, income);
                        System.out.println("Income was added!");
                    }
                    case "3" -> {
                        // Getting user input: cardNumber and money amount
                        System.out.println("Transfer");
                        System.out.println("Enter card number:");
                        String destinationCardNumber = console.nextLine();
                        System.out.println("Enter how much money you want to transfer:");
                        int amount = Integer.parseInt(console.nextLine());

                        // Trying to transfer money from loggedAccount to the destinationAccount
                        BankAccountsSQLiteManager.MoneyTransferStatus status = bank.transferMoney(loggedBankAccount, destinationCardNumber, amount);

                        // Resolving result and output user info
                        switch (status) {
                            case SUCCESS_TRANSFER -> System.out.println("Success!");
                            case NOT_ENOUGH_MONEY -> System.out.println("Not enough money!");
                            case SAME_ACCOUNT_TRANSFER -> System.out.println("You can't transfer money to the same account!");
                            case NOT_VALID_CARD_NUMBER -> System.out.println("Probably you made a mistake in the card number. Please try again!");
                            case CARD_NUMBER_DOESNT_EXIST -> System.out.println("Such a card does not exist.");
                            case SQL_ERROR -> System.out.println("Something went wrong during the operation. Try again!");
                        }
                    }
                    case "4" -> {
                        // Closing account
                        bank.closeAccount(loggedBankAccount);
                        System.out.println("The account has been closed!");
                        loggedBankAccount = null;

                        // Setting state to not logged
                        state = BankManagerState.NOT_LOGGED;
                    }
                    case "5" -> {
                        System.out.println("You have successfully logged out!");
                        loggedBankAccount = null;

                        // Setting state to not logged
                        state = BankManagerState.NOT_LOGGED;
                    }
                }

                // Refresh account info
                if (loggedBankAccount != null) {
                    bank.refreshAccountInfo(loggedBankAccount);
                }
            }
        }
    }
}
