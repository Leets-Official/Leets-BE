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
            val id = "admin"
            val password = "password"
            val encodedPassword = "encodedPassword"
            val uid = UUID.randomUUID()
            val admin = Admin(id, encodedPassword, "name", "email", uid)

            it("should return JwtResponse when login is successful") {
                every { adminRepository.findById(id) } returns Optional.of(admin)
                every { passwordEncoder.matches(password, encodedPassword) } returns true
                every { jwtProvider.generateToken(uid, id, AuthRole.ROLE_ADMIN, false) } returns "accessToken"
                every { jwtProvider.generateToken(uid, id, AuthRole.ROLE_ADMIN, true) } returns "refreshToken"

                val response = adminLogin.execute(id, password)

                response.accessToken shouldBe "accessToken"
                response.refreshToken shouldBe "refreshToken"
            }

            it("should throw AdminNotFoundException when admin not found") {
                every { adminRepository.findById(id) } returns Optional.empty()

                shouldThrow<AdminNotFoundException> {
                    adminLogin.execute(id, password)
                }
            }

            it("should throw PasswordNotMatchException when password does not match") {
                every { adminRepository.findById(id) } returns Optional.of(admin)
                every { passwordEncoder.matches(password, encodedPassword) } returns false

                shouldThrow<PasswordNotMatchException> {
                    adminLogin.execute(id, password)
                }
            }
        }
    }
})
