package land.leets.domain.portfolio.usecase;

import land.leets.domain.portfolio.domain.Portfolio;
import land.leets.domain.portfolio.domain.repository.PortfolioRepository;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;
import land.leets.domain.portfolio.presentation.mapper.PortfolioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class GetPortfoliosImpl implements GetPortfolios {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;

    @Override
    public List<PortfolioResponse> execute(String generation) {
        if (generation == null){
            List<Portfolio> portfolios = portfolioRepository.findAllBy();
            return portfolios.stream()
                    .map(portfolioMapper::mappingPortfolioToDto)
                    .toList();
        }
        List<Portfolio> portfolios = portfolioRepository.findAllByGeneration(Long.parseLong(generation));
        return portfolios.stream()
                .map(portfolioMapper::mappingPortfolioToDto)
                .toList();
    }
}