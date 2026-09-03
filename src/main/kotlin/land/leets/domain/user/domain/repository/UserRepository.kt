package land.leets.domain.user.domain.repository

import jakarta.persistence.LockModeType
import land.leets.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findBySub(sub: String): User?
    fun findByEmail(email: String): User?

    /**
     * 유저 행에 쓰기 잠금을 건다.
     *
     * 지원서 제출은 "기존 지원서 조회 -> 없으면 저장" 순서라, 같은 사람의 요청이
     * 동시에 들어오면 둘 다 조회 단계에서 null 을 보고 각각 저장해 중복 행이 생긴다.
     * 제출 처리 시작 시점에 유저 행을 잠가 같은 사람의 요청을 직렬화한다.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from users u where u.id = :id")
    fun findByIdForUpdate(@Param("id") id: UUID): User?
}
