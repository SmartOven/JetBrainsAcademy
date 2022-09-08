package account.exception;

public class UserExistsException extends RuntimeException {
    private static final String defaultErrorMessage = "User exist!";

    public UserExistsException() {
        super(defaultErrorMessage);
    }
}
