package land.leets.domain.admin.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDetailsResponse {
    @NotBlank
    private String name;
}
