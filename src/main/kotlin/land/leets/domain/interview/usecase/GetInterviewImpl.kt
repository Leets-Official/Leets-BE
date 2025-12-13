package land.leets.domain.interview.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.interview.domain.repository.InterviewRepository
import land.leets.domain.interview.presentation.dto.res.InterviewResponse
import org.springframework.stereotype.Service

@Service
class GetInterviewImpl(
    private val interviewRepository: InterviewRepository
) : GetInterview {

    override fun execute(application: Application): InterviewResponse {
        val interview = interviewRepository.findByApplication(application)
        return interview.map { InterviewResponse.from(it) }
            .orElse(InterviewResponse(null, null))
    }
}
