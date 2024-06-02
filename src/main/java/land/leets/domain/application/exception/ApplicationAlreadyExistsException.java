package land.leets.domain.application.exception;

import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class ApplicationAlreadyExistsException extends ServiceException {
    public ApplicationAlreadyExistsException() {
        super(ErrorCode.APPLICATION_ALREADY_EXISTS);
    }
}
