package converter.xml;

public class XmlSimpleValue implements XmlValue {
    protected final String value;

    public XmlSimpleValue() {
        this("");
    }

    public XmlSimpleValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
