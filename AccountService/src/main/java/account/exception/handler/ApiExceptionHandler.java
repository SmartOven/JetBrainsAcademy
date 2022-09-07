package account.exception.handler;

import account.exception.ApiResponseErrorMessage;
import account.exception.UserExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ApiResponseErrorMessage> handleUserExistsException(
            UserExistsException e,
            WebRequest request) {

        ApiResponseErrorMessage body = ApiResponseErrorMessage.generate(
                HttpStatus.BAD_REQUEST, e, request
        );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseErrorMessage> handleIllegalArgumentException(
            IllegalArgumentException e,
            WebRequest request) {

        ApiResponseErrorMessage body = ApiResponseErrorMessage.generate(
                HttpStatus.BAD_REQUEST, e, request
        );
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponseErrorMessage> handleNoSuchElementException(
            NoSuchElementException e,
            WebRequest request) {

        ApiResponseErrorMessage body = ApiResponseErrorMessage.generate(
                HttpStatus.NOT_FOUND, e, request
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
