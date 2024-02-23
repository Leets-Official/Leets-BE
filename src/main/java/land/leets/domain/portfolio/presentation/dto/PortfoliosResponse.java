package land.leets.domain.portfolio.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import land.leets.domain.contributor.domain.Contributor;
import land.leets.domain.portfolio.domain.ProjectScope;
import land.leets.domain.portfolio.domain.ProjectType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class PortfoliosResponse {

    @NotBlank
    private Long portfolioId;

    @NotBlank
    private String name;

    @NotBlank
    private String coverImgUrl;
}
