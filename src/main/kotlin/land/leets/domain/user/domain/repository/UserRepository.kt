package land.leets.domain.user.domain.repository

import land.leets.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findBySub(sub: String): Optional<User>
    fun findByEmail(email: String): Optional<User>
    fun findByUid(uid: UUID): Optional<User>
}
