package banking;

import banking.account.BankAccount;
import banking.card.CardNumber;

import java.util.HashMap;
import java.util.Map;


public class Bank {
    Map<String, BankAccount> bankAccounts;

    public Bank() {
        this.bankAccounts = new HashMap<>();
    }

    public void createAccount(String pinCode, BankAccount bankAccount) {
        bankAccounts.put(pinCode, bankAccount);
    }

    public BankAccount getBankAccount(String pinCode, CardNumber cardNumber) {
        BankAccount bankAccount = bankAccounts.getOrDefault(pinCode, null);
        if (bankAccount == null || bankAccount.getCardNumber().equals(cardNumber)) {
            return bankAccount;
        }
        return null;
    }
}
