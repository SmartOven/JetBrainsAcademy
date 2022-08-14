package tracker.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Validator {

    private final String email;
    private final boolean valid;

    public EmailValidator(String emailString) {
        Matcher emailMatcher = validEmailPattern.matcher(emailString);
        if (emailMatcher.matches()) {
            valid = true;
            email = emailMatcher.group();
        } else {
            valid = false;
            email = null;
        }
    }

    public String getEmail() {
        if (!valid) {
            throw new UnsupportedOperationException("Given email is not valid");
        }
        return email;
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    // Static
    private static final Pattern validEmailPattern;
    static {
        validEmailPattern = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9]+[A-Z0-9.-]*\\.[A-Z0-9]{1,8}", Pattern.CASE_INSENSITIVE);
    }
}
