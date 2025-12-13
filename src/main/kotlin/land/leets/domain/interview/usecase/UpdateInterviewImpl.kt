package land.leets.domain.interview.usecase

import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.domain.repository.InterviewRepository
import land.leets.domain.interview.exception.InterviewNotFoundException
import land.leets.domain.interview.presentation.dto.req.FixedInterviewRequest
import land.leets.domain.interview.presentation.dto.req.InterviewAttendanceRequest
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateInterviewImpl(
    private val userRepository: UserRepository,
    private val interviewRepository: InterviewRepository
) : UpdateInterview {

    @Transactional
    override fun byUser(request: InterviewAttendanceRequest): Interview {
        val user = userRepository.findById(request.uid).orElseThrow { UserNotFoundException() }
        val interview = interviewRepository.findByApplication_User(user).orElseThrow { InterviewNotFoundException() }
        request.updateInterview(interview)
        return interviewRepository.save(interview)
    }

    @Transactional
    override fun byAdmin(id: Long, request: FixedInterviewRequest): Interview {
        val interview = interviewRepository.findById(id).orElseThrow { InterviewNotFoundException() }
        request.updateInterview(interview)
        return interviewRepository.save(interview)
    }
}