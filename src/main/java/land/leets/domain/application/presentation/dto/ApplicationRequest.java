package land.leets.domain.application.presentation.dto;

import land.leets.domain.application.type.Position;
import land.leets.domain.application.type.SubmitStatus;
import land.leets.domain.user.domain.User;
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
    private String motive;
    private String expectation;
    private String capability;
    private String conflict;
    private String passion;
    private SubmitStatus submitStatus;

    public void updateUser(User user) {
        user.updateUserInfo(this.sid, this.phone);
    }

    public void updateApplication(land.leets.domain.application.domain.Application application) {
        application.setName(this.name);
        application.setMajor(this.major);
        application.setGrade(this.grade);
        application.setProject(this.project);
        application.setAlgorithm(this.algorithm);
        application.setPortfolio(this.portfolio);
        application.setPosition(this.position);
        application.setCareer(this.career);
        application.setInterviewDay(this.interviewDay);
        application.setInterviewTime(this.interviewTime);
        application.setMotive(this.motive);
        application.setExpectation(this.expectation);
        application.setCapability(this.capability);
        application.setConflict(this.conflict);
        application.setPassion(this.passion);
        application.setSubmitStatus(this.submitStatus);
    }
}
