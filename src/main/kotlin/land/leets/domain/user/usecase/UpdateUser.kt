package land.leets.domain.user.usecase

import land.leets.domain.application.presentation.dto.ApplicationRequest
import land.leets.domain.user.domain.User
import java.util.UUID

interface UpdateUser {
    fun execute(uid: UUID, request: ApplicationRequest): User
}
