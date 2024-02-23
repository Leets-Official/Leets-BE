package land.leets.domain.portfolio.usecase;

import land.leets.domain.portfolio.presentation.dto.PortfolioRequest;
import land.leets.domain.portfolio.presentation.dto.PortfolioResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CreatePortfolio {
    PortfolioResponse execute(PortfolioRequest request, MultipartFile logoImg, MultipartFile coverImg, MultipartFile mainImg) ;
}