package land.leets.domain.application.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Position {
    DEV("개발"),
    DESIGN("디자인"),
    BX_BI("BX/BI"),
    UX_UI("UX/UI"),
    PM("PM");

    private final String statusKo;
}
