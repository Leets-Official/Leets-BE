package land.leets.domain.application.usecase

import land.leets.domain.application.presentation.dto.ApplicationResponse

interface GetAllApplication {
    fun execute(): List<ApplicationResponse>
    fun execute(position: String?, status: String?): List<ApplicationResponse>
}
