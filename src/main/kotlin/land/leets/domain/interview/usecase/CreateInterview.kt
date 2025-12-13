package land.leets.domain.interview.usecase

import land.leets.domain.interview.domain.Interview
import land.leets.domain.interview.presentation.dto.req.InterviewRequest

interface CreateInterview {
    fun execute(request: InterviewRequest): Interview
}
