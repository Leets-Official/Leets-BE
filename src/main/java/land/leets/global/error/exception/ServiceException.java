package land.leets.global.error.exception;

import land.leets.global.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends RuntimeException {
    
    private final ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus httpStatusCode() {
        return HttpStatus.valueOf(errorCode.getHttpStatus());
    }
}
