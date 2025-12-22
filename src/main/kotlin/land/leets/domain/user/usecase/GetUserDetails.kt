package land.leets.domain.user.usecase

import land.leets.domain.auth.AuthDetails
import land.leets.domain.user.presentation.dto.UserDetailsResponse

interface GetUserDetails {
    fun execute(authDetails: AuthDetails): UserDetailsResponse
}
