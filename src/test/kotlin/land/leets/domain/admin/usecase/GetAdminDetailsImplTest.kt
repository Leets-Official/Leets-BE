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
import java.util.Optional
import java.util.UUID

class GetAdminDetailsImplTest : DescribeSpec({

    val adminRepository = mockk<AdminRepository>()
    val getAdminDetails = GetAdminDetailsImpl(adminRepository)

    describe("GetAdminDetailsImpl") {
        context("execute") {
            val id = UUID.randomUUID()
            val username = "admin"
            val authDetails = AuthDetails(id, "admin", AuthRole.ROLE_ADMIN)
            val admin = Admin(username, "password", "name", "email", id)

            it("should return AdminDetailsResponse when admin exists") {
                every { adminRepository.findById(id) } returns Optional.of(admin)

                val response = getAdminDetails.execute(authDetails)

                response.name shouldBe admin.name
            }

            it("should throw AdminNotFoundException when admin not found") {
                every { adminRepository.findById(id) } returns Optional.empty()

                shouldThrow<AdminNotFoundException> {
                    getAdminDetails.execute(authDetails)
                }
            }
        }
    }
})
