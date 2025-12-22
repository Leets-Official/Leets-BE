package land.leets.domain.user.presentation.dto

import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.user.domain.User
import java.util.UUID

data class UserDetailsResponse(
    val uid: UUID?,
    val sid: String?,
    val name: String,
    val phone: String?,
    val email: String,
    val major: String?,
    val submitStatus: SubmitStatus
) {
    companion object {
        fun of(user: User, submitStatus: SubmitStatus): UserDetailsResponse {
            return UserDetailsResponse(
                uid = user.id,
                sid = user.sid,
                name = user.name,
                phone = user.phone,
                email = user.email,
                major = null,
                submitStatus = submitStatus
            )
        }
    }
}
