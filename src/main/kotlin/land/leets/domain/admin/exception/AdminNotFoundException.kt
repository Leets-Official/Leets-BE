package land.leets.domain.admin.exception

import land.leets.global.error.ErrorCode
import land.leets.global.error.exception.ServiceException

class AdminNotFoundException : ServiceException(ErrorCode.ADMIN_NOT_FOUND)