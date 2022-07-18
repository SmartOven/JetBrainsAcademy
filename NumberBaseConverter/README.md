# Number Base Converter
Project that contains `ExtendedNumber` class and `Manager` class to work with that through the console

## ExtendedNumber class
How to use:
```java
// Create object
ExtendedNumber number1 = new ExtendedNumber("123");      // integer number, by default base is 10
ExtendedNumber number2 = new ExtendedNumber("123", 8)    // integer number, base = 8
ExtendedNumber number3 = new ExtendedNumber("af.xy", 16) // fractional number, base = 16 (max 36)

// Convert base
ExtendedNumber numberInBase8 = new ExtendedNumber("753.254", 8);
ExtendedNumber sameNumberInBase10 = numberInBase8.convertToBase(10);

// Getting string result (number value)
// toString() returns number value as string
// toString(int radix) converts number to base radix and then calls toString() method
String result1 = new ExtendedNumber("123.45678", 10).toString();   // = 123.45678
String result2 = new ExtendedNumber("123.45678", 10).toString(16); // = 7b.74ef9
```
