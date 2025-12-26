package land.leets.global.error

data class ErrorResponse(
    val httpStatus: Int,
    val message: String,
    val code: String
) {

    companion object {
        fun from(errorCode: ErrorCode) =
            ErrorResponse(
                errorCode.httpStatus,
                errorCode.message,
                errorCode.code
            )

        fun of(
            errorCode: ErrorCode,
            customMessage: String
        ) = ErrorResponse(
            errorCode.httpStatus,
            customMessage,
            errorCode.code
        )
    }
}
