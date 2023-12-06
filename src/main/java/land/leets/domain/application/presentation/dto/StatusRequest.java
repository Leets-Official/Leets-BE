package land.leets.domain.application.presentation.dto;

import jakarta.validation.constraints.NotNull;
import land.leets.domain.application.type.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatusRequest {
    @NotNull
    private ApplicationStatus applicationStatus;
}
