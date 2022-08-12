package converter.xml;

import java.util.List;
import java.util.StringJoiner;

public record XmlAttributes(List<XmlAttribute> values) {

    public List<XmlAttribute> getValues() {
        return values;
    }

    @Override
    public String toString() {
        final StringJoiner joiner = new StringJoiner(" ");
        for (XmlAttribute attribute : values) {
            joiner.add(attribute.toString());
        }
        return joiner.toString();
    }
}
