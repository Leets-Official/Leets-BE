package land.leets.domain.portfolio.usecase;

import land.leets.domain.portfolio.domain.repository.PortfolioRepository;
import land.leets.domain.portfolio.exception.PortfolioNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePortfoliosImpl implements UpdatePortfolios {
    private final PortfolioRepository portfolioRepository;

    @Override
    public void delete(Long portfolioId) {
        if (!portfolioRepository.existsById(portfolioId)) {
            throw new PortfolioNotFoundException();
        }
        portfolioRepository.deleteById(portfolioId);
    }
}