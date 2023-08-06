package land.leets.domain.admin.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminLoginRequest {
    @NotBlank
    private String id;

    @NotBlank
    private String password;
}
