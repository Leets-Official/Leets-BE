package land.leets.domain.user.domain

import jakarta.persistence.*
import land.leets.domain.shared.BaseTimeEntity
import java.util.UUID

@Entity(name = "users")
class User(
    @Column
    var sid: String?,

    @Column(nullable = false)
    val name: String,

    @Column
    var phone: String?,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false)
    val sub: String,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    val uid: UUID? = null
) : BaseTimeEntity() {

    fun updateUserInfo(sid: String, phone: String) {
        this.sid = sid
        this.phone = phone
    }
}
