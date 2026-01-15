package land.leets.domain.temporaryApplication.presentation.dto

import land.leets.domain.application.type.Position
import land.leets.domain.temporaryApplication.domain.TemporaryApplication

data class TemporaryApplicationResponse(
    val name: String?,
    val phone: String?,
    val major: String?,
    val grade: String?,
    val project: String?,
    val algorithm: String?,
    val portfolio: String?,
    val position: Position,
    val career: String?,
    val interviewDay: String?,
    val interviewTime: String?,
    val motive: String?,
    val expectation: String?,
    val capability: String?,
    val conflict: String?,
    val passion: String?
) {
    companion object {
        fun from(temporaryApplication: TemporaryApplication): TemporaryApplicationResponse {
            return TemporaryApplicationResponse(
                name = temporaryApplication.name,
                phone = temporaryApplication.phone,
                major = temporaryApplication.major,
                grade = temporaryApplication.grade,
                project = temporaryApplication.project,
                algorithm = temporaryApplication.algorithm,
                portfolio = temporaryApplication.portfolio,
                position = temporaryApplication.position,
                career = temporaryApplication.career,
                interviewDay = temporaryApplication.interviewDay,
                interviewTime = temporaryApplication.interviewTime,
                motive = temporaryApplication.motive,
                expectation = temporaryApplication.expectation,
                capability = temporaryApplication.capability,
                conflict = temporaryApplication.conflict,
                passion = temporaryApplication.passion
            )
        }
    }
}
