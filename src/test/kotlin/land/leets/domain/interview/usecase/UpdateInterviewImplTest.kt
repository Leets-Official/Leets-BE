package land.leets.domain.interview.usecase

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
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
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

@ExtendWith(MockKExtension::class)
class UpdateInterviewImplTest {

    @MockK
    private lateinit var userRepository: UserRepository

    @MockK
    private lateinit var interviewRepository: InterviewRepository

    private lateinit var updateInterview: UpdateInterview

    @BeforeEach
    fun setUp() {
        updateInterview = UpdateInterviewImpl(userRepository, interviewRepository)
    }

    @Test
    fun `should update interview by user`() {
        // given
        val uid = UUID.randomUUID()
        val request = InterviewAttendanceRequest(
            uid = uid,
            hasInterview = HasInterview.CHECK
        )
        val user = mockk<User>()
        val interview = mockk<Interview>(relaxed = true)

        every { userRepository.findById(uid) } returns Optional.of(user)
        every { interviewRepository.findByApplication_User(user) } returns interview
        every { interviewRepository.save(interview) } returns interview

        // when
        val result = updateInterview.byUser(request)

        // then
        assertThat(result).isEqualTo(interview)
        verify { interview.hasInterview = HasInterview.CHECK }
        verify { interviewRepository.save(interview) }
    }

    @Test
    fun `should throw UserNotFoundException when updating interview by unknown user`() {
        // given
        val uid = UUID.randomUUID()
        val request = InterviewAttendanceRequest(
            uid = uid,
            hasInterview = HasInterview.CHECK
        )

        every { userRepository.findById(uid) } returns Optional.empty()

        // when & then
        assertThrows<UserNotFoundException> {
            updateInterview.byUser(request)
        }
    }

    @Test
    fun `should throw InterviewNotFoundException when updating non-existent interview by user`() {
        // given
        val uid = UUID.randomUUID()
        val request = InterviewAttendanceRequest(
            uid = uid,
            hasInterview = HasInterview.CHECK
        )
        val user = mockk<User>()

        every { userRepository.findById(uid) } returns Optional.of(user)
        every { interviewRepository.findByApplication_User(user) } returns null

        // when & then
        assertThrows<InterviewNotFoundException> {
            updateInterview.byUser(request)
        }
    }

    @Test
    fun `should update interview by admin`() {
        // given
        val id = 1L
        val fixedDate = LocalDateTime.now()
        val request = FixedInterviewRequest(
            fixedInterviewDate = fixedDate,
            place = "New Room"
        )
        val interview = mockk<Interview>(relaxed = true)

        every { interviewRepository.findById(id) } returns Optional.of(interview)
        every { interviewRepository.save(interview) } returns interview

        // when
        val result = updateInterview.byAdmin(id, request)

        // then
        assertThat(result).isEqualTo(interview)
        verify { interview.fixedInterviewDate = fixedDate }
        verify { interview.place = "New Room" }
        verify { interviewRepository.save(interview) }
    }

    @Test
    fun `should throw InterviewNotFoundException when updating non-existent interview by admin`() {
        // given
        val id = 1L
        val request = FixedInterviewRequest(
            fixedInterviewDate = LocalDateTime.now(),
            place = "New Room"
        )

        every { interviewRepository.findById(id) } returns Optional.empty()

        // when & then
        assertThrows<InterviewNotFoundException> {
            updateInterview.byAdmin(id, request)
        }
    }
}
