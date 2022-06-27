package banking.account;

import banking.card.CardNumber;
import banking.card.builder.StandardCardNumberBuilder;
import banking.card.director.Director;

import java.util.Random;

/**
 * Equals to the BankCard
 */
public class BankAccount {
    private CardNumber cardNumber;
    private long balance;

    public BankAccount(CardNumber cardNumber) {
        this.cardNumber = cardNumber;
        this.balance = 0;
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
}
