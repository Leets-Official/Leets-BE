package land.leets.domain.interview.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.domain.repository.InterviewRepository
import land.leets.domain.interview.exception.InterviewNotFoundException
import land.leets.domain.interview.presentation.dto.req.FixedInterviewRequest
import land.leets.domain.interview.presentation.dto.req.InterviewAttendanceRequest
import land.leets.domain.interview.type.HasInterview
import land.leets.domain.user.domain.User
import land.leets.domain.user.domain.repository.UserRepository
import land.leets.domain.user.exception.UserNotFoundException
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

class UpdateInterviewImplTest : DescribeSpec({

    val userRepository = mockk<UserRepository>()
    val interviewRepository = mockk<InterviewRepository>()
    val updateInterview = UpdateInterviewImpl(userRepository, interviewRepository)

    describe("UpdateInterviewImpl 유스케이스는") {
        context("유저에 의해 면접을 수정할 때") {
            val uid = UUID.randomUUID()
            val request = InterviewAttendanceRequest(
                uid = uid,
                hasInterview = HasInterview.CHECK
            )
            val user = mockk<User>()
            val interview = mockk<Interview>(relaxed = true)

            it("유저에 의해 면접을 수정한다") {
                every { userRepository.findById(uid) } returns Optional.of(user)
                every { interviewRepository.findByApplication_User(user) } returns interview
                every { interviewRepository.save(interview) } returns interview

                val result = updateInterview.byUser(request)

                result shouldBe interview
                verify { interview.hasInterview = HasInterview.CHECK }
                verify { interviewRepository.save(interview) }
            }

            it("알 수 없는 유저가 면접을 수정하려 하면 UserNotFoundException을 던진다") {
                every { userRepository.findById(uid) } returns Optional.empty()

                shouldThrow<UserNotFoundException> {
                    updateInterview.byUser(request)
                }
            }

            it("존재하지 않는 면접을 유저가 수정하려 하면 InterviewNotFoundException을 던진다") {
                every { userRepository.findById(uid) } returns Optional.of(user)
                every { interviewRepository.findByApplication_User(user) } returns null

                shouldThrow<InterviewNotFoundException> {
                    updateInterview.byUser(request)
                }
            }
        }

        context("관리자에 의해 면접을 수정할 때") {
            val id = 1L
            val fixedDate = LocalDateTime.now()
            val request = FixedInterviewRequest(
                fixedInterviewDate = fixedDate,
                place = "New Room"
            )
            val interview = mockk<Interview>(relaxed = true)

            it("관리자에 의해 면접을 수정한다") {
                every { interviewRepository.findById(id) } returns Optional.of(interview)
                every { interviewRepository.save(interview) } returns interview

                val result = updateInterview.byAdmin(id, request)

                result shouldBe interview
                verify { interview.fixedInterviewDate = fixedDate }
                verify { interview.place = "New Room" }
                verify { interviewRepository.save(interview) }
            }

            it("존재하지 않는 면접을 관리자가 수정하려 하면 InterviewNotFoundException을 던진다") {
                every { interviewRepository.findById(id) } returns Optional.empty()

                shouldThrow<InterviewNotFoundException> {
                    updateInterview.byAdmin(id, request)
                }
            }
        }
    }
})