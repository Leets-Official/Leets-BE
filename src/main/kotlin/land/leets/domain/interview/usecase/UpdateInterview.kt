package land.leets.domain.interview.usecase

import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.presentation.dto.req.FixedInterviewRequest
import land.leets.domain.interview.presentation.dto.req.InterviewAttendanceRequest

interface UpdateInterview {
    fun byUser(request: InterviewAttendanceRequest): Interview
    fun byAdmin(id: Long, request: FixedInterviewRequest): Interview
}
