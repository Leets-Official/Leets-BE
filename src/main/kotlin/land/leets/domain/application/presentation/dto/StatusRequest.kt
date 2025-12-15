package land.leets.domain.application.presentation.dto

import jakarta.validation.constraints.NotNull
import land.leets.domain.application.type.ApplicationStatus

data class StatusRequest(
    @NotNull
    val applicationStatus: ApplicationStatus
)
