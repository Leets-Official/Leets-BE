package land.leets.domain.admin.usecase

import land.leets.domain.admin.domain.repository.AdminRepository
import land.leets.domain.admin.exception.AdminNotFoundException
import land.leets.domain.shared.AuthRole
import land.leets.domain.shared.exception.PasswordNotMatchException
import land.leets.global.jwt.JwtProvider
import land.leets.global.jwt.dto.JwtResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AdminLoginImpl(
    private val jwtProvider: JwtProvider,
    private val adminRepository: AdminRepository,
    private val passwordEncoder: PasswordEncoder
) : AdminLogin {

    override fun execute(id: String, password: String): JwtResponse {
        val admin = adminRepository.findById(id).orElseThrow { AdminNotFoundException() }

        if (!passwordEncoder.matches(password, admin.password)) {
            throw PasswordNotMatchException()
        }

        val accessToken = jwtProvider.generateToken(admin.uid!!, admin.id, AuthRole.ROLE_ADMIN, false)
        val refreshToken = jwtProvider.generateToken(admin.uid, admin.id, AuthRole.ROLE_ADMIN, true)

        return JwtResponse(accessToken, refreshToken)
    }
}