package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultRequest {
    private Result docResult;
    private Result finalResult;
}
