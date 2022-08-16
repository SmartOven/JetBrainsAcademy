package tracker.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator extends Validator {

    private String email;

    public EmailValidator(String emailString) {
        validate(emailString);
    }

    @Override
    protected void validate(String emailString) {
        valid = false;

        Matcher emailMatcher = validEmailPattern.matcher(emailString);
        if (emailMatcher.matches()) {
            valid = true;
            email = emailMatcher.group();
        }
    }

    public String getEmail() {
        ifNotValidThrow("Given email is not valid");
        return email;
    }

    // Static
    private static final Pattern validEmailPattern;

    static {
        validEmailPattern = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9]+[A-Z0-9.-]*\\.[A-Z0-9]{1,8}", Pattern.CASE_INSENSITIVE);
    }
}
