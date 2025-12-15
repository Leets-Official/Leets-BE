package land.leets.domain.application.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.auth.AuthDetails

interface UpdateApplication {
    fun execute(authDetails: AuthDetails, request: ApplicationRequest): Application
}
