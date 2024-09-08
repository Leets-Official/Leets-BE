package land.leets.domain.application.exception;

import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class ApplicationNotFoundException extends ServiceException {
    public ApplicationNotFoundException() {
        super(ErrorCode.APPLICATION_NOT_FOUND);
    }
}
