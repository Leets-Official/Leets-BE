package land.leets.domain.contributor.usecase

import land.leets.domain.contributor.domain.Contributor
import land.leets.domain.portfolio.domain.Portfolio

interface CreateContributor {
    fun execute(request: List<Contributor>, portfolio: Portfolio)
}
