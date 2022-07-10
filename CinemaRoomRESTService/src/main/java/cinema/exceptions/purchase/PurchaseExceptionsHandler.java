package cinema.exceptions.purchase;

import cinema.pojo.info.PurchaseExceptionInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PurchaseExceptionsHandler {
    @ExceptionHandler(PurchaseException.class)
    public final ResponseEntity<PurchaseExceptionInfo> handleTicketAlreadyPurchasedException(PurchaseException e) {
        PurchaseExceptionInfo error = new PurchaseExceptionInfo(e.getError());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
