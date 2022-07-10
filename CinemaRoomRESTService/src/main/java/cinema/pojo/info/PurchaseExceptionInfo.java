package cinema.pojo.info;

public class PurchaseExceptionInfo {
    private String error;

    public PurchaseExceptionInfo(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}