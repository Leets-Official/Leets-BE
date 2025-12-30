package land.leets.domain.application.usecase

import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.exception.ApplicationNotFoundException
import land.leets.domain.application.presentation.dto.ApplicationStatusResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class GetApplicationStatusImpl(
    private val applicationRepository: ApplicationRepository,
) : GetApplicationStatus {
    override fun execute(uid: UUID): ApplicationStatusResponse {
        val application = applicationRepository.findByUser_Id(uid)
            ?: throw ApplicationNotFoundException()

        return ApplicationStatusResponse.from(application)
    }
}