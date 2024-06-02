package land.leets.global.error.exception;

import land.leets.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    
    private final ErrorCode errorCode;

    public HttpStatus httpStatusCode() {
        return HttpStatus.valueOf(errorCode.getHttpStatus());
    }
}
