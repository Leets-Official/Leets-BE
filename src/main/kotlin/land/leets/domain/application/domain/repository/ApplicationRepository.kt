package land.leets.domain.application.domain.repository

import land.leets.domain.application.domain.Application
import land.leets.domain.application.type.Position
import land.leets.domain.application.type.SubmitStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ApplicationRepository : JpaRepository<Application, Long> {
    fun findByUser_Id(id: UUID): Application?
    fun findAllByOrderByAppliedAtDesc(): List<Application>
    fun findAllByPositionOrderByAppliedAtDesc(position: Position): List<Application>
    fun findAllBySubmitStatusOrderByAppliedAtDesc(submitStatus: SubmitStatus): List<Application>
}
