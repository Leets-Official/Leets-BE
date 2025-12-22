package land.leets.domain.user.exception

import land.leets.global.error.ErrorCode
import land.leets.global.error.exception.ServiceException

class UserNotFoundException : ServiceException(ErrorCode.USER_NOT_FOUND)
