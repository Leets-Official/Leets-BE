package land.leets.domain.application.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Result {
    PASS("합격"),
    PENDING("보류"),
    FAIL("불합격");

    private String statusKo;
}