package converter;

import converter.json.JsonToXmlConverter;
import converter.json.JsonObject;
import converter.json.JsonParser;
import converter.xml.XmlToJsonConverter;
import converter.xml.XmlElement;
import converter.xml.XmlParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        final String input = getInputFromFile();
        if (isJson(input)) {
            final Parser<JsonObject> parser = new JsonParser();
            final JsonObject json = parser.parse(input);
            final Converter<JsonObject, XmlElement> converter = new JsonToXmlConverter();
            final XmlElement xml = converter.convert(json);
            System.out.println(xml);
        } else if (isXml(input)) {
            final Parser<XmlElement> parser = new XmlParser();
            final XmlElement xml = parser.parse(input);
            final Converter<XmlElement, JsonObject> converter = new XmlToJsonConverter();
            final JsonObject json = converter.convert(xml);
            System.out.println(json.toPretty());
        } else {
            throw new IllegalArgumentException("Unknown input type: " + input);
        }
    }

    private static String getInputFromFile() throws IOException {
        final File file = new File("test.txt");
        try (final FileReader reader = new FileReader(file)) {
            final StringBuilder builder = new StringBuilder();
            final char[] buffer = new char[1024];
            int count;
            while ((count = reader.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, count));
            }
            return builder.toString();
        }
    }

    private static boolean isJson(String input) {
        return input.startsWith("{");
    }

    private static boolean isXml(String input) {
        return input.startsWith("<");
    }
}
