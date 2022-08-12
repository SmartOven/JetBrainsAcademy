package converter.json;

import converter.Pretty;

public record JsonEntity(String name, JsonValue value) implements Pretty {

    public String getName() {
        return name;
    }

    public JsonValue getValue() {
        return value;
    }

    @Override
    public String toPretty() {
        return String.format("\"%s\": %s", name, value.toPretty());
    }

    @Override
    public String toPretty(int level) {
        return String.format("\"%s\": %s", name, value.toPretty(level));
    }

    @Override
    public String toString() {
        return String.format("\"%s\": %s", name, value);
    }
}
