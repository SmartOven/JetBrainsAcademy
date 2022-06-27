package banking.card.director;

import banking.card.builder.StandardCardNumberBuilder;
import banking.customerIDGenerator.CustomerIDGenerator;

public class Director {
    public void constructStandardCardNumber(StandardCardNumberBuilder builder) {
        builder.setMII(4);
        builder.setBIN(400000);
        builder.setCustomerID(CustomerIDGenerator.getInstance().getNextAvailableID());
    }
}
