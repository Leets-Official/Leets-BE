package land.leets.domain.interview.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.interview.domain.repository.InterviewRepository
import land.leets.domain.interview.presentation.dto.res.InterviewDetailsResponse
import org.springframework.stereotype.Service

@Service
class GetInterviewDetailsImpl(
    private val interviewRepository: InterviewRepository
) : GetInterviewDetails {

    override fun execute(application: Application): InterviewDetailsResponse {
        val interview = interviewRepository.findByApplication(application)
        return interview.map { InterviewDetailsResponse.from(it) }
            .orElse(InterviewDetailsResponse(null, null, null, null))
    }
}
