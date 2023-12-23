package land.leets.domain.interview.exception;

import land.leets.global.error.ErrorCode;
import land.leets.global.error.exception.ServiceException;

public class InterviewNotFoundException extends ServiceException {
    public InterviewNotFoundException() {
        super(ErrorCode.INTERVIEW_NOT_FOUND);
    }
}
