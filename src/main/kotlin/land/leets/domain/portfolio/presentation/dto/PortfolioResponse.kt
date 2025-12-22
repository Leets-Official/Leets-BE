package land.leets.domain.portfolio.presentation.dto

import land.leets.domain.contributor.domain.Contributor
import land.leets.domain.portfolio.domain.Portfolio
import land.leets.domain.portfolio.type.ProjectScope
import land.leets.domain.portfolio.type.ProjectType
import java.time.LocalDate

data class PortfolioResponse(
    val portfolioId: Long,
    val generation: Long,
    val name: String,
    val summary: String,
    val description: String,
    val type: ProjectType,
    val scope: ProjectScope,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val serviceUrl: String,
    val logoImgName: String,
    val mainImgName: String,
    val contributors: List<Contributor>
) {
    companion object {
        fun from(portfolio: Portfolio): PortfolioResponse {
            return PortfolioResponse(
                portfolioId = portfolio.portfolioId,
                generation = portfolio.generation,
                name = portfolio.name,
                summary = portfolio.summary,
                description = portfolio.description,
                type = portfolio.type,
                scope = portfolio.scope,
                startDate = portfolio.startDate,
                endDate = portfolio.endDate,
                serviceUrl = portfolio.serviceUrl,
                logoImgName = portfolio.logoImgName,
                mainImgName = portfolio.mainImgName,
                contributors = portfolio.contributors
            )
        }
    }
}
