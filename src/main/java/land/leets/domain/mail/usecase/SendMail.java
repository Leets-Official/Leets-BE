package land.leets.domain.mail.usecase;

import java.util.List;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.type.ApplicationStatus;

public interface SendMail {
	void execute(ApplicationStatus status, List<Application> applications);
}
