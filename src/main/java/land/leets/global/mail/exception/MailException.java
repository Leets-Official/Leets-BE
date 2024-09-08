package land.leets.global.mail.exception;

import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class MailException extends ServiceException {

    public MailException() {
        super(ErrorCode.MAIL_SEND_FAIL);
    }
}
