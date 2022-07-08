package banking.bankAccount.builder;

import banking.bankAccount.BankAccountUtil;
import banking.db.CustomerIDSQLiteManager;

public class Director {
    /**
     * Leaves MII and BIN by default
     * Set customerID to the next available from database table
     * Set checkSum by the Luhn algorithm
     *
     * @param builder CardNumberBuilder
     */
    public void constructCardNumber(CardNumberBuilder builder, CustomerIDSQLiteManager manager) {
        // Getting next available customer ID from database table and update it (increment)
        int customerID = manager.incrementAndGetNextAvailableCustomerID();
        builder.setCustomerID(customerID);

        // Getting result cardNumber without checkSum, then calculating it and set
        String cardNumberWithoutCheckSum = builder.getResultWithoutCheckSum();
        int checkSum = BankAccountUtil.calculateCheckSum(cardNumberWithoutCheckSum);
        builder.setCheckSum(checkSum);
    }
}
