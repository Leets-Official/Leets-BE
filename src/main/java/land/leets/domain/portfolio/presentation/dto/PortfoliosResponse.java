package land.leets.domain.portfolio.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import land.leets.domain.portfolio.domain.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PortfoliosResponse {

    @NotBlank
    private Long portfolioId;

    @NotBlank
    private String name;

    @NotBlank
    private String mainImgName;

    public static PortfoliosResponse from(Portfolio portfolio) {
        return new PortfoliosResponse(
                portfolio.getPortfolioId(),
                portfolio.getName(),
                portfolio.getMainImgName()
        );
    }
}
