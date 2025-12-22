package land.leets.domain.user.usecase

import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.user.domain.User
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.domain.user.exception.UserNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UpdateUserImpl(
    private val userRepository: UserRepository
) : UpdateUser {

    override fun execute(uid: UUID, request: ApplicationRequest): User {
        val user = userRepository.findById(uid).orElseThrow(){ throw UserNotFoundException()}
        user.updateUserInfo(request.sid, request.phone)
        return userRepository.save(user)
    }
}
