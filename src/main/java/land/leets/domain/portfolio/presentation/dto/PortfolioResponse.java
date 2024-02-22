package land.leets.domain.portfolio.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import land.leets.domain.portfolio.domain.ProjectType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
public class PortfolioResponse {

    @NotBlank
    private Long id;

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
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;

    @NotBlank
    private String logoImgUrl;

    @NotBlank
    private String coverImgUrl;

    @NotBlank
    private String mainImgUrl;
}
