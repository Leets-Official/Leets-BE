package land.leets.domain.user.domain

import jakarta.persistence.*
import land.leets.domain.shared.BaseTimeEntity
import land.leets.global.util.TextSanitizer
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
    val id: UUID? = null
) : BaseTimeEntity() {

    /**
     * 값이 실제로 들어온 항목만 갱신한다.
     * 빈 문자열이나 "null" / "undefined" 로는 기존에 저장된 정보를 지우지 않는다.
     */
    fun updateUserInfo(sid: String?, phone: String?) {
        this.sid = TextSanitizer.clean(sid) ?: this.sid
        this.phone = TextSanitizer.clean(phone) ?: this.phone
    }
}
