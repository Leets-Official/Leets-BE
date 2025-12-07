package land.leets.domain.admin.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import land.leets.domain.admin.domain.Admin
import land.leets.domain.admin.domain.repository.AdminRepository
import land.leets.domain.admin.exception.AdminNotFoundException
import land.leets.domain.shared.AuthRole
import land.leets.domain.shared.exception.PasswordNotMatchException
import land.leets.global.jwt.JwtProvider
import land.leets.global.jwt.dto.JwtResponse
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.Optional
import java.util.UUID

class AdminLoginImplTest : DescribeSpec({

    val jwtProvider = mockk<JwtProvider>()
    val adminRepository = mockk<AdminRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val adminLogin = AdminLoginImpl(jwtProvider, adminRepository, passwordEncoder)

    describe("AdminLoginImpl") {
        context("execute") {
            val username = "admin"
            val password = "password"
            val encodedPassword = "encodedPassword"
            val id = UUID.randomUUID()
            val admin = Admin(username, encodedPassword, "name", "email", id)

            it("should return JwtResponse when login is successful") {
                every { adminRepository.findByUsername(username) } returns Optional.of(admin)
                every { passwordEncoder.matches(password, encodedPassword) } returns true
                every { jwtProvider.generateToken(id, username, AuthRole.ROLE_ADMIN, false) } returns "accessToken"
                every { jwtProvider.generateToken(id, username, AuthRole.ROLE_ADMIN, true) } returns "refreshToken"

                val response = adminLogin.execute(username, password)

                response.accessToken shouldBe "accessToken"
                response.refreshToken shouldBe "refreshToken"
            }

            it("should throw AdminNotFoundException when admin not found") {
                every { adminRepository.findByUsername(username) } returns Optional.empty()

                shouldThrow<AdminNotFoundException> {
                    adminLogin.execute(username, password)
                }
            }

            it("should throw PasswordNotMatchException when password does not match") {
                every { adminRepository.findByUsername(username) } returns Optional.of(admin)
                every { passwordEncoder.matches(password, encodedPassword) } returns false

                shouldThrow<PasswordNotMatchException> {
                    adminLogin.execute(username, password)
                }
            }
        }
    }
})
