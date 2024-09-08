package land.leets.domain.mail.usecase;

import static land.leets.TestFixture.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.type.ApplicationStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SendFinalMailImplTest {

	@Autowired
	private SendFinalMailImpl sendFinalMail;

	@MockBean
	ApplicationRepository applicationRepository;

	@DisplayName("병렬 처리로 메일을 전송합니다.")
	@Test
	@Disabled
	void send50Mail() {
		when(applicationRepository.findAllByApplicationStatus(ApplicationStatus.PASS))
			.thenReturn(List.of(
				application, application, application, application, application,
				application, application, application, application, application,
				application, application, application, application, application,
				application, application, application, application, application,
				application, application, application, application, application,
				application, application, application, application, application,
				application, application, application, application, application,
				application, application, application, application, application,
				application, application, application, application, application,
				application, application, application, application, application
			));

		sendFinalMail.execute(ApplicationStatus.PASS);
	}
}
