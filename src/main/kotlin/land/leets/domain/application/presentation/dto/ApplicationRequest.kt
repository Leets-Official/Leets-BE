package land.leets.domain.application.presentation.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import land.leets.domain.application.type.Position
import land.leets.domain.application.type.SubmitStatus

data class ApplicationRequest(
    @field:NotBlank(message = "이름은 필수입니다.")
    val name: String,
    // 프론트 지원서 폼에서 학번을 입력받지 않아 항상 빈 문자열로 들어온다. 검증 대상이 아니다.
    val sid: String?,
    @field:NotBlank(message = "전화번호는 필수입니다.")
    val phone: String,
    @field:NotBlank(message = "전공은 필수입니다.")
    val major: String,
    @field:NotBlank(message = "학년은 필수입니다.")
    val grade: String,
    val project: String?,
    val algorithm: String?,
    val portfolio: String?,
    @field:NotNull(message = "지원 포지션은 필수입니다.")
    val position: Position,
    val career: String?,
    @field:NotBlank(message = "면접 가능 날짜는 필수입니다.")
    val interviewDay: String,
    @field:NotBlank(message = "면접 가능 시간은 필수입니다.")
    val interviewTime: String,
    @field:NotBlank(message = "지원 동기는 필수입니다.")
    val motive: String,
    @field:NotBlank(message = "기대치/희망 분야는 필수입니다.")
    val expectation: String,
    @field:NotBlank(message = "역량 소개는 필수입니다.")
    val capability: String,
    @field:NotBlank(message = "갈등 해결 경험은 필수입니다.")
    val conflict: String,
    @field:NotBlank(message = "열정/포부는 필수입니다.")
    val passion: String,
    @field:NotNull(message = "제출 상태는 필수입니다.")
    val submitStatus: SubmitStatus
)
