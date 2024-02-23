package land.leets.domain.portfolio.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectScope {
    TOY("Toy"),
    FINAL("Final");

    private final String projectScope;
}
