package land.leets.global.error.exception

import land.leets.global.error.ErrorCode

class InvalidRequestBodyException(
    val field: String
) : ServiceException(ErrorCode.INVALID_REQUEST_BODY)
