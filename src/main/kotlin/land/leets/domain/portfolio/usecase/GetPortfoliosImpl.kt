package land.leets.domain.portfolio.usecase

import land.leets.domain.portfolio.domain.repository.PortfolioRepository
import land.leets.domain.portfolio.exception.PortfolioNotFoundException
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse
import land.leets.domain.portfolio.presentation.dto.PortfoliosResponse
import land.leets.domain.portfolio.type.ProjectScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetPortfoliosImpl(
    private val portfolioRepository: PortfolioRepository
) : GetPortfolios {

    @Transactional(readOnly = true)
    override fun all(generation: String?): List<List<PortfoliosResponse>> {
        return listOf(
            getPortfolios(generation?.toLong(), ProjectScope.FINAL),
            getPortfolios(generation?.toLong(), ProjectScope.TOY)
        )
    }

    @Transactional(readOnly = true)
    override fun one(portfolioId: Long): PortfolioResponse {
        val portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow { PortfolioNotFoundException() }

        return PortfolioResponse.from(portfolio)
    }

    private fun getPortfolios(
        generation: Long?,
        scope: ProjectScope
    ): List<PortfoliosResponse> {

        val list = if (generation == null) {
            portfolioRepository.findAllByScopeOrderByGenerationDesc(scope)
        } else {
            portfolioRepository.findAllByGenerationAndScope(generation, scope)
        }

        return list.map { PortfoliosResponse.from(it) }
    }
}
