package land.leets.domain.portfolio.presentation.dto

import land.leets.domain.portfolio.domain.Portfolio

data class PortfoliosResponse(
    val portfolioId: Long,
    val name: String,
    val mainImgName: String,
) {
    companion object {
        fun from(portfolio: Portfolio): PortfoliosResponse {
            return PortfoliosResponse(
                portfolioId = portfolio.portfolioId,
                name = portfolio.name,
                mainImgName = portfolio.mainImgName
            )
        }
    }
}
