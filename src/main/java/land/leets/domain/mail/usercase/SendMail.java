package land.leets.domain.mail.usercase;

import land.leets.domain.application.type.ApplicationStatus;

public interface SendMail {
    void execute(String to, String name, ApplicationStatus result);
}