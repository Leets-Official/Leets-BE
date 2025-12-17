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
import java.util.Optional
import java.util.UUID

class GetAdminDetailsImplTest : DescribeSpec({

    val adminRepository = mockk<AdminRepository>()
    val getAdminDetails = GetAdminDetailsImpl(adminRepository)

    describe("GetAdminDetailsImpl") {
        context("관리자 상세 정보를 조회할 때") {
            val id = UUID.randomUUID()
            val username = "admin"
            val authDetails = AuthDetails(id, "admin", AuthRole.ROLE_ADMIN)
            val admin = Admin(username, "password", "name", "email", id)

            it("관리자가 존재하면 AdminDetailsResponse를 반환한다") {
                every { adminRepository.findById(id) } returns Optional.of(admin)

                val response = getAdminDetails.execute(authDetails)

                response.name shouldBe admin.name
            }

            it("관리자를 찾을 수 없으면 AdminNotFoundException을 던진다") {
                every { adminRepository.findById(id) } returns Optional.empty()

                shouldThrow<AdminNotFoundException> {
                    getAdminDetails.execute(authDetails)
                }
            }
        }
    }
})
