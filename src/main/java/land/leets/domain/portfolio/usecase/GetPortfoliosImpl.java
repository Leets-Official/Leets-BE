package land.leets.domain.portfolio.usecase;

import land.leets.domain.portfolio.domain.Portfolio;
import land.leets.domain.portfolio.domain.repository.PortfolioRepository;
import land.leets.domain.portfolio.exception.PortfolioNotFoundException;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;
import land.leets.domain.portfolio.presentation.dto.PortfoliosResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPortfoliosImpl implements GetPortfolios {

    private final PortfolioRepository portfolioRepository;

    @Override
    public List<List<PortfoliosResponse>> all(String generation) {
        if (generation != null) {
            List<Portfolio> portfolios = portfolioRepository.findAllByGeneration(Long.parseLong(generation));
            return List.of(portfolios.stream().map(PortfoliosResponse::from).toList());
        }

        List<Portfolio> portfolios = portfolioRepository.findAll();
        return List.of(portfolios.stream().map(PortfoliosResponse::from).toList());
    }

    @Override
    public PortfolioResponse one(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).orElseThrow(PortfolioNotFoundException::new);
        return PortfolioResponse.from(portfolio);
    }
}
