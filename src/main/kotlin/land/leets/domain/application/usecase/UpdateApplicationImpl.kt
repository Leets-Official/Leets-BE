package land.leets.domain.application.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.exception.ApplicationNotFoundException
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.auth.AuthDetails
import land.leets.domain.user.usecase.UpdateUser
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class UpdateApplicationImpl(
    private val applicationRepository: ApplicationRepository,
    private val updateUser: UpdateUser
) : UpdateApplication {

    @Transactional
    override fun execute(authDetails: AuthDetails, request: ApplicationRequest): Application {
        val uid = authDetails.uid
        updateUser.execute(uid, request)

        val application = applicationRepository.findByUser_Uid(uid) ?: throw ApplicationNotFoundException()

        if (request.submitStatus == SubmitStatus.SUBMIT) {
            application.updateInfo(LocalDateTime.now())
        }
        application.updateContent(request)
        return applicationRepository.save(application)
    }
}
