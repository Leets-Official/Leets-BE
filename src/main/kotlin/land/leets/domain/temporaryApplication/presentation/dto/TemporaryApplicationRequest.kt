package land.leets.domain.temporaryApplication.presentation.dto

import jakarta.validation.constraints.NotNull
import land.leets.domain.application.type.Position

data class TemporaryApplicationRequest(
    val name: String?,
    val sid: String?,
    val phone: String?,
    val major: String?,
    val grade: String?,
    val project: String?,
    val algorithm: String?,
    val portfolio: String?,
    @field:NotNull(message = "지원 포지션은 필수입니다.")
    val position: Position,
    val career: String?,
    val interviewDay: String?,
    val interviewTime: String?,
    val motive: String?,
    val expectation: String?,
    val capability: String?,
    val conflict: String?,
    val passion: String?
)
