package land.leets.domain.user.domain.repository

import land.leets.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findBySub(sub: String): User?
    fun findByEmail(email: String): User?
}
