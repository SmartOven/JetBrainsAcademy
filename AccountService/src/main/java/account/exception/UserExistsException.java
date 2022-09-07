package account.exception;

public class UserExistsException extends RuntimeException {
    private static final String defaultErrorMessage = "User exists!";

    public UserExistsException() {
        super(defaultErrorMessage);
    }
}
