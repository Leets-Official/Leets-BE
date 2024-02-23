package land.leets.domain.portfolio.domain.repository;

import land.leets.domain.comment.domain.Comment;
import land.leets.domain.portfolio.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByGeneration(Long generation);
    List<Portfolio> findAllBy();
}