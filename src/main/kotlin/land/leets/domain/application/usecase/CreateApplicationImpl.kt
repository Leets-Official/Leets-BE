package land.leets.domain.application.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.exception.ApplicationAlreadyExistsException
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.auth.AuthDetails
import land.leets.domain.user.domain.User
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CreateApplicationImpl(
    private val applicationRepository: ApplicationRepository,
    private val userRepository: UserRepository
) : CreateApplication {

    override fun execute(authDetails: AuthDetails, request: ApplicationRequest): Application {
        val user: User = userRepository.findById(authDetails.uid).orElseThrow { UserNotFoundException() }

        if (applicationRepository.findByUser_Uid(user.uid!!) != null) {
            throw ApplicationAlreadyExistsException()
        }

        user.updateUserInfo(request.sid, request.phone)
        userRepository.save(user)

        val application = Application(
            user = user,
            name = request.name,
            major = request.major,
            grade = request.grade,
            project = request.project,
            algorithm = request.algorithm,
            portfolio = request.portfolio,
            position = request.position,
            career = request.career,
            interviewDay = request.interviewDay,
            interviewTime = request.interviewTime,
            motive = request.motive,
            expectation = request.expectation,
            capability = request.capability,
            conflict = request.conflict,
            passion = request.passion,
            submitStatus = request.submitStatus,
            appliedAt = if (request.submitStatus == SubmitStatus.SUBMIT) LocalDateTime.now() else null
        )
        return applicationRepository.save(application)
    }
}
