package converter.json;

import converter.Pretty;

public interface JsonValue extends Pretty {
    default String toPretty() {
        return toString();
    }
}
