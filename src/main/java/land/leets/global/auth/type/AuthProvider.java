package land.leets.global.auth.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthProvider {
    GOOGLE("구글");

    private String statusKo;
}