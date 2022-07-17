package numbers.property;

public class Duck extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isDuck();
        }
        return value;
    }

    @Override
    public String getName() {
        return "duck";
    }

    private boolean isDuck() {
        long _num = num;
        while (_num > 0) {
            if (_num % 10 == 0) {
                return true;
            }
            _num /= 10;
        }
        return false;
    }
}