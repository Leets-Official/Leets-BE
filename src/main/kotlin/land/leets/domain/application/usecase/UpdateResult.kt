package land.leets.domain.application.usecase

import land.leets.domain.application.domain.Application
import land.leets.domain.application.presentation.dto.StatusRequest

interface UpdateResult {
    fun execute(id: Long, request: StatusRequest): Application
}
