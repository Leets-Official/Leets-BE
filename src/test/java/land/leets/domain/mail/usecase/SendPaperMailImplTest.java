package land.leets.domain.mail.usecase;

import static land.leets.TestFixture.*;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import land.leets.domain.application.type.ApplicationStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SendPaperMailImplTest {

	@Autowired
	private SendFinalMailImpl sendFinalMail;

	@DisplayName("비동기로 메일을 전송합니다.")
	@Test
	@Disabled
	void execute() {
		sendFinalMail.execute(ApplicationStatus.PASS, List.of(
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application, application, application, application, application,
			application, application, application, application
		));
	}
}
