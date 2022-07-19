package cinema.exceptions;

import cinema.pojo.info.CustomRequestExceptionInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RequestExceptionHandler {
    @ExceptionHandler(CustomRequestException.class)
    public final ResponseEntity<CustomRequestExceptionInfo> handleTicketAlreadyPurchasedException(CustomRequestException e) {
        CustomRequestExceptionInfo error = new CustomRequestExceptionInfo(e.getError());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CustomRequestExceptionInfo> handleMissingParams(MissingServletRequestParameterException e) {
        CustomRequestExceptionInfo error = new CustomRequestExceptionInfo("The password is wrong!");
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
