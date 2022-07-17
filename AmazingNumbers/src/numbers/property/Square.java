package numbers.property;

public class Square extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isSquare();
        }
        return value;
    }

    @Override
    public String getName() {
        return "square";
    }

    private boolean isSquare() {
        long root = (long) Math.sqrt((double) num);
        return root * root == num;
    }
}