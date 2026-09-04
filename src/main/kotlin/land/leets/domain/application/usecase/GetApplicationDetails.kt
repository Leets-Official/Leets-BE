package land.leets.domain.application.usecase

import land.leets.domain.application.presentation.dto.ApplicationDetailsResponse
import land.leets.domain.application.presentation.dto.MyApplicationResponse
import java.util.UUID

interface GetApplicationDetails {
    fun execute(id: Long): ApplicationDetailsResponse
    fun execute(uid: UUID): MyApplicationResponse
}
