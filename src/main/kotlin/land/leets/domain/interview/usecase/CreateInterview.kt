package land.leets.domain.interview.usecase

import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.presentation.dto.req.InterviewRequest
import land.leets.domain.interview.type.HasInterview
import java.util.UUID

interface CreateInterview {
    fun execute(request: InterviewRequest): Interview
}
