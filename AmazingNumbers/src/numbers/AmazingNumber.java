package numbers;

import numbers.property.*;

import java.util.*;

public class AmazingNumber {
    private static final Map<String, Property> properties;

    static {
        // Has only even and odd properties by default
        properties = new HashMap<>();
        Property even = new Even();
        Property odd = new Odd();
        properties.put(even.getName(), even);
        properties.put(odd.getName(), odd);
    }

    public static void setProperties(Property[] availableProperties) {
        for (Property property : availableProperties) {
            properties.putIfAbsent(property.getName(), property);
        }
    }

    public static Boolean getProperty(String propertyName, long num) {
        return properties.get(propertyName).getValue(num);
    }

    public static Map<String, Boolean> getProperties(long num) {
        Map<String, Boolean> numProperties = new HashMap<>();
        for (String propertyName : properties.keySet()) {
            Boolean propertyValue = getProperty(propertyName, num);

            numProperties.put(propertyName, propertyValue);
        }
        return numProperties;
    }

    public static String getPropertiesString(long num) {
        StringBuilder propertiesStringBuilder = new StringBuilder();

        Map<String, Boolean> numProperties = getProperties(num);

        for (String propertyName : numProperties.keySet()) {
            if (numProperties.get(propertyName)) {
                propertiesStringBuilder.append(propertyName).append(", ");
            }
        }

        String result = propertiesStringBuilder.toString();
        return result.substring(0, result.length() - 2);
    }



    private static boolean isAvailable(String propertyName) {
        Property property = properties.getOrDefault(propertyName.toLowerCase(Locale.ROOT), null);
        return property != null;
    }

    private static boolean isNatural(long num) {
        return num > 0;
    }
}
