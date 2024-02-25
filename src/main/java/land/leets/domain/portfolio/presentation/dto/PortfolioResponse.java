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
public class PortfolioResponse {

    @NotBlank
    private Long portfolioId;

    @NotBlank
    private Long generation;

    @NotBlank
    private String name;

    @NotBlank
    private String summary;

    @NotBlank
    private String description;

    @NotBlank
    private ProjectType type;

    @NotBlank
    private ProjectScope scope;

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;

    @NotBlank
    private String serviceUrl;

    @NotBlank
    private String logoImgName;

    @NotBlank
    private String mainImgName;

    @NotBlank
    private List<Contributor> contributors;
}
