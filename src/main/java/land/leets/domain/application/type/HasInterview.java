package land.leets.domain.application.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HasInterview {
    CHECK("참여"),
    UNCHECK("불참");

    private String statusKo;
}