package land.leets.domain.shared.exception;


import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class PasswordNotMatchException extends ServiceException {
    public PasswordNotMatchException() {
        super(ErrorCode.PASSWORD_NOT_MATCH);
    }
}
