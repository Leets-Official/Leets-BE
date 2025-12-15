package land.leets.domain.application.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.presentation.dto.ApplicationResponse
import land.leets.domain.application.type.Position
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.interview.usecase.GetInterview
import org.springframework.stereotype.Service

@Service
class GetAllApplicationImpl(
    private val applicationRepository: ApplicationRepository,
    private val getInterview: GetInterview
) : GetAllApplication {

    override fun execute(): List<ApplicationResponse> {
        return mapApplications(applicationRepository.findAllByOrderByAppliedAtDesc())
    }

    override fun execute(position: String?, status: String?): List<ApplicationResponse> {
        if (position != null) {
            val filter = Position.valueOf(position.uppercase())
            return mapApplications(applicationRepository.findAllByPositionOrderByAppliedAtDesc(filter))
        }

        val filter = SubmitStatus.valueOf(status!!.uppercase())
        return mapApplications(applicationRepository.findAllBySubmitStatusOrderByAppliedAtDesc(filter))
    }

    private fun mapApplications(applications: List<Application>): List<ApplicationResponse> {
        return applications.map { application ->
            val interview = getInterview.execute(application)
            val phone = application.user.getPhone()
            ApplicationResponse.of(application, interview, phone)
        }
    }
}
