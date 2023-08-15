package land.leets.domain.application.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubmitStatus {
    NONE("미제출"),
    SAVE("임시저장"),
    SUBMIT("제출")
    ;

    private String statusKo;
}