package land.leets.domain.application.presentation.dto

import land.leets.domain.application.type.Position
import land.leets.domain.application.type.SubmitStatus

data class ApplicationRequest(
    val name: String,
    val sid: String,
    val phone: String,
    val major: String,
    val grade: String,
    val project: String?,
    val algorithm: String?,
    val portfolio: String?,
    val position: Position,
    val career: String?,
    val interviewDay: String,
    val interviewTime: String,
    val motive: String,
    val expectation: String,
    val capability: String,
    val conflict: String,
    val passion: String,
    val submitStatus: SubmitStatus
)