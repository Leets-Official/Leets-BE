package land.leets.domain.comment.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import land.leets.domain.admin.domain.Admin
import land.leets.domain.admin.domain.repository.AdminRepository
import land.leets.domain.admin.exception.AdminNotFoundException
import land.leets.domain.auth.AuthDetails
import land.leets.domain.comment.domain.Comment
import land.leets.domain.comment.domain.repository.CommentRepository
import land.leets.domain.comment.presentation.dto.CommentRequest
import land.leets.domain.shared.AuthRole
import java.time.LocalDateTime
import java.util.*

class CreateCommentTest : DescribeSpec({
    val adminRepository = mockk<AdminRepository>()
    val commentRepository = mockk<CommentRepository>()
    val createComment = CreateCommentImpl(adminRepository, commentRepository)

    describe("CreateComment") {
        context("유효한 요청이 주어지면") {
            val uuid = UUID.randomUUID()
            val authDetails = AuthDetails(uuid, "email", AuthRole.ROLE_ADMIN)
            val request = CommentRequest(1L, "content")
            val admin = mockk<Admin>()
            val savedComment = mockk<Comment>()
            val now = LocalDateTime.now()

            every { adminRepository.findById(uuid) } returns Optional.of(admin)
            every { commentRepository.save(any()) } returns savedComment
            every { savedComment.content } returns "content"
            every { savedComment.createdAt } returns now
            every { savedComment.updatedAt } returns now
            every { savedComment.admin } returns admin
            every { admin.name } returns "admin"

            it("댓글을 생성하고 반환해야 한다") {
                val response = createComment.execute(authDetails, request)

                response.content shouldBe "content"
                response.admin.name shouldBe "admin"
                response.createdAt shouldBe now
                response.updatedAt shouldBe now
            }
        }

        context("관리자를 찾을 수 없으면") {
            val uuid = UUID.randomUUID()
            val authDetails = AuthDetails(uuid, "email", AuthRole.ROLE_ADMIN)
            val request = CommentRequest(1L, "content")

            every { adminRepository.findById(uuid) } returns Optional.empty()

            it("AdminNotFoundException을 던져야 한다") {
                shouldThrow<AdminNotFoundException> {
                    createComment.execute(authDetails, request)
                }
            }
        }
    }
})
