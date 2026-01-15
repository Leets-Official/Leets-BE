package land.leets.domain.temporaryApplication.domain

import jakarta.persistence.*
import land.leets.domain.application.type.Position
import land.leets.domain.shared.BaseTimeEntity
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationRequest
import land.leets.domain.user.domain.User

@Entity(name = "temporary_applications")
class TemporaryApplication(

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @Column
    var name: String? = null,

    @Column
    var phone: String? = null,

    @Column
    var major: String? = null,

    @Column
    var grade: String? = null,

    @Column
    var project: String? = null,

    @Column
    var algorithm: String? = null,

    @Column
    var portfolio: String? = null,

    @Column(columnDefinition = "char(10)")
    @Enumerated(EnumType.STRING)
    var position: Position,

    @Column
    var career: String? = null,

    @Column
    var interviewDay: String? = null,

    @Column
    var interviewTime: String? = null,

    @Column(columnDefinition = "text(600)")
    var motive: String? = null,

    @Column(columnDefinition = "text(600)")
    var expectation: String? = null,

    @Column(columnDefinition = "text(600)")
    var capability: String? = null,

    @Column(columnDefinition = "text(600)")
    var conflict: String? = null,

    @Column(columnDefinition = "text(600)")
    var passion: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity() {

    fun updateContent(request: TemporaryApplicationRequest) {
        name = request.name
        phone = request.phone
        major = request.major
        grade = request.grade
        project = request.project
        algorithm = request.algorithm
        portfolio = request.portfolio
        position = request.position
        career = request.career
        interviewDay = request.interviewDay
        interviewTime = request.interviewTime
        motive = request.motive
        expectation = request.expectation
        capability = request.capability
        conflict = request.conflict
        passion = request.passion
    }

    companion object {
        fun of(user: User, request: TemporaryApplicationRequest): TemporaryApplication {
            return TemporaryApplication(
                user = user,
                name = request.name,
                phone = request.phone,
                major = request.major,
                grade = request.grade,
                project = request.project,
                algorithm = request.algorithm,
                portfolio = request.portfolio,
                position = request.position,
                career = request.career,
                interviewDay = request.interviewDay,
                interviewTime = request.interviewTime,
                motive = request.motive,
                expectation = request.expectation,
                capability = request.capability,
                conflict = request.conflict,
                passion = request.passion
            )
        }
    }
}
