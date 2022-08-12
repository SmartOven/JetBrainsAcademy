package converter.json;

public record JsonDouble(double value) implements JsonNumber {

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
