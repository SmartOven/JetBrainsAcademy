package tracker.util.validator;

import tracker.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameValidator extends Validator {

    private String firstName;
    private String lastName;
    private boolean firstNameValid;
    private boolean lastNameValid;

    public NameValidator(String nameString) {
        validate(nameString);
        valid = firstNameValid && lastNameValid;
    }

    public NameValidator(String firstNameString, String lastNameString) {
        validateFirstName(firstNameString);
        validateLastName(lastNameString);
        valid = firstNameValid && lastNameValid;
    }

    @Override
    protected void validate(String nameString) {
        // Last whitespaces group start and end indexes
        Integer[] startEnd = StringUtil.findFirstWhitespacesSequence(nameString);

        if (startEnd == null) {
            // Last name doesn't exist
            // Only need to check first name validity
            validateFirstName(nameString);
            lastNameValid = false;
            return;
        }

        String firstNameString = nameString.substring(0, startEnd[0]);
        String lastNameString = nameString.substring(startEnd[1]);

        // Both first and last name exists
        // Check validity of both of them
        validateFirstName(firstNameString);
        validateLastName(lastNameString);
    }

    private void validateFirstName(String firstNameString) {
        Matcher firstNameMatcher = validFirstNamePattern.matcher(firstNameString);
        if (!firstNameMatcher.matches()) {
            firstNameValid = false;
            return;
        }
        firstNameValid = true;
        firstName = firstNameMatcher.group(1);
    }

    private void validateLastName(String lastNameString) {
        Matcher lastNameMatcher = validLastNamePattern.matcher(lastNameString);
        if (!lastNameMatcher.matches()) {
            lastNameValid = false;
            return;
        }
        lastNameValid = true;
        lastName = lastNameMatcher.group(1).substring(1);
    }

    public String getFirstName() {
        ifNotValidThrow("Given name is not valid");
        return firstName;
    }

    public String getLastName() {
        ifNotValidThrow("Given name is not valid");
        return lastName;
    }

    public boolean isFirstNameValid() {
        return firstNameValid;
    }

    public boolean isLastNameValid() {
        return lastNameValid;
    }

    // Static
    private static final Pattern validFirstNamePattern;
    private static final Pattern validLastNamePattern;

    static {
        validFirstNamePattern = Pattern.compile("([A-Z](?:[-']?[A-Z]+)+)", Pattern.CASE_INSENSITIVE);
        validLastNamePattern = Pattern.compile("((?:\\s[A-Z](?:[-']?[A-Z]+)+)+)", Pattern.CASE_INSENSITIVE);
    }
}
