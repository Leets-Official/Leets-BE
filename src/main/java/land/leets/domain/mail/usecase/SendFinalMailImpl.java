package land.leets.domain.mail.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.type.ApplicationStatus;
import land.leets.global.mail.MailProvider;
import land.leets.global.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class SendFinalMailImpl implements SendMail {

	private static final String MAIL_TITLE = "[Leets] 면접 결과 안내 메일입니다.";
	private static final Map<ApplicationStatus, String> templates = Map.of(
		ApplicationStatus.PASS, "Pass.html",
		ApplicationStatus.FAIL, "Fail.html"
	);

	private final Random RANDOM = new Random();
	private final ApplicationRepository applicationRepository;
	private final MailProvider mailProvider;
	private final TemplateEngine templateEngine;

	@Override
	public void execute(ApplicationStatus status) {
		List<Application> applications = applicationRepository.findAllByApplicationStatus(status);

		List<MailDto> mailDtos = new ArrayList<>();
		for (Application application : applications) {
			Context context = makeContext(application.getName());
			String message = templateEngine.process(templates.get(status), context);
			MailDto mailDto = new MailDto(MAIL_TITLE, new String[] {application.getUser().getEmail()}, message);
			mailDtos.add(mailDto);
		}
		mailProvider.sendEmails(mailDtos);
	}

	private Context makeContext(String name) {
		Context context = new Context();
		context.setVariable("name", name);
		int themeNumber = RANDOM.nextInt(3) + 1;
		context.setVariable("theme", themeNumber);
		return context;
	}
}
