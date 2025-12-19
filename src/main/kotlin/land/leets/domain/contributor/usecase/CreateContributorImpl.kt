package land.leets.domain.contributor.usecase

import land.leets.domain.contributor.domain.Contributor
import land.leets.domain.contributor.domain.repository.ContributorRepository
import land.leets.domain.portfolio.domain.Portfolio
import org.springframework.stereotype.Service

@Service
class CreateContributorImpl(
    private val contributorRepository: ContributorRepository
) : CreateContributor {
    override fun execute(request: List<Contributor>, portfolio: Portfolio) {
        for (contributor in request) {
            contributor.updatePortfolio(portfolio)
            contributorRepository.save(contributor)
        }
    }
}
