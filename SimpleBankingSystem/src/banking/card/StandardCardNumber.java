package banking.card;

public class StandardCardNumber extends CardNumber {

    public StandardCardNumber(int MII, int BIN, long customerID) {
        super(MII, BIN, customerID);
    }

    public StandardCardNumber(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
