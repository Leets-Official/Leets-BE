package land.leets.global.error;

public record BindExceptionResponse(
        int httpStatus,
        String message
) {
}
