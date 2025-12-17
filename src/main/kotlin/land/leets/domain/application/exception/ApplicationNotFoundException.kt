package land.leets.domain.application.exception

import land.leets.global.error.ErrorCode
import land.leets.global.error.exception.ServiceException

class ApplicationNotFoundException : ServiceException(ErrorCode.APPLICATION_NOT_FOUND)
