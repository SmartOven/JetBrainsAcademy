package cinema.pojo.info;

public class CustomRequestExceptionInfo {
    private String error;

    public CustomRequestExceptionInfo(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}