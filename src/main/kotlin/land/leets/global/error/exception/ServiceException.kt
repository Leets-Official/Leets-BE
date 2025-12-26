package land.leets.global.error.exception

import land.leets.global.error.ErrorCode
import lombok.Getter
import org.springframework.http.HttpStatus

@Getter
open class ServiceException(
    val errorCode: ErrorCode
) : RuntimeException() {
    fun httpStatusCode(): HttpStatus {
        return HttpStatus.valueOf(errorCode.httpStatus)
    }
}
