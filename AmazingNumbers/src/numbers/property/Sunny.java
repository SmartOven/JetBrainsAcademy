package numbers.property;

public class Sunny extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isSunny();
        }
        return value;
    }

    @Override
    public String getName() {
        return "sunny";
    }

    private boolean isSunny() {
        long root = (long) Math.sqrt((double) (num + 1));
        return root * root == (num + 1);
    }
}