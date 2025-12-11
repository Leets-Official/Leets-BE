package land.leets.domain.comment.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import land.leets.domain.admin.domain.Admin
import land.leets.domain.comment.domain.Comment
import land.leets.domain.comment.domain.repository.CommentRepository
import java.time.LocalDateTime

class GetCommentsTest : DescribeSpec({
    val commentRepository = mockk<CommentRepository>()
    val getComments = GetCommentsImpl(commentRepository)

    describe("GetComments") {
        context("지원서 ID가 주어지면") {
            val applicationId = 1L
            val comment = mockk<Comment>()
            val admin = mockk<Admin>()
            val now = LocalDateTime.now()

            every { commentRepository.findAllByApplicationId(applicationId) } returns listOf(comment)
            every { comment.content } returns "content"
            every { comment.createdAt } returns now
            every { comment.updatedAt } returns now
            every { comment.admin } returns admin
            every { admin.name } returns "admin"

            it("해당 지원서의 모든 댓글을 반환해야 한다") {
                val response = getComments.execute(applicationId)

                response.size shouldBe 1
                response[0].content shouldBe "content"
                response[0].admin.name shouldBe "admin"
            }
        }
    }
})
