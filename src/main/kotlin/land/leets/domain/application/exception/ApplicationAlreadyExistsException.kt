package land.leets.domain.application.exception

import land.leets.global.error.ErrorCode
import land.leets.global.error.exception.ServiceException

class ApplicationAlreadyExistsException : ServiceException(ErrorCode.APPLICATION_ALREADY_EXISTS)
