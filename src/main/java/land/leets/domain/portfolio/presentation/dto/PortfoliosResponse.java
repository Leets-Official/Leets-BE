package land.leets.domain.portfolio.presentation.dto;

import jakarta.validation.constraints.NotBlank;
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
}
