package numbers.property;

public class Odd extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isOdd();
        }
        return value;
    }

    @Override
    public String getName() {
        return "odd";
    }

    private boolean isOdd() {
        return num % 2 != 0;
    }
}
