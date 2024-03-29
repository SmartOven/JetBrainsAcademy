package account.exception.handler;

import account.exception.ApiResponseErrorMessage;
import account.exception.DataManagementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {
            DataManagementException.class,
            IllegalArgumentException.class,
            UnsupportedOperationException.class
    })
    public ResponseEntity<ApiResponseErrorMessage> handleUserExistsException(
            Exception e,
            HttpServletRequest request) {

        ApiResponseErrorMessage body = ApiResponseErrorMessage.generate(
                HttpStatus.BAD_REQUEST, e, request
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(value = {
            NoSuchElementException.class
    })
    public ResponseEntity<ApiResponseErrorMessage> handleNoSuchElementException(
            Exception e,
            HttpServletRequest request) {

        ApiResponseErrorMessage body = ApiResponseErrorMessage.generate(
                HttpStatus.NOT_FOUND, e, request
        );
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
