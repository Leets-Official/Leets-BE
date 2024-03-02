package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.application.type.Position;
import land.leets.domain.application.type.SubmitStatus;
import land.leets.domain.interview.presentation.dto.res.InterviewDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationDetailsResponse {

    private Long id;
    private String phone;
    private String name;
    private String major;
    private String grade;
    private String project;
    private String algorithm;
    private String portfolio;
    private Position position;
    private String career;
    private String interviewDay;
    private String interviewTime;
    private String motive;
    private String schedule;
    private String capability;
    private String conflict;
    private String passion;
    private ApplicationStatus applicationStatus;
    private SubmitStatus submitStatus;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
    private InterviewDetailsResponse interview;
}
