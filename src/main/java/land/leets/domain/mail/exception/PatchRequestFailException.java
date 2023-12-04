package land.leets.domain.mail.exception;

import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class PatchRequestFailException extends ServiceException {
    public PatchRequestFailException() {
        super(ErrorCode.PATCH_REQUEST_FAIL);
    }
}
