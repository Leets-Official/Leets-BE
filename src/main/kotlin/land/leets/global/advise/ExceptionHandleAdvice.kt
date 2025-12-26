package land.leets.global.advise

import land.leets.global.error.ErrorCode
import land.leets.global.error.ErrorResponse
import land.leets.global.error.exception.ServiceException
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestCookieException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors

@RestControllerAdvice
class ExceptionHandleAdvice {
    @ExceptionHandler(ServiceException::class)
    fun handleServiceException(ex: ServiceException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse.from(ex.errorCode)
        return ResponseEntity.status(ex.httpStatusCode()).body(response)
    }

    @ExceptionHandler(MissingRequestCookieException::class)
    fun handleMissingRequestCookieException(ex: MissingRequestCookieException): ResponseEntity<ErrorResponse> {
        val response = ErrorResponse.from(ErrorCode.COOKIE_NOT_FOUND)
        return ResponseEntity.status(ex.statusCode).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        ex.printStackTrace()
        val response = ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR)
        return ResponseEntity.internalServerError().body(response)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map { obj: FieldError? -> obj!!.getField() }
            .collect(Collectors.joining(", "))

        val customMessage = String.format(ErrorCode.INVALID_REQUEST_BODY.message, fieldErrors)
        val response = ErrorResponse.of(ErrorCode.INVALID_REQUEST_BODY, customMessage)

        return ResponseEntity.status(ErrorCode.INVALID_REQUEST_BODY.httpStatus).body(response)
    }
}
