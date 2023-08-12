package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;
    private String name;
    private float gpa;
    private int grade;
    private String position;
    private String interviewDay;
    private String interviewTime;
    private String hasInterview;
    private ApplicationStatus applicationStatus;
}
