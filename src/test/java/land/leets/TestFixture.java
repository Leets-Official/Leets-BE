package land.leets;

import java.time.LocalDateTime;
import java.util.UUID;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.application.type.Position;
import land.leets.domain.application.type.SubmitStatus;
import land.leets.domain.user.domain.User;
import land.leets.global.mail.dto.MailDto;

public class TestFixture {

	public static User user = new User(UUID.randomUUID(), "sid", "테스트아연", "010", "naayen@naver.com", "sub");
	public static Application application = new Application(
		0L, user, "테스트아연", "major", "grade", "project", "algorithm",
		"portfolio", Position.DEV, "c", "2024.06.02", "3시", "motive",
		"schedule", "capability", "conflict", "passion", LocalDateTime.now(),
		ApplicationStatus.PASS, SubmitStatus.SUBMIT);

	public static MailDto mailDto = new MailDto("제목", new String[] {"naayen@naver.com"}, "body");
}
