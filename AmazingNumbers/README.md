# Amazing Numbers
Project that represent different number properties. Contains `AmazingNumber` and `Manager` classes

## AmazingNumber class
It is util class (only static methods).
Public static methods list:
1) `void setProperties(Property[] availableProperties)` - set available properties to be checked for numbers
2) `Boolean getProperty(String propertyName, long num)` - returns if the number has property or not
3) `Map<String, Boolean> getProperties(long num)` - returns all properties of the number as `Map` object (even if they are `false`)
4) `String getPropertiesString(long num)` - returns `String` with all the `true` properties of the number separated by `, `
