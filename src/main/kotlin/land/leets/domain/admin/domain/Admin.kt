package land.leets.domain.admin.domain

import jakarta.persistence.*
import land.leets.domain.shared.BaseTimeEntity
import java.util.UUID

@Entity(name = "admins")
class Admin(
    @Column(nullable = false)
    val id: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val email: String,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    val uid: UUID? = null
) : BaseTimeEntity()