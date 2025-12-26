package land.leets.global.error.exception

import land.leets.global.error.ErrorCode
import org.springframework.http.HttpStatus

open class ServiceException(
    val errorCode: ErrorCode
) : RuntimeException() {
    fun httpStatusCode(): HttpStatus {
        return HttpStatus.valueOf(errorCode.httpStatus)
    }
}
