package land.leets.domain.mail.usercase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.interview.domain.Interview;
import land.leets.domain.interview.domain.repository.InterviewRepository;
import land.leets.domain.interview.exception.InterviewNotFoundException;
import land.leets.global.mail.MailProvider;
import land.leets.global.mail.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;


@RequiredArgsConstructor
@Service
@Transactional
public class SendMailImpl implements SendMail {

    private final Environment environment;
    private final MailProvider mailProvider;
    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;
    private final TemplateEngine templateEngine;
    private static final String PASS_PAPER_TEMPLATE = "PassPaper.html";
    private static final String FAIL_PAPER_TEMPLATE = "FailPaper.html";
    private static final String PASS_TEMPLATE = "Pass.html";
    private static final String FAIL_TEMPLATE = "Fail.html";
    private static final String PLUS_TEMPLATE = "Plus.html";

    @Value("${target.url.dev}")
    private String LOCAL_TARGET_URL;

    @Value("${target.url.prod}")
    private String SERVER_TARGET_URL;

    @Override
    public void execute(String paperOrFinal) {
        if ("paper".equals(paperOrFinal)) {
            processApplications(ApplicationStatus.PASS_PAPER, PASS_PAPER_TEMPLATE, "[Leets] 서류 결과 안내 메일입니다.");
            processApplications(ApplicationStatus.FAIL_PAPER, FAIL_PAPER_TEMPLATE, "[Leets] 서류 결과 안내 메일입니다.");
        }
        if ("final".equals(paperOrFinal)) {
            processApplications(ApplicationStatus.PASS, PASS_TEMPLATE, "[Leets] 면접 결과 안내 메일입니다.");
            processApplications(ApplicationStatus.FAIL, FAIL_TEMPLATE, "[Leets] 면접 결과 안내 메일입니다.");
        }
        if ("plus".equals(paperOrFinal)) {
            processApplications(ApplicationStatus.PENDING, PLUS_TEMPLATE, "[Leets 추가 안내]");
        }
    }

    private void processApplications(ApplicationStatus status, String templateName, String mailTitle) {
        List<Application> applications = applicationRepository.findAllByApplicationStatus(status);
        for (Application application : applications) {
            Context context = getContext(application.getName());
            setContextTheme(context);
            if (status == ApplicationStatus.PASS_PAPER) {
                setPaperContextVariables(context, application);
            }
            String message = templateEngine.process(templateName, context);
            sendMail(application.getUser().getEmail(), mailTitle, message);
        }
    }

    private Context getContext(String name) {
        Context context = new Context();
        context.setVariable("name", name);
        return context;
    }

    private void setPaperContextVariables(Context context, Application application) {
        boolean isProd = Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("prod"));
        UriComponents attendUrl = UriComponentsBuilder.fromHttpUrl(isProd ? SERVER_TARGET_URL : LOCAL_TARGET_URL)
                .queryParam("email", application.getUser().getEmail())
                .queryParam("attend", true).build();
        context.setVariable("attendUrl", attendUrl);

        UriComponents absentUrl = UriComponentsBuilder.fromHttpUrl(isProd ? SERVER_TARGET_URL : LOCAL_TARGET_URL)
                .queryParam("email", application.getUser().getEmail())
                .queryParam("attend", false).build();
        context.setVariable("absentUrl", absentUrl);

        Interview interview = interviewRepository.findByApplication(application).orElseThrow(InterviewNotFoundException::new);

        String date = interview.getFixedInterviewDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(Locale.KOREAN));
        String time = interview.getFixedInterviewDate().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.KOREAN));
        context.setVariable("fixedInterviewDate", date + " " + time);

        context.setVariable("interviewPlace", interview.getPlace());
    }

    private void setContextTheme(Context context) {
        Random random = new Random();
        int theme = random.nextInt(3) + 1;
        context.setVariable("theme", theme);
    }

    private void sendMail(String toEmail, String title, String message) {
        MailDto mailDto = MailDto.builder()
                .to(new String[]{toEmail})
                .title(title)
                .body(message)
                .build();
        mailProvider.sendEmail(mailDto);
    }
}
