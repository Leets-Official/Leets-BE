package land.leets.domain.application.domain

import jakarta.persistence.*
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.application.type.ApplicationStatus
import land.leets.domain.application.type.Position
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.shared.BaseTimeEntity
import land.leets.domain.user.domain.User
import java.time.LocalDateTime

@Entity(name = "applications")
class Application(
    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var major: String,

    @Column(nullable = false)
    var grade: String,

    @Column
    var project: String? = null,

    @Column
    var algorithm: String? = null,

    @Column
    var portfolio: String? = null,

    @Column(nullable = false, columnDefinition = "char(10)")
    @Enumerated(EnumType.STRING)
    var position: Position,

    @Column
    var career: String? = null,

    @Column(nullable = false)
    var interviewDay: String,

    @Column(nullable = false)
    var interviewTime: String,

    @Column(nullable = false, columnDefinition = "text(600)")
    var motive: String,

    @Column(nullable = false, columnDefinition = "text(600)")
    var expectation: String,

    @Column(nullable = false, columnDefinition = "text(600)")
    var capability: String,

    @Column(nullable = false, columnDefinition = "text(600)")
    var conflict: String,

    @Column(nullable = false, columnDefinition = "text(600)")
    var passion: String,

    @Column
    var appliedAt: LocalDateTime? = null,

    @Column(columnDefinition = "char(10)")
    @Enumerated(EnumType.STRING)
    var applicationStatus: ApplicationStatus = ApplicationStatus.PENDING,

    @Column(columnDefinition = "char(10)")
    @Enumerated(EnumType.STRING)
    var submitStatus: SubmitStatus,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity() {
    fun updateInfo(appliedAt: LocalDateTime) {
        this.appliedAt = appliedAt
    }

    fun updateApplicationStatus(applicationStatus: ApplicationStatus) {
        this.applicationStatus = applicationStatus
    }

    fun updateContent(request: ApplicationRequest) {
        this.name = request.name
        this.major = request.major
        this.grade = request.grade
        this.project = request.project
        this.algorithm = request.algorithm
        this.portfolio = request.portfolio
        this.position = request.position
        this.career = request.career
        this.interviewDay = request.interviewDay
        this.interviewTime = request.interviewTime
        this.motive = request.motive
        this.expectation = request.expectation
        this.capability = request.capability
        this.conflict = request.conflict
        this.passion = request.passion
        this.submitStatus = request.submitStatus
    }
}
