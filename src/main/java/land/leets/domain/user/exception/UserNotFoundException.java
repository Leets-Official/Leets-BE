package land.leets.domain.user.exception;

import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class UserNotFoundException extends ServiceException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
