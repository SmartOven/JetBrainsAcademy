package banking.card;

public abstract class CardNumber {
    int MII;
    int BIN;
    long customerID;
    public CardNumber(int MII, int BIN, long customerID) {
        this.MII = MII;
        this.BIN = BIN;
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return String.valueOf(BIN) + customerID;
    }
}
