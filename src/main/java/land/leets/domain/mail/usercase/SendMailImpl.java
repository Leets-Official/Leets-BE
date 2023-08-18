package land.leets.domain.mail.usercase;

import land.leets.domain.application.domain.Application;
import land.leets.domain.application.domain.repository.ApplicationRepository;
import land.leets.domain.application.type.ApplicationStatus;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional
public class SendMailImpl implements SendMail {

    private final Environment environment;
    private final MailProvider mailProvider;
    private final ApplicationRepository applicationRepository;
    private final TemplateEngine templateEngine;
    private static final String PASS_PAPER_TEMPLATE = "pass-paper.html";
    private static final String FAIL_PAPER_TEMPLATE = "fail-paper.html";
    private static final String PASS_TEMPLATE = "pass.html";
    private static final String FAIL_TEMPLATE = "fail.html";

    @Value("${target.url.dev}")
    private String LOCAL_TARGET_URL;

    @Value("${target.url.prod}")
    private String SERVER_TARGET_URL;

    @Override
    public void execute() {

        List<Application> applications = applicationRepository.findAll();

        for (Application application : applications) {
            Context context = getContext(application.getName(), application.getFixedInterviewDate(), application.getUser().getEmail());
            String message = "";

            if (application.getApplicationStatus() == ApplicationStatus.PASS_PAPER) {
                message = templateEngine.process(PASS_PAPER_TEMPLATE, context);
            }
            if (application.getApplicationStatus() == ApplicationStatus.FAIL_PAPER) {
                message = templateEngine.process(FAIL_PAPER_TEMPLATE, context);
            }
            if (application.getApplicationStatus() == ApplicationStatus.PASS) {
                message = templateEngine.process(PASS_TEMPLATE, context);
            }
            if (application.getApplicationStatus() == ApplicationStatus.FAIL) {
                message = templateEngine.process(FAIL_TEMPLATE, context);
            }
            MailDto mailDto = MailDto.builder()
                    .to(new String[]{application.getUser().getEmail()})
                    .title("[Leets] 서류 결과 안내 메일입니다.")
                    .body(message)
                    .build();

            mailProvider.sendEmail(mailDto);
        }
    }

    private Context getContext(String name, LocalDateTime fixedInterviewDate, String email) {
        Context context = new Context();
        context.setVariable("name", name);

        String date = fixedInterviewDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
        String time = fixedInterviewDate.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
        context.setVariable("fixedInterviewDate", date + " " + time);
        context.setVariable("interviewPlace", "김성민 하우스");

        boolean isProd = Arrays.stream(environment.getActiveProfiles()).anyMatch(env -> env.equalsIgnoreCase("prod"));

        UriComponents url = UriComponentsBuilder.fromHttpUrl(isProd ? SERVER_TARGET_URL : LOCAL_TARGET_URL)
                .queryParam("email", email)
                .build();
        context.setVariable("url", url);
        return context;
    }
}
