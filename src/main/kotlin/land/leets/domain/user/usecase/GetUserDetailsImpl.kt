package land.leets.domain.user.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.type.SubmitStatus
import land.leets.domain.auth.AuthDetails
import land.leets.domain.user.domain.User
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.domain.user.exception.UserNotFoundException
import land.leets.domain.user.presentation.dto.UserDetailsResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetUserDetailsImpl(
    private val userRepository: UserRepository,
    private val applicationRepository: ApplicationRepository
) : GetUserDetails {

    override fun execute(authDetails: AuthDetails): UserDetailsResponse {
        val uid: UUID = authDetails.uid
        val user: User = userRepository.findById(uid).orElseThrow{ throw UserNotFoundException() }

        val application: Application? = applicationRepository.findByUser_Uid(uid)

        return UserDetailsResponse.of(user, application?.submitStatus ?: SubmitStatus.NONE)
    }
}
