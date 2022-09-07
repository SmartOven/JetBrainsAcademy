package account.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseErrorMessage {
    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;

    public static ApiResponseErrorMessage generate(
            HttpStatus status,
            Throwable e,
            WebRequest request) {

        return new ApiResponseErrorMessage(
                status.value(),
                LocalDateTime.now(),
                e.getMessage(),
                request.getDescription(false)
        );
    }
}
