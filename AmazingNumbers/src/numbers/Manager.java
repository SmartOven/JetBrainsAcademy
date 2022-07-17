package numbers;

import numbers.property.*;

import java.util.*;

public class Manager {
    private final Scanner console;
    private static final Set<String> availablePropertiesNamesSet;
    private static final String availablePropertiesNamesString;

    static {
        availablePropertiesNamesString = "EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD";
        availablePropertiesNamesSet = new HashSet<>(List.of(
                availablePropertiesNamesString.split(", ")
        ));
        Property[] availableProperties = new Property[] {
                new Buzz(), new Duck(), new Palindromic(), new Gapful(), new Spy(), new Square(), new Sunny(), new Even(), new Odd(), new Jumping(), new Happy(), new Sad()
        };

        AmazingNumber.setProperties(availableProperties);
    }

    public Manager(Scanner console) {
        this.console = console;
    }

    public void work() {
        // Sending Welcome message
        System.out.println("Welcome to Amazing Numbers!");

        // Sending info
        System.out.println(
                """
                        Supported requests:
                        - enter a natural number to know its properties;
                        - enter two natural numbers to obtain the properties of the list:
                          * the first parameter represents a starting number;
                          * the second parameter shows how many consecutive numbers are to be printed;
                        - two natural numbers and properties to search for;
                        - a property preceded by minus must not be present in numbers;
                        - separate the parameters with one space;
                        - enter 0 to exit."""
        );

        // Working until 0 got as input
        String input;
        long firstNum;
        int range;
        List<String> properties;
        while (!(input = getUserInput()).equals("0")) {
            String[] parameters = input.split(" ");

            // Wrong type of firstNum
            if ((firstNum = resolveFirstNum(parameters[0])) == -1) {
                continue;
            }

            // Get number info
            if (parameters.length == 1) {
                System.out.print(getNumInfo(firstNum));
                continue;
            }

            // Wrong type of range
            if ((range = resolveRange(parameters[1])) == -1) {
                continue;
            }

            // Get range info
            if (parameters.length == 2) {
                System.out.println(getRangeInfo(firstNum, range));
                continue;
            }

            // Wrong property type
            if ((properties = resolveProperties(parameters)) == null) {
                continue;
            }

            // Get numbers by properties
            System.out.println(getNumbersByProperties(firstNum, range, properties));
        }

        System.out.println("Goodbye!");
    }

    private long resolveFirstNum(String parameter) {
        long firstNum;
        try {
            firstNum = Long.parseLong(parameter);
            if (firstNum < 0) {
                System.out.println("The first parameter should be a natural number or zero.");
                return -1;
            }
            return firstNum;
        } catch (NumberFormatException e) {
            System.out.println("The first parameter should be a natural number or zero.");
            return -1;
        }
    }

    private int resolveRange(String parameter) {
        int range;
        try {
            range = Integer.parseInt(parameter);
            if (range < 0) {
                System.out.println("The second parameter should be a natural number or zero.");
                return -1;
            }
            return range;
        } catch (NumberFormatException e) {
            System.out.println("The second parameter should be a natural number or zero.");
            return -1;
        }
    }

    private List<String> resolveProperties(String[] parameters) {
        List<String> properties = new LinkedList<>();
        for (int i = 2; i < parameters.length; i++) {
            properties.add(parameters[i].toLowerCase(Locale.ROOT));
        }
        if (!arePropertiesAvailable(properties)) {
            return null;
        }
        return properties;
    }

