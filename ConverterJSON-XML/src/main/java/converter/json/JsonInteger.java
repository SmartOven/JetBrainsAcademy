package converter.json;

public record JsonInteger(int value) implements JsonNumber {

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
