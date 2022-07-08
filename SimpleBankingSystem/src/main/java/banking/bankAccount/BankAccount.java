package banking.bankAccount;

public class BankAccount {
    private final String cardNumber;
    private final String pinCode;
    private int balance;

    public BankAccount(String cardNumber, String pinCode) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.balance = 0;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPinCode() {
        return pinCode;
    }
}
