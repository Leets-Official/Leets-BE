package land.leets.domain.auth

import land.leets.domain.shared.AuthRole
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.global.error.ErrorCode
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserAuthDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): AuthDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException(ErrorCode.USER_NOT_FOUND.message)

        return AuthDetails(user.id!!, user.email, AuthRole.ROLE_USER)
    }
}
