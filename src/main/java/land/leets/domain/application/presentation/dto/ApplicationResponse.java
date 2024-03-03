package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.application.type.Position;
import land.leets.domain.interview.presentation.dto.res.InterviewResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;
    private String name;
    private String grade;
    private Position position;
    private String career;
    private ApplicationStatus applicationStatus;
    private String phone;
    private InterviewResponse interview;
}
