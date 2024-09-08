package land.leets.domain.contributor.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Position {

    BACK_END("BackEnd"),
    FRONT_END("FrontEnd"),
    BX_BI("BX/BI"),
    UX_UI("UX/UI");

    private final String position;
}
