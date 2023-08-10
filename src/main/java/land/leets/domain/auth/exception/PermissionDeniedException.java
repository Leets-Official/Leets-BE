package land.leets.domain.auth.exception;


import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class PermissionDeniedException extends ServiceException {
    public PermissionDeniedException() {
        super(ErrorCode.PERMISSION_DENIED);
    }
}
