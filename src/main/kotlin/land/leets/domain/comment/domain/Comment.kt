package land.leets.domain.comment.domain

import jakarta.persistence.*
import land.leets.domain.admin.domain.Admin
import land.leets.domain.shared.BaseTimeEntity

@Entity(name = "comments")
class Comment(
    @Column(nullable = false)
    val applicationId: Long,

    @Column(nullable = false)
    val content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    val admin: Admin,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseTimeEntity()
