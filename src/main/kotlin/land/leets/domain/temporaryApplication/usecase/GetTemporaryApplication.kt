package land.leets.domain.temporaryApplication.usecase

import land.leets.domain.auth.AuthDetails
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationResponse

interface GetTemporaryApplication {
    fun execute(authDetails: AuthDetails): TemporaryApplicationResponse?
}
