package numbers;

import numbers.property.*;

import java.util.*;

/**
 * Util class that checks properties of the number
 */
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

    // Util class - no instances
    private AmazingNumber() {

    }

    /**
     * Clears properties and fills it with the properties from the given array
     *
     * @param availableProperties array of properties to be added to available
     */
    public static void setProperties(Property[] availableProperties) {
        properties.clear();
        for (Property property : availableProperties) {
            properties.putIfAbsent(property.getName(), property);
        }
    }

    /**
     * Checks if given number has required property
     *
     * @param propertyName name of property to be checked for number
     * @param num          number
     * @return true if number has property, else false
     */
    public static Boolean getProperty(String propertyName, long num) {
        return properties.get(propertyName).getValue(num);
    }

    /**
     * Returns all the properties of the given number as Map object where key = propertyName, value = true/false
     *
     * @param num number
     * @return the Map object with (propertyName, propertyValue) pairs
     */
    public static Map<String, Boolean> getProperties(long num) {
        Map<String, Boolean> numProperties = new HashMap<>();
        for (String propertyName : properties.keySet()) {
            Boolean propertyValue = getProperty(propertyName, num);

            numProperties.put(propertyName, propertyValue);
        }
        return numProperties;
    }

    /**
     * Generate String with propertyNames that has propertyValue = true in the Map of number properties
     *
     * @param num number
     * @return String with number's properties
     */
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
}
