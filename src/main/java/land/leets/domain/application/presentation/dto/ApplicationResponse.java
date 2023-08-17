package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.application.type.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;
    private String name;
    private String gpa;
    private int grade;
    private Position position;
    private String career;
    private LocalDateTime fixedInterviewDate;
    private String hasInterview;
    private ApplicationStatus applicationStatus;
}
