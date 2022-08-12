package converter.json;

import converter.Converter;
import converter.xml.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonToXmlConverter implements Converter<JsonObject, XmlElement> {
    private static final String ROOT = "root";
    private static final String ELEMENT = "element";
    private static final String ATTRIBUTE = "@";
    private static final String VALUE = "#";

    @Override
    public XmlElement convert(JsonObject json) {
        final List<XmlElement> children = convertChildren(json);
        if (children.size() == 1) {
            return children.get(0);
        } else {
            return new XmlElement(ROOT, new XmlElements(children));
        }
    }

    private List<XmlElement> convertChildren(JsonObject json) {
        final List<XmlElement> elements = new ArrayList<>();
        final List<JsonEntity> entities = json.getValues();
        for (JsonEntity entity : entities) {
            if (entity.getName().isEmpty() || ATTRIBUTE.equals(entity.getName()) || VALUE.equals(entity.getName())) continue;
            final String name;
            if (entity.getName().startsWith(ATTRIBUTE) || entity.getName().startsWith(VALUE)) {
                name = entity.getName().substring(1);
                if (entities.stream().map(JsonEntity::getName).anyMatch(name::equals)) continue;
            } else {
                name = entity.getName();
            }
            final XmlElement element;
            if (entity.getValue() instanceof JsonNull) {
                element = convertNull(name);
            } else if (entity.getValue() instanceof JsonPrimitive) {
                element = convertPrimitive(name, (JsonPrimitive) entity.getValue());
            } else if (entity.getValue() instanceof JsonObject) {
                element = convertObject(name, (JsonObject) entity.getValue());
            } else if (entity.getValue() instanceof JsonArray) {
                element = convertArray(name, (JsonArray) entity.getValue());
            } else {
                throw new UnsupportedOperationException("Unsupported JSON value type: " + entity.getValue());
            }
            elements.add(element);
        }
        return elements;
    }

    private XmlElement convertNull(String name) {
        return new XmlElement(name);
    }

    private XmlElement convertPrimitive(String name, JsonPrimitive primitive) {
        return new XmlElement(name, new XmlSimpleValue(primitive.toString()));
    }

    private XmlElement convertObject(String name, JsonObject object) {
        if (isXml(name, object)) {
            final XmlValue value = getXmlValue(name, object);
            if (hasXmlAttributes(object) && hasXmlValue(name, object)) {
                final XmlAttributes attributes = getXmlAttributes(object);
                if (value == null) {
                    return new XmlElement(name, attributes);
                } else {
                    return new XmlElement(name, attributes, value);
                }
            } else {
                if (value == null) {
                    return convertNull(name);
                } else {
                    return new XmlElement(name, value);
                }
            }
        } else {
            final List<XmlElement> children = convertChildren(object);
            final XmlValue value;
            if (children.isEmpty()) {
                value = new XmlSimpleValue();
            } else {
                value = new XmlElements(children);
            }
            return new XmlElement(name, value);
        }
    }

    private XmlElement convertArray(String name, JsonArray array) {
        return new XmlElement(name, convertArray(array));
    }

    private boolean isXml(String name, JsonObject object) {
        final boolean isValid = object.getValues()
                .stream()
                .allMatch(o -> o.getName().length() > 1
                        && ((o.getName().startsWith(ATTRIBUTE)
                        && (o.getValue() instanceof JsonPrimitive
                        || o.getValue() instanceof JsonNull
                        || (o.getValue() instanceof JsonObject && ((JsonObject) o.getValue()).getValues().isEmpty())
                        || (o.getValue() instanceof JsonArray && ((JsonArray) o.getValue()).getValues().isEmpty()))
                        || o.getName().startsWith(VALUE))));
        if (!isValid) {
            return false;
        }
        final List<String> values = object.getValues()
                .stream()
                .map(JsonEntity::getName)
                .filter(s -> s.startsWith(VALUE)).toList();
        return values.size() == 1 && (VALUE + name).equals(values.get(0));
    }

    private boolean hasXmlAttributes(JsonObject object) {
        return object.getValues().stream().map(JsonEntity::getName).anyMatch(n -> n.startsWith(ATTRIBUTE) && n.length() > 1);
    }

    private boolean hasXmlValue(String name, JsonObject object) {
        return object.getValues().stream().anyMatch(e -> (VALUE + name).equals(e.getName()));
    }

    private XmlAttributes getXmlAttributes(JsonObject object) {
        return new XmlAttributes(object.getValues().stream()
                .filter(e -> e.getName().startsWith(ATTRIBUTE) && e.getName().length() > 1)
                .map(this::getXmlAttribute)
                .collect(Collectors.toList()));
    }

    private XmlAttribute getXmlAttribute(JsonEntity entity) {
        final String name = entity.getName().substring(1);
        final String value;
        if (entity.getValue() instanceof JsonPrimitive) {
            value = entity.getValue().toString();
        } else {
            value = "";
        }
        return new XmlAttribute(name, value);
    }

    private XmlValue getXmlValue(String name, JsonObject object) {
        return object.getValues().stream()
                .filter(e -> (VALUE + name).equals(e.getName()))
                .limit(1)
                .map(e -> convertValue(e.getValue()))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private XmlValue convertValue(JsonValue value) {
        if (value instanceof JsonNull) {
            return null;
        } else if (value instanceof JsonPrimitive) {
            return new XmlSimpleValue(value.toString());
        } else if (value instanceof JsonObject) {
            final List<XmlElement> children = convertChildren((JsonObject) value);
            if (children.isEmpty()) {
                return new XmlSimpleValue();
            } else {
                return new XmlElements(children);
            }
        } else if (value instanceof JsonArray) {
            return convertArray((JsonArray) value);
        }
        throw new IllegalArgumentException("Unknown Json value type: " + value);
    }

    private XmlElements convertArray(JsonArray array) {
        final List<XmlElement> values = new ArrayList<>();
        XmlElement element;
        for (JsonValue value : array.getValues()) {
            if (value instanceof JsonNull) {
                element = convertNull(ELEMENT);
            } else if (value instanceof JsonPrimitive) {
                element = convertPrimitive(ELEMENT, (JsonPrimitive) value);
            } else if (value instanceof JsonObject) {
                element = convertObject(ELEMENT, (JsonObject) value);
            } else if (value instanceof JsonArray) {
                element = convertArray(ELEMENT, (JsonArray) value);
            } else {
                throw new UnsupportedOperationException("Unsupported JSON value type: " + value);
            }
            values.add(element);
        }
        return new XmlElements(values);
    }
}
