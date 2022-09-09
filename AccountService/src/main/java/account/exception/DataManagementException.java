package account.exception;

import org.springframework.validation.BindingResult;

/**
 * Override `getDefaultErrorMessage()` to set custom default message
 */
public class DataManagementException extends RuntimeException {
    public DataManagementException() {
        super("Something went wrong during data management");
    }

    public DataManagementException(String message) {
        super(message);
    }

    public DataManagementException(BindingResult errors) {
        super(getErrorMessagesAsString(errors));
    }

    static String getErrorMessagesAsString(BindingResult errors) {
        StringBuilder errorsString = new StringBuilder();
        errors.getAllErrors().forEach(e -> errorsString.append(e.getDefaultMessage()));
        return errorsString.toString();
    }
}
