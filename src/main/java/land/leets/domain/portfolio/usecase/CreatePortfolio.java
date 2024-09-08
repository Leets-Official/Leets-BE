package land.leets.domain.portfolio.usecase;

import land.leets.domain.portfolio.presentation.dto.PortfolioRequest;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;

public interface CreatePortfolio {
    PortfolioResponse execute(PortfolioRequest request);
}
