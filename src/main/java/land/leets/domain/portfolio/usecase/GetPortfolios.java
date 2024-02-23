package land.leets.domain.portfolio.usecase;

import land.leets.domain.portfolio.domain.ProjectScope;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;

import java.util.List;
import java.util.Map;

public interface GetPortfolios {
    List<List<PortfolioResponse>>  execute(String generation);
}
