package banking;

import banking.account.BankAccount;

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
}
