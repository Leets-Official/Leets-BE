package land.leets.domain.admin.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import land.leets.domain.admin.domain.Admin
import land.leets.domain.admin.domain.repository.AdminRepository
import land.leets.domain.admin.exception.AdminNotFoundException
import land.leets.domain.auth.AuthDetails
import land.leets.domain.shared.AuthRole
import org.springframework.data.repository.findByIdOrNull
import java.util.UUID

class GetAdminDetailsImplTest : DescribeSpec({

    val adminRepository = mockk<AdminRepository>()
    val getAdminDetails = GetAdminDetailsImpl(adminRepository)

    describe("GetAdminDetailsImpl") {
        context("execute") {
            val uid = UUID.randomUUID()
            val authDetails = AuthDetails(uid, "admin", AuthRole.ROLE_ADMIN)
            val admin = Admin("admin", "password", "name", "email", uid)

            it("should return AdminDetailsResponse when admin exists") {
                every { adminRepository.findByIdOrNull(uid) } returns admin

                val response = getAdminDetails.execute(authDetails)

                response.name shouldBe admin.name
            }

            it("should throw AdminNotFoundException when admin not found") {
                every { adminRepository.findByIdOrNull(uid) } returns null

                shouldThrow<AdminNotFoundException> {
                    getAdminDetails.execute(authDetails)
                }
            }
        }
    }
})
