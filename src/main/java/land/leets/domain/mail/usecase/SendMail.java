package land.leets.domain.mail.usecase;

import land.leets.domain.application.type.ApplicationStatus;

public interface SendMail {

	void execute(ApplicationStatus status);
}
