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
import land.leets.global.jwt.dto.JwtResponse
import java.util.UUID

class AdminRefreshTokenImplTest : DescribeSpec({

    val jwtProvider = mockk<JwtProvider>()
    val adminRefreshToken = AdminRefreshTokenImpl(jwtProvider)

    describe("AdminRefreshTokenImpl") {
        context("execute") {
            val refreshToken = "refreshToken"
            val newAccessToken = "newAccessToken"
            val role = AuthRole.ROLE_ADMIN
            val uid = UUID.randomUUID()
            val subject = "admin"

            it("should return JwtResponse with new access token") {
                val claims = mockk<Claims>()
                every { claims.get("role", String::class.java) } returns role.name
                every { claims.get("uid", String::class.java) } returns uid.toString()
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
