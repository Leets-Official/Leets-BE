package land.leets.domain.application.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.auth.AuthDetails

@Deprecated("임시 저장 테이블 분리에 따른 미사용")
interface UpdateApplication {
    fun execute(authDetails: AuthDetails, request: ApplicationRequest): Application
}
