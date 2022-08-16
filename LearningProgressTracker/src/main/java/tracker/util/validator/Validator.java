package tracker.util.validator;

public abstract class Validator {
    protected boolean valid;

    public boolean isValid() {
        return valid;
    }

    protected void validate(String s) {
        valid = false;
    }

    protected void ifNotValidThrow(String message) {
        throw new UnsupportedOperationException(message);
    }

    protected void ifNotValidThrow() {
        throw new UnsupportedOperationException("Given string is not valid");
    }
}
