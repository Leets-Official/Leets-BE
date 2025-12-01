package land.leets.domain.portfolio.domain.repository;

import land.leets.domain.portfolio.domain.Portfolio;
import land.leets.domain.portfolio.domain.ProjectScope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByGeneration(Long generation);

    List<Portfolio> findAllByGenerationAndScope(Long generation, ProjectScope scope);

    List<Portfolio> findAllByScopeOrderByGenerationDesc(ProjectScope scope);

    Portfolio findByPortfolioId(Long portfolioId);
}
