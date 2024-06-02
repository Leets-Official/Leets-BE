package land.leets.domain.application.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationStatus {
    PASS_PAPER("서류 합격"),
    FAIL_PAPER("서류 불합격"),
    PASS("최종 합격"),
    FAIL("최종 불합격"),
    PENDING("보류");

    private String statusKo;
}
