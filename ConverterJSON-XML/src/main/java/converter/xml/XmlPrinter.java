package converter.xml;

import java.io.PrintStream;

public class XmlPrinter {
    private final PrintStream printer;

    public XmlPrinter() {
        printer = System.out;
    }

    public void print(XmlElement element) {
        final String path = element.name;
        final XmlValue value = element.value;
        printElement();
        printPath(path);
        printValue(value);
        printAttributes(element.attributes);
        printer.println();
        printNext(path, value);
    }

    private void printNext(String path, XmlValue value) {
        if (value instanceof XmlElement) {
            print(path, (XmlElement) value);
        } else if (value instanceof XmlElements) {
            for (XmlElement xElement : ((XmlElements) value).getValues()) {
                print(path, xElement);
            }
        }
    }

    private void print(String parentPath, XmlElement element) {
        final String path = parentPath + ", " + element.name;
        final XmlValue value = element.value;
        printElement();
        printPath(path);
        printValue(value);
        printAttributes(element.attributes);
        printer.println();
        printNext(path, value);
    }

    private void printElement() {
        printer.println("Element:");
    }

    private void printPath(String path) {
        printer.println("path = " + path);
    }

    private void printValue(XmlValue value) {
        if (value == null) {
            printer.println("value = null");
        } else if (value instanceof final XmlSimpleValue simpleValue) {
            printer.println("value = \"" + simpleValue + "\"");
        }
    }

    private void printAttributes(XmlAttributes attributes) {
        if (attributes != null) {
            printer.println("attributes:");
            for (XmlAttribute attribute : attributes.getValues()) {
                printer.println(attribute.name + " = \"" + attribute.value + "\"");
            }
        }
    }
}