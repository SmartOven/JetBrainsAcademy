package numbers.property;

public class Spy extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isSpy();
        }
        return value;
    }

    @Override
    public String getName() {
        return "spy";
    }

    private boolean isSpy() {
        char[] digits = String.valueOf(num).toCharArray();
        long sum = 0;
        long product = 1;

        for (char digit : digits) {
            sum += digit - '0';
            product *= (digit - '0');
        }

        return sum == product;
    }
}