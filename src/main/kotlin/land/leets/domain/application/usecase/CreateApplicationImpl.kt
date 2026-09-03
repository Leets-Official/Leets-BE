package land.leets.domain.application.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.exception.ApplicationAlreadyExistsException
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.auth.AuthDetails
import land.leets.domain.temporaryApplication.domain.repository.TemporaryApplicationRepository
import land.leets.domain.user.domain.User
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.domain.user.exception.UserNotFoundException
import land.leets.global.error.exception.InvalidRequestBodyException
import land.leets.global.util.TextSanitizer
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CreateApplicationImpl(
    private val applicationRepository: ApplicationRepository,
    private val userRepository: UserRepository,
    private val temporaryApplicationRepository: TemporaryApplicationRepository
) : CreateApplication {

    @Transactional
    override fun execute(authDetails: AuthDetails, request: ApplicationRequest): Application {
        // 유저 행을 먼저 잠가, 같은 사람이 동시에 두 번 제출해도 아래 중복 검사를 통과하지 못하게 한다.
        val user: User = userRepository.findByIdForUpdate(authDetails.uid) ?: throw UserNotFoundException()

        if (applicationRepository.findByUser_Id(user.id!!) != null) {
            throw ApplicationAlreadyExistsException()
        }

        user.updateUserInfo(request.sid, request.phone)
        userRepository.save(user)

        val application = Application(
            user = user,
            name = request.name.required("name"),
            major = request.major.required("major"),
            grade = request.grade.required("grade"),
            project = TextSanitizer.clean(request.project),
            algorithm = TextSanitizer.clean(request.algorithm),
            portfolio = TextSanitizer.clean(request.portfolio),
            position = request.position,
            career = TextSanitizer.clean(request.career),
            interviewDay = request.interviewDay.required("interviewDay"),
            interviewTime = request.interviewTime.required("interviewTime"),
            motive = request.motive.required("motive"),
            expectation = request.expectation.required("expectation"),
            capability = request.capability.required("capability"),
            conflict = request.conflict.required("conflict"),
            passion = request.passion.required("passion"),
            submitStatus = request.submitStatus,
            appliedAt = if (request.submitStatus == SubmitStatus.SUBMIT) LocalDateTime.now() else null
        )

        val savedApplication = try {
            applicationRepository.save(application)
        } catch (e: DataIntegrityViolationException) {
            // user_id UNIQUE 제약에 걸린 경우. 동시에 두 번 제출된 상황이다.
            throw ApplicationAlreadyExistsException()
        }

        temporaryApplicationRepository.findByUser_Id(user.id)?.let {
            temporaryApplicationRepository.delete(it)
        }

        return savedApplication
    }

    /**
     * @NotBlank 가 잡지 못하는 "null" / "undefined" 문자열까지 걸러낸 뒤 반환한다.
     */
    private fun String?.required(field: String): String =
        TextSanitizer.clean(this) ?: throw InvalidRequestBodyException(field)
}
