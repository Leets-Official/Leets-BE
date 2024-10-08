package land.leets.domain.portfolio.usecase;

import land.leets.domain.portfolio.domain.Portfolio;
import land.leets.domain.portfolio.domain.ProjectScope;
import land.leets.domain.portfolio.domain.repository.PortfolioRepository;
import land.leets.domain.portfolio.exception.PortfolioNotFoundException;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;
import land.leets.domain.portfolio.presentation.dto.PortfoliosResponse;
import land.leets.domain.portfolio.presentation.mapper.PortfolioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetPortfoliosImpl implements GetPortfolios {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;

    @Override
    public List<List<PortfoliosResponse>> all(String generation) {
        List<List<PortfoliosResponse>> response = new ArrayList<>();
        if (generation == null) {
            response.add(getPortfoliosByScope(ProjectScope.FINAL));
            response.add(getPortfoliosByScope(ProjectScope.TOY));
            return response;
        }
        response.add(getPortfoliosByGenerationAndScope(Long.parseLong(generation), ProjectScope.FINAL));
        response.add(getPortfoliosByGenerationAndScope(Long.parseLong(generation), ProjectScope.TOY));
        return response;
    }

    @Override
    public PortfolioResponse one(Long portfolioId) {
        if (!portfolioRepository.existsById(portfolioId)) {
            throw new PortfolioNotFoundException();
        }
        Portfolio portfolio = portfolioRepository.findByPortfolioId(portfolioId);
        return portfolioMapper.mappingPortfolioToPortfolioResponse(portfolio);
    }

    private List<PortfoliosResponse> getPortfoliosByScope(ProjectScope scope) {
        return portfolioRepository.findAllByScopeOrderByGenerationDesc(scope).stream()
                .map(portfolioMapper::mappingPortfolioToPortfoliosResponse)
                .toList();
    }

    private List<PortfoliosResponse> getPortfoliosByGenerationAndScope(Long generation, ProjectScope scope) {
        return portfolioRepository.findAllByGenerationAndScope(generation, scope).stream()
                .map(portfolioMapper::mappingPortfolioToPortfoliosResponse)
                .toList();
    }

}
