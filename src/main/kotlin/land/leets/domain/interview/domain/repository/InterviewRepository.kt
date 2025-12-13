package land.leets.domain.interview.domain.repository

import land.leets.domain.application.domain.Application
import land.leets.domain.interview.domain.Interview
import land.leets.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface InterviewRepository : JpaRepository<Interview, Long> {
    fun findByApplication(application: Application): Optional<Interview>
    fun findByApplication_User(user: User): Optional<Interview>
}
