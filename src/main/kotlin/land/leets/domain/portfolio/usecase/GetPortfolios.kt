package land.leets.domain.portfolio.usecase

import land.leets.domain.portfolio.presentation.dto.PortfolioResponse
import land.leets.domain.portfolio.presentation.dto.PortfoliosResponse

interface GetPortfolios {
    fun all(generation: String?): List<List<PortfoliosResponse>>

    fun one(portfolioId: Long): PortfolioResponse
}
