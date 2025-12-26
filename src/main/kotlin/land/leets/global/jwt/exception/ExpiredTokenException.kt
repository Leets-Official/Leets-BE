package land.leets.global.jwt.exception

import land.leets.global.error.ErrorCode
import land.leets.global.error.exception.ServiceException

class ExpiredTokenException : ServiceException(ErrorCode.EXPIRED_TOKEN)
