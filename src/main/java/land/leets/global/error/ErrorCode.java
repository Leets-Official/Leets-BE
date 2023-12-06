package land.leets.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    PATCH_REQUEST_FAIL(404, "PATCH_REQUEST_FAIL", "PATCH 요청에 실패했습니다."),
    INTERVIEW_NOT_FOUND(404, "INTERVIEW_NOT_FOUND", "면접 정보를 찾을 수 없습니다."),
    APPLICATION_ALREADY_EXISTS(409, "APPLICATION_ALREADY_EXISTS", "이미 지원한 지원자입니다."),
    PERMISSION_DENIED(403, "PERMISSION_DENIED", "권한이 없습니다."),
    APPLICATION_NOT_FOUND(404, "APPLICATION_NOT_FOUND", "신청서를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(403, "PASSWORD_NOT_MATCH", "비밀번호가 일치하지 않습니다."),
    ADMIN_NOT_FOUND(404, "ADMIN_NOT_FOUND", "관리자를 찾을 수 없습니다."),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "유저를 찾을 수 없습니다."),
    EXPIRED_TOKEN(403, "EXPIRED_TOKEN", "만료된 토큰입니다."),
    INVALID_TOKEN(401, "INVALID_TOKEN", "유효하지 않은 토큰입니다."),
    COOKIE_NOT_FOUND(400, "COOKIE_NOT_FOUND", "쿠키를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR", "서버 오류가 발생했습니다.");

    private final int httpStatus;
    private final String code;
    private final String message;
}
