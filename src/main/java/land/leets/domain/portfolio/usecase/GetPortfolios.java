package land.leets.domain.portfolio.usecase;

import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;

import java.util.List;

public interface GetPortfolios {
    List<PortfolioResponse> execute(String generation);
}
