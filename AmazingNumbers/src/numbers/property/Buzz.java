package numbers.property;

public class Buzz extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isBuzz();
        }
        return value;
    }

    @Override
    public String getName() {
        return "buzz";
    }

    private boolean isBuzz() {
        return num % 7 == 0 || num % 10 == 7;
    }
}
