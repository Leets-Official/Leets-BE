package land.leets.domain.temporaryApplication.usecase

import land.leets.domain.auth.AuthDetails
import land.leets.domain.temporaryApplication.domain.TemporaryApplication
import land.leets.domain.temporaryApplication.domain.repository.TemporaryApplicationRepository
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationRequest
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationResponse
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class SaveTemporaryApplicationImpl(
    private val temporaryApplicationRepository: TemporaryApplicationRepository,
    private val userRepository: UserRepository,
) : SaveTemporaryApplication {

    override fun execute(authDetails: AuthDetails, request: TemporaryApplicationRequest): TemporaryApplicationResponse {
        val user = userRepository.findById(authDetails.uid).orElseThrow { UserNotFoundException() }

        val temporaryApplication = temporaryApplicationRepository.findByUser_Id(user.id!!)
            ?.also { it.updateContent(request) }
            ?: TemporaryApplication.of(user, request)

        request.phone?.let { phone ->
            user.updateUserInfo(request.sid, phone)
            userRepository.save(user)
        }

        return TemporaryApplicationResponse.from(
            temporaryApplicationRepository.save(temporaryApplication)
        )
    }
}
