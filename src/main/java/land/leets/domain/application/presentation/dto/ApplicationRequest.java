package land.leets.domain.application.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationRequest {
    private String name;
    private String sid;
    private String phone;
    private float gpa;
    private String major;
    private int grade;
    private String project;
    private String algorithm;
    private String portfolio;
    private String position;
    private String interviewDay;
    private String interviewTime;
    private String enhancement;
    private String level;
    private String pros;
    private String goal;
    private String completion;
}
