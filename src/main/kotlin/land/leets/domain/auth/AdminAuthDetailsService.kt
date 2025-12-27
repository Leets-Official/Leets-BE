package land.leets.domain.auth

import land.leets.domain.admin.domain.repository.AdminRepository
import land.leets.domain.shared.AuthRole
import land.leets.global.error.ErrorCode
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AdminAuthDetailsService(
    private val adminRepository: AdminRepository
) : UserDetailsService {

    override fun loadUserByUsername(sub: String): AuthDetails {
        val admin = adminRepository.findByUsername(sub)
            ?: throw UsernameNotFoundException(ErrorCode.ADMIN_NOT_FOUND.message)

        return AuthDetails(admin.id!!, admin.username, AuthRole.ROLE_ADMIN)
    }
}
