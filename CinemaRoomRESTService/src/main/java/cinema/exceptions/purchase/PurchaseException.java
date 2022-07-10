package cinema.exceptions.purchase;

public class PurchaseException extends RuntimeException {
    private String error;

    public PurchaseException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
