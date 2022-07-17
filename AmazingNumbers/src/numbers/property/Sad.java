package numbers.property;

public class Sad extends Property {
    @Override
    protected boolean calculateValue() {
        if (value == null) {
            value = isSad();
        }
        return value;
    }

    @Override
    public String getName() {
        return "sad";
    }

    private boolean isSad() {
        return !(new Happy().getValue(num));
    }
}
