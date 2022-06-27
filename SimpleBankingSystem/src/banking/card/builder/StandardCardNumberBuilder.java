package banking.card.builder;

import banking.card.StandardCardNumber;

public class StandardCardNumberBuilder implements CardNumberBuilder {
    private int MII;
    private int BIN;
    private long customerID;

    @Override
    public void setMII(int MII) {
        this.MII = MII;
    }

    @Override
    public void setBIN(int BIN) {
        this.BIN = BIN;
    }

    @Override
    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    @Override
    public StandardCardNumber getResult() {
        return new StandardCardNumber(MII, BIN, customerID);
    }
}
