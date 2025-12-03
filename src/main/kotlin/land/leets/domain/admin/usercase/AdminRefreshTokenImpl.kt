package land.leets.domain.admin.usercase

import io.jsonwebtoken.Claims
import land.leets.domain.shared.AuthRole
import land.leets.global.jwt.JwtProvider
import land.leets.global.jwt.dto.JwtResponse
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AdminRefreshTokenImpl(
    private val jwtProvider: JwtProvider
) : AdminRefreshToken {

    override fun execute(refreshToken: String): JwtResponse {
        jwtProvider.validateToken(refreshToken, true)
        val claims = jwtProvider.parseClaims(refreshToken, true)
        val role = claims.get("role", String::class.java)
        val uuid = UUID.fromString(claims.get("uid", String::class.java))
        val newAccessToken = jwtProvider.generateToken(uuid, claims.subject, AuthRole.valueOf(role), false)
        return JwtResponse(newAccessToken, null)
    }
}