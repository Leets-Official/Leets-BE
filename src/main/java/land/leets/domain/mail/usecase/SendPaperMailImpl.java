package land.leets.domain.mail.usecase;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.domain.repository.InterviewRepository;
import land.leets.domain.interview.exception.InterviewNotFoundException;
import land.leets.domain.interview.type.HasInterview;
import land.leets.global.mail.MailProvider;
import land.leets.global.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SendPaperMailImpl implements SendMail {

	private static final String MAIL_TITLE = "[Leets] 서류 결과 안내 메일입니다.";
	private static final Map<ApplicationStatus, String> templates = Map.of(
		ApplicationStatus.PASS_PAPER, "PassPaper.html",
		ApplicationStatus.FAIL_PAPER, "FailPaper.html"
	);

	private final Random RANDOM = new Random();
	private final Environment environment;
	private final InterviewRepository interviewRepository;
	private final ApplicationRepository applicationRepository;
	private final TemplateEngine templateEngine;
	private final MailProvider mailProvider;

	@Value("${target.url.dev}")
	private String LOCAL_TARGET_URL;

	@Value("${target.url.prod}")
	private String SERVER_TARGET_URL;

	@Override
	public void execute(ApplicationStatus status) {
		List<Application> applications = applicationRepository.findAllByApplicationStatus(status);

		List<MailDto> mailDtos = new ArrayList<>();
		for (Application application : applications) {
			Context context = makeContext(application.getName());
			if (status == ApplicationStatus.PASS_PAPER) {
				setInterviewContext(context, application);
			}
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

	private void setInterviewContext(Context context, Application application) {
		boolean isProd = Arrays.stream(environment.getActiveProfiles())
			.anyMatch(env -> env.equalsIgnoreCase("prod"));

		UUID uid = application.getUser().getUid();
		UriComponents attendUrl = UriComponentsBuilder.fromHttpUrl(isProd ? SERVER_TARGET_URL : LOCAL_TARGET_URL)
			.queryParam("uid", uid)
			.queryParam("hasInterview", HasInterview.CHECK)
			.build();
		context.setVariable("attendUrl", attendUrl);

		UriComponents absentUrl = UriComponentsBuilder.fromHttpUrl(isProd ? SERVER_TARGET_URL : LOCAL_TARGET_URL)
			.queryParam("uid", uid)
			.queryParam("hasInterview", HasInterview.UNCHECK)
			.build();
		context.setVariable("absentUrl", absentUrl);

		Interview interview = interviewRepository.findByApplication(application)
			.orElseThrow(InterviewNotFoundException::new);

		String date = interview.getFixedInterviewDate()
			.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.KOREAN));
		String time = interview.getFixedInterviewDate()
			.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.KOREAN));
		context.setVariable("fixedInterviewDate", date + " " + time);
		context.setVariable("interviewPlace", interview.getPlace());
	}
}
