package converter.json;

public record JsonBoolean(boolean value) implements JsonPrimitive {

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
