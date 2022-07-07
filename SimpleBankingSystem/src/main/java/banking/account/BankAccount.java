package banking.account;

import banking.card.CardNumber;
import banking.card.builder.StandardCardNumberBuilder;
import banking.card.director.Director;

/**
 * Equals to the BankCard
 */
public class BankAccount {
    private final CardNumber cardNumber;
    private int balance;

    public BankAccount(CardNumber cardNumber, int balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public BankAccount() {
        this.cardNumber = generateCardNumber();
        this.balance = 0;
    }

    private CardNumber generateCardNumber() {
        Director director = new Director();
        StandardCardNumberBuilder builder = new StandardCardNumberBuilder();
        director.constructStandardCardNumber(builder);
        return builder.getResult();
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public int getBalance() {
        return balance;
    }
}
