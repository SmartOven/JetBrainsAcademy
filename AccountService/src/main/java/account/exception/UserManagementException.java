package account.exception;

import org.springframework.validation.BindingResult;

public class UserManagementException extends RuntimeException {
    private static final String defaultErrorMessage = "Something went wrong during user management";

    public UserManagementException() {
        super(defaultErrorMessage);
    }

    public UserManagementException(String message) {
        super(message);
    }

    public UserManagementException(BindingResult errors) {
        super(getErrorMessagesAsString(errors));
    }

    static String getErrorMessagesAsString(BindingResult errors) {
        StringBuilder errorsString = new StringBuilder();
        errors.getAllErrors().forEach(e -> errorsString.append(e.getDefaultMessage()));
        return errorsString.toString();
    }
}
