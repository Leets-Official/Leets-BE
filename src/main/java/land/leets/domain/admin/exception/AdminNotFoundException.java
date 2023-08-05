package land.leets.domain.admin.exception;

import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class AdminNotFoundException extends ServiceException {
    public AdminNotFoundException() {
        super(ErrorCode.ADMIN_NOT_FOUND);
    }
}
