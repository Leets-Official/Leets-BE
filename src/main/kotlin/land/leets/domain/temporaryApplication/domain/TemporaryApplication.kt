package land.leets.domain.temporaryApplication.domain

import jakarta.persistence.*
import land.leets.domain.application.type.Position
import land.leets.domain.shared.BaseTimeEntity
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationRequest
import land.leets.domain.user.domain.User
import land.leets.global.util.TextSanitizer

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

    /**
     * 임시 저장은 정의상 부분 데이터라 필수 값 검증을 걸지 않는다.
     * 대신 값이 실제로 들어온 항목만 갱신해, 아직 입력하지 않은 단계의 빈 값이
     * 이미 저장된 내용을 지우지 않도록 한다.
     */
    fun updateContent(request: TemporaryApplicationRequest) {
        name = TextSanitizer.clean(request.name) ?: name
        phone = TextSanitizer.clean(request.phone) ?: phone
        major = TextSanitizer.clean(request.major) ?: major
        grade = TextSanitizer.clean(request.grade) ?: grade
        project = TextSanitizer.clean(request.project) ?: project
        algorithm = TextSanitizer.clean(request.algorithm) ?: algorithm
        portfolio = TextSanitizer.clean(request.portfolio) ?: portfolio
        position = request.position
        career = TextSanitizer.clean(request.career) ?: career
        interviewDay = TextSanitizer.clean(request.interviewDay) ?: interviewDay
        interviewTime = TextSanitizer.clean(request.interviewTime) ?: interviewTime
        motive = TextSanitizer.clean(request.motive) ?: motive
        expectation = TextSanitizer.clean(request.expectation) ?: expectation
        capability = TextSanitizer.clean(request.capability) ?: capability
        conflict = TextSanitizer.clean(request.conflict) ?: conflict
        passion = TextSanitizer.clean(request.passion) ?: passion
    }

    companion object {
        fun of(user: User, request: TemporaryApplicationRequest): TemporaryApplication {
            return TemporaryApplication(
                user = user,
                name = TextSanitizer.clean(request.name),
                phone = TextSanitizer.clean(request.phone),
                major = TextSanitizer.clean(request.major),
                grade = TextSanitizer.clean(request.grade),
                project = TextSanitizer.clean(request.project),
                algorithm = TextSanitizer.clean(request.algorithm),
                portfolio = TextSanitizer.clean(request.portfolio),
                position = request.position,
                career = TextSanitizer.clean(request.career),
                interviewDay = TextSanitizer.clean(request.interviewDay),
                interviewTime = TextSanitizer.clean(request.interviewTime),
                motive = TextSanitizer.clean(request.motive),
                expectation = TextSanitizer.clean(request.expectation),
                capability = TextSanitizer.clean(request.capability),
                conflict = TextSanitizer.clean(request.conflict),
                passion = TextSanitizer.clean(request.passion)
            )
        }
    }
}
