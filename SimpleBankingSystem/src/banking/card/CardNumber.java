package banking.card;

import java.util.Objects;

public abstract class CardNumber {
    int MII;
    int BIN;
    long customerID;
    String digits;

    public CardNumber(String s) {
        try {
            this.MII = Integer.parseInt(String.valueOf((s.charAt(0) - '0')));
            this.BIN = Integer.parseInt(s.substring(0, 6));
            this.customerID = Long.parseLong(s.substring(6));
        } catch (Exception e) {
            System.out.println("Something went wrong!!");
            e.printStackTrace();
        }
        this.digits = generateDigits();
    }

    public CardNumber(int MII, int BIN, long customerID) {
        this.MII = MII;
        this.BIN = BIN;
        this.customerID = customerID;
        this.digits = generateDigits();
    }

    public static boolean isCardNumber(String s) {
        if (!(s.length() >= 16 && s.length() <= 19)) {
            return false;
        }
        for (char digit: s.toCharArray()) {
            if (!Character.isDigit(digit)) {
                return false;
            }
        }
        return true;
    }

    private String generateDigits() {
        String id = String.valueOf(customerID);
        return BIN + "0".repeat(10 - id.length()) + id;
    }

    @Override
    public String toString() {
        return digits;
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
