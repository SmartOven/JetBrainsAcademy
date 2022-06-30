package banking.card;

import banking.util.LuhnAlgorithm;

import java.util.Objects;

public abstract class CardNumber {
    private int MII;
    private int BIN;
    private long customerID;
    private int checkSum;
    String stringValue;

    public CardNumber(String s) {
        try {
            this.MII = Integer.parseInt(String.valueOf(s.charAt(0)));
            this.BIN = Integer.parseInt(s.substring(0, 6));
            this.customerID = Long.parseLong(s.substring(6, s.length() - 1));
            this.checkSum = Integer.parseInt(String.valueOf(s.charAt(s.length() - 1)));
        } catch (Exception e) {
            System.out.println("Something went wrong!!");
            e.printStackTrace();
        }
        this.stringValue = generateStringValue();
    }

    public CardNumber(int MII, int BIN, long customerID, int checkSum) {
        this.MII = MII;
        this.BIN = BIN;
        this.customerID = customerID;
        this.checkSum = checkSum;
        this.stringValue = generateStringValue();
    }

    public static boolean isValidCardNumber(String s) {
        if (!(s.length() >= 16 && s.length() <= 19)) {
            return false;
        }
        for (char digit: s.toCharArray()) {
            if (!Character.isDigit(digit)) {
                return false;
            }
        }
        return isValidCheckSum(s);
    }

    private static boolean isValidCheckSum(String s) {
        int checkSumDigit = s.charAt(s.length() - 1) - '0';
        return checkSumDigit == LuhnAlgorithm.getCheckSum(s.substring(0, s.length() - 1));
    }

    private String generateStringValue() {
        String stringCustomerID = String.valueOf(customerID);
        return BIN + "0".repeat(9 - stringCustomerID.length()) + stringCustomerID + checkSum;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardNumber that = (CardNumber) o;
        return this.toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(MII, BIN, customerID);
    }
}
