package land.leets.global.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import land.leets.domain.auth.AdminAuthDetailsService
import land.leets.domain.auth.AuthDetails
import land.leets.domain.auth.UserAuthDetailsService
import land.leets.domain.shared.AuthRole
import land.leets.global.jwt.exception.ExpiredTokenException
import land.leets.global.jwt.exception.InvalidTokenException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    @Value("\${jwt.auth.access_secret}")
    accessSecret: String,
    @Value("\${jwt.auth.refresh_secret}")
    refreshSecret: String,
    private val userAuthDetailsService: UserAuthDetailsService,
    private val adminAuthDetailsService: AdminAuthDetailsService
) {
    private val accessSecret: SecretKey = Keys.hmacShaKeyFor(accessSecret.toByteArray(StandardCharsets.UTF_8))
    private val refreshSecret: SecretKey = Keys.hmacShaKeyFor(refreshSecret.toByteArray(StandardCharsets.UTF_8))

    fun generateToken(uuid: UUID, sub: String?, role: AuthRole, isRefreshToken: Boolean): String {
        val expirationDate = LocalDateTime.now()
            .run { if (isRefreshToken) plusDays(30) else plusDays(1) }
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .let { Date.from(it) }

        return Jwts.builder().apply {
            claim("role", role.role)
            claim("id", uuid.toString())
            subject(sub)
            expiration(expirationDate)
            signWith(if (isRefreshToken) refreshSecret else accessSecret)
        }.compact()
    }

    fun getAuthentication(token: String): Authentication {
        val claims = parseClaims(token, false)
        val authorities = listOf(SimpleGrantedAuthority(claims["role"].toString()))
        return UsernamePasswordAuthenticationToken(getDetails(claims), "", authorities)
    }

    private fun getDetails(claims: Claims): AuthDetails =
        if (claims["role"]!! == AuthRole.ROLE_ADMIN.role)
            adminAuthDetailsService.loadUserByUsername(claims.subject)
        else
            userAuthDetailsService.loadUserByUsername(claims.subject)


    fun validateToken(token: String, isRefreshToken: Boolean) =
        runCatching { parseClaims(token, isRefreshToken) }
            .onFailure { exception ->
                when (exception) {
                    is SignatureException,
                    is MalformedJwtException,
                    is UnsupportedJwtException,
                    is IllegalArgumentException -> throw InvalidTokenException()

                    is ExpiredJwtException -> throw ExpiredTokenException()

                    else -> throw exception
                }
            }

    fun parseClaims(token: String, isRefreshToken: Boolean): Claims =
        runCatching {
            Jwts.parser()
                .verifyWith(if (isRefreshToken) refreshSecret else accessSecret)
                .build()
                .parseSignedClaims(token)
                .payload
        }.getOrElse { exception ->
            when (exception) {
                is ExpiredJwtException -> exception.claims
                else -> throw exception
            }
        }
}
