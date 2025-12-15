package land.leets.domain.application.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.exception.ApplicationNotFoundException
import land.leets.domain.application.presentation.dto.StatusRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UpdateResultImpl(
    private val applicationRepository: ApplicationRepository
) : UpdateResult {

    override fun execute(id: Long, request: StatusRequest): Application {
        val application = applicationRepository.findByIdOrNull(id) ?: throw ApplicationNotFoundException()
        application.updateApplicationStatus(request.applicationStatus)
        return applicationRepository.save(application)
    }
}
