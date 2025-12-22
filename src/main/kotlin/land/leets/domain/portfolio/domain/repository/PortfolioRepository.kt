package land.leets.domain.portfolio.domain.repository

import land.leets.domain.portfolio.domain.Portfolio
import land.leets.domain.portfolio.type.ProjectScope
import org.springframework.data.jpa.repository.JpaRepository

interface PortfolioRepository : JpaRepository<Portfolio, Long> {
    fun findAllByGenerationAndScope(generation: Long, scope: ProjectScope): List<Portfolio>

    fun findAllByScopeOrderByGenerationDesc(scope: ProjectScope): List<Portfolio>
}
