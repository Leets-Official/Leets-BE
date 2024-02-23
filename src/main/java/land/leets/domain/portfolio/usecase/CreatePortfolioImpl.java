package land.leets.domain.portfolio.usecase;

import land.leets.domain.contributor.usecase.CreateContributor;
import land.leets.domain.image.usecase.SaveImage;
import land.leets.domain.portfolio.domain.Portfolio;
import land.leets.domain.portfolio.domain.repository.PortfolioRepository;
import land.leets.domain.portfolio.presentation.dto.PortfolioRequest;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;
import land.leets.domain.portfolio.presentation.mapper.PortfolioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CreatePortfolioImpl implements CreatePortfolio {
    private final PortfolioRepository portfolioRepository;
    private final PortfolioMapper portfolioMapper;
    private final SaveImage saveImage;
    private final CreateContributor createContributor;

    @Override
    public PortfolioResponse execute(PortfolioRequest request, MultipartFile logoImg, MultipartFile coverImg, MultipartFile mainImg)  {
        String logoImgUrl = saveImage.save(logoImg);
        String coverImgUrl = saveImage.save(coverImg);
        String mainImgUrl = saveImage.save(mainImg);

        Portfolio portfolio = Portfolio.builder()
                .generation(request.getGeneration())
                .name(request.getName())
                .summary(request.getSummary())
                .description(request.getDescription())
                .type(request.getType())
                .scope(request.getScope())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .contributors(request.getContributors())
                .logoImgUrl(logoImgUrl)
                .coverImgUrl(coverImgUrl)
                .mainImgUrl(mainImgUrl)
                .build();
        Portfolio save = portfolioRepository.save(portfolio);
        createContributor.execute(request.getContributors(), save);
        return portfolioMapper.mappingPortfolioToDto(save);
    }
}
