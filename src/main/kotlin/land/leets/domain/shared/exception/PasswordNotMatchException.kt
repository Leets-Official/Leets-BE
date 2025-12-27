package land.leets.domain.shared.exception

import land.leets.global.error.ErrorCode
import land.leets.global.error.exception.ServiceException

class PasswordNotMatchException : ServiceException(ErrorCode.PASSWORD_NOT_MATCH)
