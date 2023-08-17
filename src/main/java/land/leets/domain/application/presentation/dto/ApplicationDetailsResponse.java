package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.application.type.Position;
import land.leets.domain.application.type.SubmitStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApplicationDetailsResponse {

    private Long id;
    private String phone;
    private String name;
    private String gpa;
    private String major;
    private int grade;
    private String project;
    private String algorithm;
    private String portfolio;
    private Position position;
    private String career;
    private String interviewDay;
    private String interviewTime;
    private String enhancement;
    private String level;
    private String pros;
    private String goal;
    private String completion;
    private boolean hasInterview;
    private LocalDateTime fixedInterviewDate;
    private ApplicationStatus applicationStatus;
    private SubmitStatus submitStatus;
}
