package numbers.property;

public class Even extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isEven();
        }
        return value;
    }

    @Override
    public String getName() {
        return "even";
    }

    private boolean isEven() {
        return num % 2 == 0;
    }
}
