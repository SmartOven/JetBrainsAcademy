package converter.json;

public record JsonString(String value) implements JsonPrimitive {

    @Override
    public String toPretty() {
        return String.format("\"%s\"", value);
    }

    @Override
    public String toString() {
        return value;
    }
}
