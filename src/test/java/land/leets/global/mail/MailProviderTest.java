package land.leets.global.mail;

import static land.leets.TestFixture.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailProviderTest {

	@Autowired
	MailProvider mailProvider;

	@Test
	@DisplayName("병렬 처리로 50개 메일을 전송합니다.")
	void sendEmails() {
		mailProvider.sendEmails(List.of(
			mailDto, mailDto, mailDto, mailDto, mailDto,
			mailDto, mailDto, mailDto, mailDto, mailDto,
			mailDto, mailDto, mailDto, mailDto, mailDto,
			mailDto, mailDto, mailDto, mailDto, mailDto,
			mailDto, mailDto, mailDto, mailDto, mailDto,
			mailDto, mailDto, mailDto, mailDto, mailDto,
			mailDto, mailDto, mailDto, mailDto, mailDto,
			mailDto, mailDto, mailDto, mailDto, mailDto,
			mailDto, mailDto, mailDto, mailDto, mailDto,
			mailDto, mailDto, mailDto, mailDto, mailDto
		));
	}
}
