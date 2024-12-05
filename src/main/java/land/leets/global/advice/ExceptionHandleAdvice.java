package land.leets.global.advice;

import land.leets.global.error.ErrorCode;
import land.leets.global.error.ErrorResponse;
import land.leets.global.error.exception.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandleAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        return ResponseEntity.status(ex.httpStatusCode()).body(response);
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public ResponseEntity<ErrorResponse> handleMissingRequestCookieException(MissingRequestCookieException ex) {
        ErrorResponse response = new ErrorResponse(ErrorCode.COOKIE_NOT_FOUND);
        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));

        String customMessage = String.format(ErrorCode.INVALID_REQUEST_BODY.getMessage(), fieldErrors);
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_REQUEST_BODY, customMessage);

        return ResponseEntity.status(ErrorCode.INVALID_REQUEST_BODY.getHttpStatus()).body(response);
    }
}
