package banking.bankAccount.builder;

/**
 * Builder for card numbers
 * Instead of having interface Builder and classes ConcreteBuilders,
 * I made only the ConcreteBuilder (CardNumberBuilder) to simplify the architecture of the project
 */
public class CardNumberBuilder {
    private int MII;
    private int BIN;
    private long customerID;
    private int checkSum;

    public CardNumberBuilder() {
        // Default values
        MII = 4;
        BIN = 4000_00;
        customerID = 0;
        checkSum = 2;
    }

    public void setMII(int MII) {
        this.MII = MII;
    }

    public void setBIN(int BIN) {
        this.BIN = BIN;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public String getResultWithoutCheckSum() {
        StringBuilder cardNumber = new StringBuilder();
        String customerIDStringValue = String.valueOf(customerID);

        cardNumber.append(BIN);
        cardNumber.append("0".repeat(9 - customerIDStringValue.length()));
        cardNumber.append(customerIDStringValue);
        return cardNumber.toString();
    }

    public String getResult() {
        return getResultWithoutCheckSum() + checkSum;
    }
}
