package land.leets.domain.portfolio.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectType {
    WEB("WEB");

    private final String projectType;
}
