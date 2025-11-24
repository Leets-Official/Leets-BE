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
import land.leets.domain.mail.domain.Mail;
import land.leets.domain.mail.service.MailManager;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SendPaperMailImpl implements SendMail {

	private static final String MAIL_TITLE = "[Leets] 서류 결과 안내 메일입니다.";
	private static final String NAME_FIELD = "name";
	private static final String THEME_FIELD = "theme";
	private static final int THEME_COUNT = 3;
	private static final String ENV_PROD = "prod";
	private static final String UID_FIELD = "uid";
	private static final String HAS_INTERVIEW_FIELD = "hasInterview";
	private static final String ATTEND_URL_FIELD = "attendUrl";
	private static final String ABSENT_URL_FIELD = "absentUrl";
	private static final String FIXED_INTERVIEW_DATE_FIELD = "fixedInterviewDate";
	private static final String INTERVIEW_PLACE_FIELD = "interviewPlace";
	private static final Map<ApplicationStatus, String> templates = Map.of(
		ApplicationStatus.PASS_PAPER, "PassPaper.html",
		ApplicationStatus.FAIL_PAPER, "FailPaper.html"
	);

	private final Random RANDOM = new Random();
	private final Environment environment;
	private final InterviewRepository interviewRepository;
	private final ApplicationRepository applicationRepository;
	private final TemplateEngine templateEngine;
	private final MailManager mailManager;

	@Value("${target.url.dev}")
	private String LOCAL_TARGET_URL;

	@Value("${target.url.prod}")
	private String SERVER_TARGET_URL;

	@Override
	public void execute(ApplicationStatus status) {
		List<Application> applications = applicationRepository.findAllByApplicationStatus(status);

		List<Mail> mails = new ArrayList<>();
		for (Application application : applications) {
			Context context = makeContext(application.getName());
			if (status == ApplicationStatus.PASS_PAPER) {
				setInterviewContext(context, application);
			}
			String message = templateEngine.process(templates.get(status), context);
			Mail mail = new Mail(MAIL_TITLE, application.getUser().getEmail(), message);
			mails.add(mail);
		}
		mailManager.sendEmails(mails);
	}

	private Context makeContext(String name) {
		Context context = new Context();
		context.setVariable(NAME_FIELD, name);
		int themeNumber = RANDOM.nextInt(THEME_COUNT) + 1;
		context.setVariable(THEME_FIELD, themeNumber);
		return context;
	}

	private void setInterviewContext(Context context, Application application) {
		boolean isProd = Arrays.stream(environment.getActiveProfiles())
			.anyMatch(env -> env.equalsIgnoreCase(ENV_PROD));

		UUID uid = application.getUser().getUid();
		UriComponents attendUrl = UriComponentsBuilder.fromUriString(isProd ? SERVER_TARGET_URL : LOCAL_TARGET_URL)
			.queryParam(UID_FIELD, uid)
			.queryParam(HAS_INTERVIEW_FIELD, HasInterview.CHECK)
			.build();
		context.setVariable(ATTEND_URL_FIELD, attendUrl);

		UriComponents absentUrl = UriComponentsBuilder.fromUriString(isProd ? SERVER_TARGET_URL : LOCAL_TARGET_URL)
			.queryParam(UID_FIELD, uid)
			.queryParam(HAS_INTERVIEW_FIELD, HasInterview.UNCHECK)
			.build();
		context.setVariable(ABSENT_URL_FIELD, absentUrl);

		Interview interview = interviewRepository.findByApplication(application)
			.orElseThrow(InterviewNotFoundException::new);

		String date = interview.getFixedInterviewDate()
			.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.KOREAN));
		String time = interview.getFixedInterviewDate()
			.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.KOREAN));
		context.setVariable(FIXED_INTERVIEW_DATE_FIELD, date + " " + time);
		context.setVariable(INTERVIEW_PLACE_FIELD, interview.getPlace());
	}
}
