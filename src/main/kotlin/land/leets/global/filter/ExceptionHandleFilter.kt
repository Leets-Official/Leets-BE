package land.leets.global.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import land.leets.global.error.ErrorCode
import land.leets.global.error.ErrorResponse
import land.leets.global.error.exception.ServiceException
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class ExceptionHandleFilter : OncePerRequestFilter() {

    @Throws(IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        runCatching {
            filterChain.doFilter(request, response)
        }.onFailure { exception ->
            val errorCode = when (exception) {
                is ServiceException -> exception.errorCode
                else -> ErrorCode.INTERNAL_SERVER_ERROR
            }
            sendErrorResponse(response, errorCode)
        }
    }

    @Throws(IOException::class)
    private fun sendErrorResponse(response: HttpServletResponse, errorCode: ErrorCode) {
        response.apply {
            status = errorCode.httpStatus
            characterEncoding = "UTF-8"
            contentType = "application/json"

            val errorResponse = ErrorResponse.from(errorCode)
            val result = mapOf("result" to errorResponse)

            writer.write(ObjectMapper().writeValueAsString(result))
        }
    }
}
