package numbers.property;

public class Happy extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isHappy();
        }
        return value;
    }

    @Override
    public String getName() {
        return "happy";
    }

    private boolean isHappy() {
        long _num = num;
        if (_num == 0) {
            return false;
        }
        int counter = 100;
        while (counter-- != 0) {
            long sum = 0;
            while (_num > 0) {
                long x = _num % 10;
                sum += x * x;
                _num /= 10;
            }
            if (sum == 1) {
                return true;
            }
            _num = sum;
        }
        return false;
    }
}
