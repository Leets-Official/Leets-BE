package land.leets.domain.temporaryApplication.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import land.leets.domain.application.type.Position
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationRequest
import land.leets.domain.user.domain.User

class TemporaryApplicationUpdateContentTest : DescribeSpec({

    val user = User(
        sid = "20202020",
        name = "테스터",
        phone = "010-1234-5678",
        email = "test@test.com",
        sub = "sub"
    )

    fun request(
        name: String? = null,
        phone: String? = null,
        major: String? = null,
        grade: String? = null,
        motive: String? = null
    ) = TemporaryApplicationRequest(
        name = name,
        sid = null,
        phone = phone,
        major = major,
        grade = grade,
        project = null,
        algorithm = null,
        portfolio = null,
        position = Position.BACKEND,
        career = null,
        interviewDay = null,
        interviewTime = null,
        motive = motive,
        expectation = null,
        capability = null,
        conflict = null,
        passion = null
    )

    describe("TemporaryApplication 임시 저장은") {
        context("1단계에서 개인정보만 저장한 뒤") {
            it("2단계에서 자기소개서만 저장해도 개인정보가 남아 있다") {
                val temporaryApplication = TemporaryApplication.of(
                    user,
                    request(name = "홍길동", phone = "010-1111-2222", major = "컴퓨터공학", grade = "3")
                )

                // 프론트는 아직 입력하지 않은 단계의 필드를 빈 문자열로 보낸다.
                temporaryApplication.updateContent(
                    request(name = "", phone = "", major = "", grade = "", motive = "지원 동기")
                )

                temporaryApplication.name shouldBe "홍길동"
                temporaryApplication.phone shouldBe "010-1111-2222"
                temporaryApplication.major shouldBe "컴퓨터공학"
                temporaryApplication.grade shouldBe "3"
                temporaryApplication.motive shouldBe "지원 동기"
            }
        }

        context("문자열 \"null\" / \"undefined\" 가 들어오면") {
            it("저장하지 않는다") {
                val temporaryApplication = TemporaryApplication.of(
                    user,
                    request(name = "null", phone = "undefined", major = "  ")
                )

                temporaryApplication.name shouldBe null
                temporaryApplication.phone shouldBe null
                temporaryApplication.major shouldBe null
            }
        }
    }
})
