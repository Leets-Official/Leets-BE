package land.leets.domain.application.presentation.dto;

import jakarta.validation.constraints.NotNull;
import land.leets.domain.application.type.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultRequest {

    @NotNull
    private Result result;
}
