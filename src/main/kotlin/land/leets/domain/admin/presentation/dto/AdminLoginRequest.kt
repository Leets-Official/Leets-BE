package land.leets.domain.admin.presentation.dto

import jakarta.validation.constraints.NotBlank

data class AdminLoginRequest(
    @field:NotBlank
    val id: String,

    @field:NotBlank
    val password: String
)