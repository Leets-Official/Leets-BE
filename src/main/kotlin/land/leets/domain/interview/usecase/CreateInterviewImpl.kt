package land.leets.domain.interview.usecase

import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.exception.ApplicationNotFoundException
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.domain.repository.InterviewRepository
import land.leets.domain.interview.presentation.dto.req.InterviewRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateInterviewImpl(
    private val applicationRepository: ApplicationRepository,
    private val interviewRepository: InterviewRepository,
) : CreateInterview {
    override fun execute(request: InterviewRequest): Interview {
        val application = applicationRepository.findById(request.applicationId)
            .orElseThrow { ApplicationNotFoundException() }

        val interview = Interview(
            application = application,
            fixedInterviewDate = request.fixedInterviewDate,
            place = request.place
        )
        return interviewRepository.save(interview)
    }
}
