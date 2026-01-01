package land.leets.domain.storage.usecase

import land.leets.domain.storage.presentation.dto.PreAuthenticatedUrlResponse

interface GetPreAuthenticatedUrl {
    fun execute(fileName: String): PreAuthenticatedUrlResponse
}
