package land.leets.domain.interview.domain

import jakarta.persistence.*
import land.leets.domain.application.domain.Application
import land.leets.domain.interview.type.HasInterview
import land.leets.domain.shared.BaseTimeEntity
import java.time.LocalDateTime

@Entity(name = "interviews")
class Interview(
    @OneToOne
    val application: Application,

    @Column(columnDefinition = "char(10)")
    @Enumerated(EnumType.STRING)
    var hasInterview: HasInterview = HasInterview.PENDING,

    @Column(nullable = false)
    var fixedInterviewDate: LocalDateTime,

    @Column(nullable = false)
    var place: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
) : BaseTimeEntity()
