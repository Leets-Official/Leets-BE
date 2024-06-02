package land.leets.domain.portfolio.usecase;

import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;
import land.leets.domain.portfolio.presentation.dto.PortfoliosResponse;

import java.util.List;

public interface GetPortfolios {
    List<List<PortfoliosResponse>> all(String generation);

    PortfolioResponse one(Long portfolioId);
}
