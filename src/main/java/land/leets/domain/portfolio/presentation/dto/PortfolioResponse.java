package land.leets.domain.portfolio.presentation.dto;

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

    private Long portfolioId;

    private Long generation;

    private String name;

    private String summary;

    private String description;

    private ProjectType type;

    private ProjectScope scope;

    private LocalDate startDate;

    private LocalDate endDate;

    private String serviceUrl;

    private String logoImgName;

    private String mainImgName;

    private List<Contributor> contributors;
}
