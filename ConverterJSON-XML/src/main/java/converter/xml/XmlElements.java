package converter.xml;

import java.util.List;

public record XmlElements(List<XmlElement> values) implements XmlComplexValue {

    public List<XmlElement> getValues() {
        return values;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (XmlElement value : values) {
            builder.append(value);
        }
        return builder.toString();
    }
}