    private boolean arePropertiesAvailable(List<String> properties) {
        // Check unavailable properties
        StringBuilder unavailablePropertiesBuilder = new StringBuilder();
        int unavailablePropertiesCount = 0;
        for (String propertyName: properties) {
            propertyName = propertyName.toUpperCase(Locale.ROOT);
            if (propertyName.charAt(0) == '-') {
                propertyName = propertyName.substring(1);
            }
            if (!availablePropertiesNamesSet.contains(propertyName)) {
                unavailablePropertiesBuilder.append(propertyName).append(", ");
                unavailablePropertiesCount++;
            }
        }
        if (unavailablePropertiesCount > 0) {
            String unavailableProperties = unavailablePropertiesBuilder.toString();
            unavailableProperties = unavailableProperties.substring(0, unavailableProperties.length() - 2);
            if (unavailablePropertiesCount == 1) {
                System.out.println("The property [" + unavailableProperties + "] is wrong.\n" +
                        "Available properties: [" + availablePropertiesNamesString + "]");
            } else {
                System.out.println("The properties [" + unavailableProperties + "] are wrong.\n" +
                        "Available properties: [" + availablePropertiesNamesString + "]");
            }
            return false;
        }


        // Check conflicting properties
        Set<String> propertiesSet = new HashSet<>(properties);

        for (String propertyName : propertiesSet) {
            if (propertyName.charAt(0) != '-' && propertiesSet.contains("-" + propertyName)) {
                propertyName = propertyName.toUpperCase(Locale.ROOT);
                System.out.println("The request contains mutually exclusive properties: [" + propertyName + ", -" + propertyName + "]\n" +
                        "There are no numbers with these properties.");
                return false;
            }
        }

        if (propertiesSet.contains("even") && propertiesSet.contains("odd")) {
            System.out.println("The request contains mutually exclusive properties: [ODD, EVEN]\n" +
                    "There are no numbers with these properties.");
            return false;
        }
        if (propertiesSet.contains("sunny") && propertiesSet.contains("square")) {
            System.out.println("The request contains mutually exclusive properties: [SUNNY, SQUARE]\n" +
                    "There are no numbers with these properties.");
            return false;
        }
        if (propertiesSet.contains("spy") && propertiesSet.contains("duck")) {
            System.out.println("The request contains mutually exclusive properties: [SPY, DUCK]\n" +
                    "There are no numbers with these properties.");
            return false;
        }
        if (propertiesSet.contains("happy") && propertiesSet.contains("sad")) {
            System.out.println("The request contains mutually exclusive properties: [HAPPY, SAD]\n" +
                    "There are no numbers with these properties.");
            return false;
        }

        if (propertiesSet.contains("-even") && propertiesSet.contains("-odd")) {
            System.out.println("The request contains mutually exclusive properties: [-ODD, -EVEN]\n" +
                    "There are no numbers with these properties.");
            return false;
        }
        if (propertiesSet.contains("-spy") && propertiesSet.contains("-duck")) {
            System.out.println("The request contains mutually exclusive properties: [-SPY, -DUCK]\n" +
                    "There are no numbers with these properties.");
            return false;
        }
        if (propertiesSet.contains("-happy") && propertiesSet.contains("-sad")) {
            System.out.println("The request contains mutually exclusive properties: [-HAPPY, -SAD]\n" +
                    "There are no numbers with these properties.");
            return false;
        }

        return true;
    }

    private void appendNumProperties(StringBuilder info, long num) {
        info.append(" ".repeat(13))
                .append(num).append(" is ")
                .append(AmazingNumber.getPropertiesString(num)).append("\n");
    }

    private String getUserInput() {
        System.out.print("Enter a request: ");
        return console.nextLine();
    }

    private String getNumbersByProperties(long firstNum, int count, List<String> propertyNames) {
        StringBuilder info = new StringBuilder();

        for (long num = firstNum; count > 0; num++) {
            boolean hasAllProperties = true;

            // Check if the number has all required properties
            for (String propertyName: propertyNames) {
                // Check if the number has wrong property
                if (propertyName.charAt(0) == '-') {
                    if (!AmazingNumber.getProperty(propertyName.substring(1), num)) {
                        continue;
                    }

                    hasAllProperties = false;
                    break;
                }

                boolean hasProperty = AmazingNumber.getProperty(propertyName, num);
                if (hasProperty) {
                    continue;
                }

                hasAllProperties = false;
                break;
            }

            // If not -> check next num
            if (!hasAllProperties) {
                continue;
            }

            appendNumProperties(info, num);

            count--;
        }

        return info.toString();
    }

    private String getRangeInfo(long firstNum, int range) {
        StringBuilder info = new StringBuilder();
        for (long num = firstNum; num < firstNum + range; num++) {
            appendNumProperties(info, num);
        }
        return info.toString();
    }

    private String getNumInfo(long num) {
        StringBuilder properties = new StringBuilder();
        properties.append("Properties of ").append(num).append("\n");

        Map<String, Boolean> numProperties = AmazingNumber.getProperties(num);
        for (String propertyName : numProperties.keySet()) {
            boolean propertyValue = numProperties.get(propertyName);
            properties.append(propertyName).append(": ").append(propertyValue).append("\n");
        }

        return properties.toString();
    }
}
