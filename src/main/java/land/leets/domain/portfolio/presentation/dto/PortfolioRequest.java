package land.leets.domain.portfolio.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import land.leets.domain.contributor.domain.Contributor;
import land.leets.domain.portfolio.domain.ProjectScope;
import land.leets.domain.portfolio.domain.ProjectType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
public class PortfolioRequest {

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
    private String serviceUrl;

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;

    @NotBlank
    private List<Contributor> contributors;
}