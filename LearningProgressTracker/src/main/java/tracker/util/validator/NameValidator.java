package tracker.util.validator;

import tracker.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator implements Validator {

    private final String firstName;
    private final String lastName;
    private final boolean firstNameValid;
    private final boolean lastNameValid;

    public NameValidator(String nameString) {
        // Last whitespaces group start and end indexes
        Integer[] startEnd = StringUtil.findFirstWhitespacesSequence(nameString);

        if (startEnd == null) {
            // Last name doesn't exist
            // Only need to check first name validity
            firstName = validateAndGetFirstName(nameString);
            firstNameValid = (firstName != null);

            lastName = null;
            lastNameValid = false;
        } else {
            String firstNameString = nameString.substring(0, startEnd[0]);
            String lastNameString = nameString.substring(startEnd[1]);

            // Both first and last name exists
            // Check validity of both of them
            firstName = validateAndGetFirstName(firstNameString);
            firstNameValid = (firstName != null);

            lastName = validateAndGetLastName(lastNameString);
            lastNameValid = (lastName != null);
        }
    }

    public NameValidator(String firstNameString, String lastNameString) {
        firstName = validateAndGetFirstName(firstNameString);
        firstNameValid = (firstName != null);

        lastName = validateAndGetLastName(lastNameString);
        lastNameValid = (lastName != null);
    }

    private String validateAndGetFirstName(String firstNameString) {
        Matcher firstNameMatcher = validFirstNamePattern.matcher(firstNameString);
        if (firstNameMatcher.matches()) {
            return firstNameMatcher.group(1);
        }
        return null;
    }

    private String validateAndGetLastName(String lastNameString) {
        Matcher lastNameMatcher = validLastNamePattern.matcher(lastNameString);
        if (lastNameMatcher.matches()) {
            return lastNameMatcher.group(1).substring(1);
        }
        return null;
    }

    public String getFirstName() {
        if (!isValid()) {
            throw new UnsupportedOperationException("Given name is not valid");
        }
        return firstName;
    }

    public String getLastName() {
        if (!isValid()) {
            throw new UnsupportedOperationException("Given name is not valid");
        }
        return lastName;
    }

    public boolean isFirstNameValid() {
        return firstNameValid;
    }

    public boolean isLastNameValid() {
        return lastNameValid;
    }

    @Override
    public boolean isValid() {
        return firstNameValid && lastNameValid;
    }

    // Static
    private static final Pattern validFirstNamePattern;
    private static final Pattern validLastNamePattern;
    static {
        validFirstNamePattern = Pattern.compile("([A-Z](?:[-']?[A-Z]+)+)", Pattern.CASE_INSENSITIVE);
        validLastNamePattern = Pattern.compile("((?:\\s[A-Z](?:[-']?[A-Z]+)+)+)", Pattern.CASE_INSENSITIVE);
    }
}
