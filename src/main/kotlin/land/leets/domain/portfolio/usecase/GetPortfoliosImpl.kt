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
        if (generation == null) {
            return  listOf(
                getPortfoliosByScope(ProjectScope.FINAL),
                getPortfoliosByScope(ProjectScope.TOY)
            )
        }
        return listOf(
            getPortfoliosByGenerationAndScope(generation.toLong(), ProjectScope.FINAL),
            getPortfoliosByGenerationAndScope(generation.toLong(), ProjectScope.TOY)
        )
    }

    @Transactional(readOnly = true)
    override fun one(portfolioId: Long): PortfolioResponse {
        val portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow { PortfolioNotFoundException() }

        return PortfolioResponse.from(portfolio)
    }

    private fun getPortfoliosByScope(scope: ProjectScope): List<PortfoliosResponse> {
        return portfolioRepository.findAllByScopeOrderByGenerationDesc(scope)
            .map { PortfoliosResponse.from(it) }
            .toList()
    }

    private fun getPortfoliosByGenerationAndScope(
        generation: Long,
        scope: ProjectScope
    ): List<PortfoliosResponse> {
        return portfolioRepository.findAllByGenerationAndScope(generation, scope)
            .map { PortfoliosResponse.from(it) }
            .toList()
    }
}
