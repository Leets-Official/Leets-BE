package land.leets.domain.portfolio.usecase;

import land.leets.domain.contributor.usecase.CreateContributor;
import land.leets.domain.portfolio.domain.Portfolio;
import land.leets.domain.portfolio.domain.repository.PortfolioRepository;
import land.leets.domain.portfolio.presentation.dto.PortfolioRequest;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;
import land.leets.domain.portfolio.presentation.mapper.PortfolioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePortfolioImpl implements CreatePortfolio {
    
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final CreateContributor createContributor;

    @Override
    public PortfolioResponse execute(PortfolioRequest request) {

        Portfolio portfolio = Portfolio.builder()
                .generation(request.getGeneration())
                .name(request.getName())
                .summary(request.getSummary())
                .description(request.getDescription())
                .type(request.getType())
                .scope(request.getScope())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .serviceUrl(request.getServiceUrl())
                .contributors(request.getContributors())
                .logoImgName(request.getLogoImgName())
                .mainImgName(request.getMainImgName())
                .build();
        Portfolio save = portfolioRepository.save(portfolio);
        createContributor.execute(request.getContributors(), save);
        return portfolioMapper.mappingPortfolioToPortfolioResponse(save);
    }
}
