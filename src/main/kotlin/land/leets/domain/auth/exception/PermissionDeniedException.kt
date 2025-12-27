package land.leets.domain.auth.exception

import land.leets.global.error.ErrorCode
import land.leets.global.error.exception.ServiceException

class PermissionDeniedException : ServiceException(ErrorCode.PERMISSION_DENIED)
