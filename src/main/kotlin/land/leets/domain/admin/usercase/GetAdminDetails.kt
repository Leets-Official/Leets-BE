package land.leets.domain.admin.usercase

import land.leets.domain.admin.presentation.dto.AdminDetailsResponse
import land.leets.domain.auth.AuthDetails

interface GetAdminDetails {
    fun execute(authDetails: AuthDetails): AdminDetailsResponse
}