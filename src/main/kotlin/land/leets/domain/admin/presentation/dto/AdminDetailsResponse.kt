package land.leets.domain.admin.presentation.dto

import jakarta.validation.constraints.NotBlank
import land.leets.domain.admin.domain.Admin

data class AdminDetailsResponse(
    @field:NotBlank
    val name: String
) {
    companion object {
        @JvmStatic
        fun from(admin: Admin): AdminDetailsResponse {
            return AdminDetailsResponse(admin.name)
        }
    }
}