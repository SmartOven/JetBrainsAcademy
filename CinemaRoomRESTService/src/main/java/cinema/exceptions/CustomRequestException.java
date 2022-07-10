package cinema.exceptions;

public class CustomRequestException extends RuntimeException {
    private String error;

    public CustomRequestException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
