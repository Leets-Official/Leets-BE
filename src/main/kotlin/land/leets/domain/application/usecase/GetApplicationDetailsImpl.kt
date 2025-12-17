package land.leets.domain.application.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.exception.ApplicationNotFoundException
import land.leets.domain.application.presentation.dto.ApplicationDetailsResponse
import land.leets.domain.interview.usecase.GetInterviewDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetApplicationDetailsImpl(
    private val applicationRepository: ApplicationRepository,
    private val getInterviewDetails: GetInterviewDetails
) : GetApplicationDetails {

    override fun execute(id: Long): ApplicationDetailsResponse {
        val application = applicationRepository.findByIdOrNull(id) ?: throw ApplicationNotFoundException()
        return getDetails(application)
    }

    override fun execute(uid: UUID): Application {
        return applicationRepository.findByUser_Uid(uid) ?: throw ApplicationNotFoundException()
    }

    private fun getDetails(application: Application): ApplicationDetailsResponse {
        val interview = getInterviewDetails.execute(application)
        val phone = application.user.getPhone()
        return ApplicationDetailsResponse.of(application, interview, phone)
    }
}
