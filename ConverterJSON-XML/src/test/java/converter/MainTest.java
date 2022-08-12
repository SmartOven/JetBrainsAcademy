package converter;

import converter.json.JsonToXmlConverter;
import converter.json.JsonObject;
import converter.json.JsonParser;
import converter.xml.XmlToJsonConverter;
import converter.xml.XmlElement;
import converter.xml.XmlParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void parseJsonConvertToXml() {
        // Creating parser and parsing json
        Parser<JsonObject> parser = new JsonParser();
        JsonObject json = parser.parse(jsonString);

        // Creating converter and converting to xml
        Converter<JsonObject, XmlElement> converter = new JsonToXmlConverter();
        XmlElement xml = converter.convert(json);

        // Checking result
        assertEquals(expectedXml, xml.toString());
    }

    @Test
    void parseXmlConvertToJson() {
        // Creating parser and parsing xml
        Parser<XmlElement> parser = new XmlParser();
        XmlElement xml = parser.parse(xmlString);

        // Creating converter and converting to json
        Converter<XmlElement, JsonObject> converter = new XmlToJsonConverter();
        JsonObject json = converter.convert(xml);

        // Checking result
        assertEquals(expectedJson, json.toString());
    }

    private static final String jsonString = """
            {
              "transactions": [
                {
                  "id": "6753322",
                  "number": {
                    "@region": "Russia",
                    "#number": "8-900-000-00-00"
                  },
                  "date": {
                    "@day": "12",
                    "@month": "12",
                    "@year": "2018",
                    "#date": null
                  },
                  "amount": {
                    "@currency": "EUR",
                    "#amount": "1000.00"
                  },
                  "completed": "true"
                },
                {
                  "id": "67533244",
                  "number": {
                    "@region": "Russia",
                    "#number": "8-900-000-00-01"
                  },
                  "date": {
                    "@day": "13",
                    "@month": "12",
                    "@year": "2018",
                    "#date": null
                  },
                  "amount": {
                    "@currency": "RUB",
                    "#amount": "2000.00"
                  },
                  "completed": "true"
                },
                {
                  "id": "67533257",
                  "number": {
                    "@region": "Russia",
                    "#number": "8-900-000-00-02"
                  },
                  "date": {
                    "@day": "14",
                    "@month": "12",
                    "@year": "2018",
                    "#date": null
                  },
                  "amount": {
                    "@currency": "EUR",
                    "#amount": "3000.00"
                  },
                  "completed": "false"
                },
                {
                  "id": "67533259",
                  "number": {
                    "@region": "Ukraine",
                    "#number": "8-900-000-00-03"
                  },
                  "date": {
                    "@day": "15",
                    "@month": "12",
                    "@year": "2018",
                    "#date": null
                  },
                  "amount": {
                    "@currency": "GRN",
                    "#amount": "4000.00"
                  },
                  "completed": "false"
                },
                {
                  "id": "67533566",
                  "number": {
                    "@region": "Ukraine",
                    "#number": "8-900-000-00-04"
                  },
                  "date": {
                    "@day": "16",
                    "@month": "12",
                    "@year": "2018",
                    "#date": null
                  },
                  "amount": {
                    "@currency": "USD",
                    "#amount": "5000.00"
                  },
                  "completed": "false"
                }
              ]
            }
            """;
    private static final String expectedJson = "{\"transactions\": [{\"id\": 6753322, \"number\": {\"@region\": Russia, \"#number\": 8-900-000-00-00}, \"date\": {\"@day\": 12, \"@month\": 12, \"@year\": 2018, \"#date\": null}, \"amount\": {\"@currency\": EUR, \"#amount\": 1000.00}, \"completed\": true}, {\"id\": 67533244, \"number\": {\"@region\": Russia, \"#number\": 8-900-000-00-01}, \"date\": {\"@day\": 13, \"@month\": 12, \"@year\": 2018, \"#date\": null}, \"amount\": {\"@currency\": RUB, \"#amount\": 2000.00}, \"completed\": true}, {\"id\": 67533257, \"number\": {\"@region\": Russia, \"#number\": 8-900-000-00-02}, \"date\": {\"@day\": 14, \"@month\": 12, \"@year\": 2018, \"#date\": null}, \"amount\": {\"@currency\": EUR, \"#amount\": 3000.00}, \"completed\": false}, {\"id\": 67533259, \"number\": {\"@region\": Ukraine, \"#number\": 8-900-000-00-03}, \"date\": {\"@day\": 15, \"@month\": 12, \"@year\": 2018, \"#date\": null}, \"amount\": {\"@currency\": GRN, \"#amount\": 4000.00}, \"completed\": false}, {\"id\": 67533566, \"number\": {\"@region\": Ukraine, \"#number\": 8-900-000-00-04}, \"date\": {\"@day\": 16, \"@month\": 12, \"@year\": 2018, \"#date\": null}, \"amount\": {\"@currency\": USD, \"#amount\": 5000.00}, \"completed\": false}]}";
    private static final String xmlString = "<transactions><transaction><id>6753322</id><number region=\"Russia\">8-900-000-00-00</number><date day=\"12\" month=\"12\" year=\"2018\"/><amount currency=\"EUR\">1000.00</amount><completed>true</completed></transaction><transaction><id>67533244</id><number region=\"Russia\">8-900-000-00-01</number><date day=\"13\" month=\"12\" year=\"2018\"/><amount currency=\"RUB\">2000.00</amount><completed>true</completed></transaction><transaction><id>67533257</id><number region=\"Russia\">8-900-000-00-02</number><date day=\"14\" month=\"12\" year=\"2018\"/><amount currency=\"EUR\">3000.00</amount><completed>false</completed></transaction><transaction><id>67533259</id><number region=\"Ukraine\">8-900-000-00-03</number><date day=\"15\" month=\"12\" year=\"2018\"/><amount currency=\"GRN\">4000.00</amount><completed>false</completed></transaction><transaction><id>67533566</id><number region=\"Ukraine\">8-900-000-00-04</number><date day=\"16\" month=\"12\" year=\"2018\"/><amount currency=\"USD\">5000.00</amount><completed>false</completed></transaction></transactions>";
    private static final String expectedXml = "<transactions><element><id>6753322</id><number region=\"Russia\">8-900-000-00-00</number><date day=\"12\" month=\"12\" year=\"2018\"/><amount currency=\"EUR\">1000.00</amount><completed>true</completed></element><element><id>67533244</id><number region=\"Russia\">8-900-000-00-01</number><date day=\"13\" month=\"12\" year=\"2018\"/><amount currency=\"RUB\">2000.00</amount><completed>true</completed></element><element><id>67533257</id><number region=\"Russia\">8-900-000-00-02</number><date day=\"14\" month=\"12\" year=\"2018\"/><amount currency=\"EUR\">3000.00</amount><completed>false</completed></element><element><id>67533259</id><number region=\"Ukraine\">8-900-000-00-03</number><date day=\"15\" month=\"12\" year=\"2018\"/><amount currency=\"GRN\">4000.00</amount><completed>false</completed></element><element><id>67533566</id><number region=\"Ukraine\">8-900-000-00-04</number><date day=\"16\" month=\"12\" year=\"2018\"/><amount currency=\"USD\">5000.00</amount><completed>false</completed></element></transactions>";
}