package land.leets.domain.admin.presentation.dto

import land.leets.domain.admin.domain.Admin

data class AdminDetailsResponse(
    val name: String
) {
    companion object {
        @JvmStatic
        fun from(admin: Admin): AdminDetailsResponse {
            return AdminDetailsResponse(admin.name)
        }
    }
}