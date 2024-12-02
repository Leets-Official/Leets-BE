package land.leets.domain.mail.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RecruitMailRequest(
        @Email @NotBlank String email
) {
}
