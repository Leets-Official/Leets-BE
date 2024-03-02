package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.Position;
import land.leets.domain.application.type.SubmitStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationRequest {
    private String name;
    private String sid;
    private String phone;
    private String major;
    private String grade;
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
    private SubmitStatus submitStatus;
}
