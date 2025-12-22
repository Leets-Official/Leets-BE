package land.leets.domain.admin.usecase

import io.jsonwebtoken.Claims
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import land.leets.domain.shared.AuthRole
import land.leets.global.jwt.JwtProvider
import java.util.UUID

class AdminRefreshTokenImplTest : DescribeSpec({

    val jwtProvider = mockk<JwtProvider>()
    val adminRefreshToken = AdminRefreshTokenImpl(jwtProvider)

    describe("AdminRefreshTokenImpl 유스케이스는") {
        context("리프레시 토큰 갱신을 실행할 때") {
            val refreshToken = "refreshToken"
            val newAccessToken = "newAccessToken"
            val role = AuthRole.ROLE_ADMIN
            val uid = UUID.randomUUID()
            val subject = "admin"

            it("새로운 액세스 토큰이 담긴 JwtResponse를 반환한다") {
                val claims = mockk<Claims>()
                every { claims.get("role", String::class.java) } returns role.name
                every { claims.get("id", String::class.java) } returns uid.toString()
                every { claims.subject } returns subject

                every { jwtProvider.validateToken(refreshToken, true) } just runs
                every { jwtProvider.parseClaims(refreshToken, true) } returns claims
                every { jwtProvider.generateToken(uid, subject, role, false) } returns newAccessToken

                val response = adminRefreshToken.execute(refreshToken)

                response.accessToken shouldBe newAccessToken
                response.refreshToken shouldBe null
            }
        }
    }
})
