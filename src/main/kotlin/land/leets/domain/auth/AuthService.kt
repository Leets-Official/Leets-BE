package land.leets.domain.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import io.github.oshai.kotlinlogging.KotlinLogging
import land.leets.domain.auth.exception.PermissionDeniedException
import land.leets.domain.auth.presentation.dto.OAuthTokenDto
import land.leets.domain.user.domain.User
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.global.jwt.exception.InvalidTokenException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

private val log = KotlinLogging.logger {}

@Service
class AuthService(
    @Value("\${google.auth.url}") private val googleAuthUrl: String,
    @Value("\${google.redirect.url}") private val googleRedirectUrl: String,
    @Value("\${spring.security.oauth2.client.registration.google.client-id}") private val googleClientId: String,
    @Value("\${spring.security.oauth2.client.registration.google.client-secret}") private val googleClientPassword: String,
    private val userRepository: UserRepository,
) {

    fun getGoogleToken(code: String): User {
        val restTemplate = RestTemplate()
        val params = mapOf(
            "code" to code,
            "client_id" to googleClientId,
            "client_secret" to googleClientPassword,
            "redirect_uri" to googleRedirectUrl,
            "grant_type" to "authorization_code"
        )

        val responseEntity = restTemplate.postForEntity(googleAuthUrl, params, OAuthTokenDto::class.java)

        if (responseEntity.statusCode != HttpStatus.OK || responseEntity.body == null) {
            throw PermissionDeniedException()
        }

        val idToken = responseEntity.body!!.idToken
        return getUser(idToken)
    }

    fun getUser(idToken: String): User {
        val verifier = GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory.getDefaultInstance())
            .setAudience(listOf(googleClientId))
            .build()

        val googleIdToken = runCatching {
            verifier.verify(idToken)
        }.onFailure { e ->
            log.debug {
                "${"Google ID 토큰 인증 실패: {}"} ${
                    e::class.simpleName
                }"
            }
        }.getOrNull() ?: throw InvalidTokenException()


        val payload = googleIdToken.payload
        val userId = payload.subject

        return userRepository.findBySub(userId) ?: userRepository.save(
            User(
                sid = null,
                name = payload["name"].toString(),
                phone = null,
                email = payload.email,
                sub = userId
            )
        )
    }
}
