package land.leets.domain.application.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubmitStatus {
    SAVE("임시저장"),
    SUBMIT("제출")
    ;

    private String statusKo;
}