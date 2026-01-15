package land.leets.domain.temporaryApplication.domain.repository

import land.leets.domain.temporaryApplication.domain.TemporaryApplication
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TemporaryApplicationRepository : JpaRepository<TemporaryApplication, Long> {
    fun findByUser_Id(id: UUID): TemporaryApplication?
}
