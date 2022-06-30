package banking.card.director;

import banking.card.builder.StandardCardNumberBuilder;
import banking.customerIDGenerator.CustomerIDGenerator;
import banking.util.LuhnAlgorithm;

public class Director {
    public void constructStandardCardNumber(StandardCardNumberBuilder builder) {
        int MII = 4;
        int BIN = 400000;
        long customerID = CustomerIDGenerator.getInstance().getNextAvailableID();
        String stringCustomerID = String.valueOf(customerID);
        int checkSum = LuhnAlgorithm.getCheckSum(BIN + "0".repeat(9 - stringCustomerID.length()) + stringCustomerID);

        builder.setMII(MII);
        builder.setBIN(BIN);
        builder.setCustomerID(customerID);
        builder.setCheckSum(checkSum);
    }
}
