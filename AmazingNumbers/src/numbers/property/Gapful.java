package numbers.property;

public class Gapful extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isGapful();
        }
        return value;
    }

    @Override
    public String getName() {
        return "gapful";
    }

    private boolean isGapful() {
        String n = String.valueOf(num);
        if (n.length() < 3) {
            return false;
        }
        int divisor = (n.charAt(0) - '0') * 10 + n.charAt(n.length() - 1) - '0';
        return num % divisor == 0;
    }
}