package land.leets.domain.admin.presentation.dto

import jakarta.validation.constraints.NotBlank

data class AdminLoginRequest(
    @NotBlank
    val username: String,

    @NotBlank
    val password: String
)