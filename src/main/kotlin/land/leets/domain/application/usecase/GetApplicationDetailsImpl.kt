package land.leets.domain.application.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.domain.repository.ApplicationRepository
import land.leets.domain.application.exception.ApplicationNotFoundException
import land.leets.domain.application.presentation.dto.ApplicationDetailsResponse
import land.leets.domain.application.presentation.dto.MyApplicationResponse
import land.leets.domain.application.service.RecruitSchedule
import land.leets.domain.interview.usecase.GetInterviewDetails
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class GetApplicationDetailsImpl(
    private val applicationRepository: ApplicationRepository,
    private val getInterviewDetails: GetInterviewDetails,
    private val recruitSchedule: RecruitSchedule
) : GetApplicationDetails {

    override fun execute(id: Long): ApplicationDetailsResponse {
        val application = applicationRepository.findByIdOrNull(id) ?: throw ApplicationNotFoundException()
        return getDetails(application)
    }

    override fun execute(uid: UUID): MyApplicationResponse {
        val application = applicationRepository.findByUser_Id(uid) ?: throw ApplicationNotFoundException()
        // 발표 시각 전에는 확정된 심사 결과가 응답 본문으로 나가지 않도록 치환한다.
        val visibleStatus = recruitSchedule.visibleStatus(application.applicationStatus, application.appliedAt)
        val round = recruitSchedule.roundOf(application.appliedAt)
        return MyApplicationResponse.of(application, visibleStatus, round)
    }

    private fun getDetails(application: Application): ApplicationDetailsResponse {
        val interview = getInterviewDetails.execute(application)
        val phone = application.user.phone ?: ""
        val email = application.user.email
        return ApplicationDetailsResponse.of(application, interview, phone, email)
    }
}
