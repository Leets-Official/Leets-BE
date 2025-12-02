package land.leets.domain.admin.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import land.leets.domain.admin.domain.Admin;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminDetailsResponse {
    @NotBlank
    private String name;

    public static AdminDetailsResponse from(Admin admin) {
        return new AdminDetailsResponse(admin.getName());
    }
}
