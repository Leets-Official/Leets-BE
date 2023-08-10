package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationResponse {
    private String name;
    private float gpa;
    private int grade;
    private String position;
    private String interview;
    private String hasInterview;
    private Result result;
}
