package land.leets.domain.temporaryApplication.usecase

import land.leets.domain.auth.AuthDetails
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationRequest
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationResponse

interface SaveTemporaryApplication {
    fun execute(authDetails: AuthDetails, request: TemporaryApplicationRequest): TemporaryApplicationResponse
}
