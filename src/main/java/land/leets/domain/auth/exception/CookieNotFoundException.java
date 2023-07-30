package land.leets.domain.auth.exception;


import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class CookieNotFoundException extends ServiceException {
    public CookieNotFoundException() {
        super(ErrorCode.COOKIE_NOT_FOUND);
    }
}
