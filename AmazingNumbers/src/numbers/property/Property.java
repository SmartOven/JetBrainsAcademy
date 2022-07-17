package numbers.property;

/**
 * How to use:
 *
 * Override getName() (return actual name instead)
 * Override getValue() to return does number has property or not
 */
public abstract class Property {
    protected Boolean value;
    protected long num;

    protected void setNum(long num) {
        this.num = num;
        this.value = null;
    }

    public boolean getValue(long num) {
        setNum(num);
        return calculateValue();
    }

    protected boolean calculateValue() {
        return value;
    }

    public String getName() {
        return "property";
    }
}
