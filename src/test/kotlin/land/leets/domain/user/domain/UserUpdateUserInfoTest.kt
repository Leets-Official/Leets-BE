package land.leets.domain.user.domain

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UserUpdateUserInfoTest : DescribeSpec({

    fun user() = User(
        sid = "20202020",
        name = "테스터",
        phone = "010-1234-5678",
        email = "test@test.com",
        sub = "sub"
    )

    describe("User.updateUserInfo 는") {
        context("값이 들어오면") {
            it("공백을 제거하고 갱신한다") {
                val user = user()
                user.updateUserInfo("  20251111  ", " 010-0000-0000 ")

                user.sid shouldBe "20251111"
                user.phone shouldBe "010-0000-0000"
            }
        }

        context("빈 값이 들어오면") {
            it("기존에 저장된 정보를 지우지 않는다") {
                val user = user()
                user.updateUserInfo("", "   ")

                user.sid shouldBe "20202020"
                user.phone shouldBe "010-1234-5678"
            }

            it("null 로 기존 정보를 지우지 않는다") {
                val user = user()
                user.updateUserInfo(null, null)

                user.sid shouldBe "20202020"
                user.phone shouldBe "010-1234-5678"
            }
        }

        context("문자열 \"null\" / \"undefined\" 가 들어오면") {
            it("실제 값으로 취급하지 않는다") {
                val user = user()
                user.updateUserInfo("null", "undefined")

                user.sid shouldBe "20202020"
                user.phone shouldBe "010-1234-5678"
            }
        }
    }
})
