package land.leets.domain.interview.exception

import land.leets.global.error.ErrorCode
import land.leets.global.error.exception.ServiceException

class InterviewNotFoundException : ServiceException(ErrorCode.INTERVIEW_NOT_FOUND)
