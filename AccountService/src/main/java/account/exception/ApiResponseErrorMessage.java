package account.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseErrorMessage {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public static ApiResponseErrorMessage generate(HttpStatus httpStatus, Throwable e, HttpServletRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();
        int status = httpStatus.value();
        String error = statusToErrorStringMap.get(httpStatus);
        String message = e.getMessage();
        String path = request.getRequestURI();

        return new ApiResponseErrorMessage(timestamp, status, error, message, path);
    }

    private static final Map<HttpStatus, String> statusToErrorStringMap = Map.of(
            HttpStatus.BAD_REQUEST, "Bad Request"
    );
}
