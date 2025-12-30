package land.leets.domain.application.usecase

import land.leets.domain.application.presentation.dto.ApplicationStatusResponse
import java.util.UUID

interface GetApplicationStatus {
    fun execute(uid: UUID): ApplicationStatusResponse
}