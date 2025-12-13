package land.leets.domain.interview.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.interview.presentation.dto.res.InterviewResponse

interface GetInterview {
    fun execute(application: Application): InterviewResponse
}
